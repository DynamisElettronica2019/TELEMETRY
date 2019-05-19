package back_end;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.fazecast.jSerialComm.*;

import configuration.ConfReader;
import exceptions.InvalidReadingException;
import exceptions.InvalidUpdateException;
import front_end.View;

public class Receiver {
	/*
	 * Attributi
	 */
	private Parser parser;
	private Data data;
	private CommandSender commandSender;
	private byte[] strRead; // Vengono salvati i dati letti
	private int strIndex; // Primo elemento libero dell'array
	private int openBracketIndex; // Indice parentesi aperta, -1 se non trovata
	private int closeBracketIndex; // Indice parentesi chiusa, -1 se non trovata
	private byte[] strToSend; // Stringa pronta da inviare a parsed
	private SerialPort comPort;
	private int baudRate;
	private String portName;
	private byte pktStart;
	private byte pktEnd;
	private char pktStartSend;
	private char pktEndSend;
	private char mode;

	/*
	 * Costruttore. Selezionare modalità car ("C") o lap ("L"). La modalità L non ha il command sender
	 */
	public Receiver(ArrayList<View> myViews, Data data, Parser parser, char mode) {
		this.mode = mode;
		this.data = data;
		this.parser = parser;
		strRead = new byte[2056];
		strIndex = 0;
		openBracketIndex = -1;
		if(mode == 'C'){
			commandSender=new CommandSender(this, data, myViews);
			baudRate = (int)ConfReader.getCarRecBaud();
			portName = ConfReader.getCarRecPort();
		}
		else if(mode == 'L'){
			baudRate = (int)ConfReader.getLapRecBaud();
			portName = ConfReader.getLapRecPort();
		}
		pktStart = (byte) 0x57;
		pktEnd = (byte) 0x67;
		
		pktStartSend = ConfReader.getPktStart();
		pktEndSend = ConfReader.getPktEnd();
	}
	
	/*
	 * Send string 'toSend' through comPort adding pktStart and pktEnd. Sending disable in L mode
	 */
	public void send(String toSend) {
		if(mode == 'C'){
			toSend = pktStartSend+toSend+pktEndSend;
			comPort.writeBytes(toSend.getBytes(), toSend.getBytes().length);
		}
	}

	/*
	 * Apre la porta seriale e salva nell'array di caratteri Controlla la presenza
	 * di parentesi e ne salva gli indici
	 */
	public final class PacketListener implements SerialPortPacketListener {
		
		@Override
		public int getListeningEvents() {
			return SerialPort.LISTENING_EVENT_DATA_RECEIVED; // Verifica la presenza di dati da leggere
		}

		@Override
		public int getPacketSize() {
			return 1;
		}

		@Override
		public void serialEvent(SerialPortEvent event) { // Funzione di callback
			try {
				byte[] newData = event.getReceivedData();
				for (int i = 0; i < newData.length; ++i) {
					strRead[strIndex] = newData[i]; // Salva i byte letti nell'array
					if (strRead[strIndex] == pktStart) { // Salva l'indice della parentesi aperta
						openBracketIndex = strIndex;
					} else if (openBracketIndex != -1) { // Cerco una parentesi chiusa solo se ne ho trovata una aperta
						if (strRead[strIndex] == pktEnd) {
							closeBracketIndex = strIndex;
							createString(); // Chiamata alla funziona che crea la stringa da inviare
						}
					}
					if (strIndex==2055) {
						strIndex = 0;
						openBracketIndex = -1;
					}
					else {
						strIndex++;
					}
				}
			}
			catch (Exception e) { //check errore
				e.printStackTrace();
			}
		}
	}

	
	/*
	 * Su un nuovo thread viene aperta la porta seriale e messa in ascolto. Parte un nuovo thread per la
	 * gestione di eventuali disconnessioni
	 */
	public void Reader() {
		comPort = SerialPort.getCommPort(portName);
		Thread portListener = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					if((comPort.isOpen() == false)){
						comPort.openPort(); //Open port
						if((comPort.isOpen() == true)){
							if(mode == 'C') System.out.println("Car receiver connected on port "+portName);
							if(mode == 'L') System.out.println("Lap receiver connected on port "+portName);
						}
					}
					try {
						TimeUnit.SECONDS.sleep(2); //Wait
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		portListener.start();
		comPort.setBaudRate(baudRate);
		PacketListener listener = new PacketListener();
		comPort.addDataListener(listener);
	}

	/*
	 * Crea una stringa con i caratteri compresi tra le parentesi aperta e chiusa,
	 * parentesi comprese Resetta gli indici delle parentesi
	 */
	private void createString() {
		strToSend = Arrays.copyOfRange(strRead, (openBracketIndex + 1), (closeBracketIndex));
		strIndex = 0;
		openBracketIndex = -1;
		closeBracketIndex = -1;
		parser.decodeString(strToSend);
	}

}
