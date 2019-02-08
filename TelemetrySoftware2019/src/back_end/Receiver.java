package back_end;

import java.util.ArrayList;

import com.fazecast.jSerialComm.*;

import configuration.ConfReader;
import exceptions.InvalidReadingException;
import exceptions.InvalidUpdateException;
import front_end.View;
import front_end.CommandLineView;

public class Receiver {
	/*
	 * Attributi
	 */
	private Parser parser;
	private Data data;
	private CommandSender commandSender;
	private char[] strRead; // Vengono salvati i dati letti
	private int strIndex; // Primo elemento libero dell'array
	private int openBracketIndex; // Indice parentesi aperta, -1 se non trovata
	private int closeBracketIndex; // Indice parentesi chiusa, -1 se non trovata
	private String strToSend; // Stringa pronta da inviare a parsed
	private SerialPort comPort;
	private int baudRate;
	private String commPort;
	private char pktStart;
	private char pktEnd;

	/*
	 * Costruttore
	 */
	public Receiver() {
	
		ArrayList<View> myViews = new ArrayList<>();
		myViews.add(new CommandLineView());
		
		data = new Data(myViews);
		parser = new Parser(data);
		commandSender=new CommandSender(this, data, myViews);
		strRead = new char[2056];
		strIndex = 0;
		openBracketIndex = -1;
		baudRate = (int)ConfReader.getRecBaud();
		commPort = ConfReader.getRecPort();
		pktStart = ConfReader.getPktStart();
		pktEnd = ConfReader.getPktEnd();
	}
	
	/*
	 * Send string 'toSend' through comPort adding pktStart and pktEnd
	 */
	public void send(String toSend) {
		toSend = pktStart+toSend+pktEnd;
		//comPort.writeBytes(toSend.getBytes(), toSend.getBytes().length);
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
					strRead[strIndex] = (char) (newData[i]); // Salva i caratteri letti nell'array
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
	 * Su un nuovo thread viene aperta la porta seriale e messa in ascolto
	 */
	public void Reader() {
		comPort = SerialPort.getCommPort(commPort);
		comPort.openPort(); // Apertura della porta
		if (comPort.isOpen() == false) {
			System.err.println("Impossibile aprire la porta");
		} else {
			comPort.setBaudRate(baudRate);
			PacketListener listener = new PacketListener();
			comPort.addDataListener(listener);
		}
	}

	/*
	 * Crea una stringa con i caratteri compresi tra le parentesi aperta e chiusa,
	 * parentesi comprese Resetta gli indici delle parentesi
	 */
	private void createString() {
		strToSend = new String(strRead, (openBracketIndex + 1), (closeBracketIndex - openBracketIndex - 1));
		strIndex = 0;
		openBracketIndex = -1;
		closeBracketIndex = -1;
		try {
			parser.parseString(strToSend);
		} catch (InvalidReadingException | InvalidUpdateException e) {
			e.log();
		}
	}
	
	///////only for TEST
	public Parser getParser() {
		return parser;
	}
	public CommandSender getCommandSender() {
		return commandSender;
	}
	//N.B. togliere commento riga 56 alla finde dei test

	/*
	 * Main
	 */
	public static void main(String[] args) {
		
		Receiver rec = new Receiver();
		Thread threadLettura = new Thread(new Runnable() {
			@Override
			public void run() {
				rec.Reader();
			}
		});
		threadLettura.start();
		
		/////////TEST
		try {
			rec.getParser().parseString("ES");
			rec.getParser().parseString("AM");
			rec.getParser().parseString("S;1");
			rec.getParser().parseString("S;0");
			rec.getParser().parseString("D;13245;3.77");
			rec.getParser().parseString("C;13.45;3.77");
			rec.getParser().parseString("C;13.44;3.90");
		} catch (InvalidReadingException | InvalidUpdateException e) {
			e.log();
		}
		///////////
	}
}
