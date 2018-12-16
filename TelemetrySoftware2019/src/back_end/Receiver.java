package back_end;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortPacketListener;

import Receiver.PacketListener;

public class Receiver {
	/*
	 * Attributi
	 */
	private char[] strRead; // Vengono salvati i dati letti
	private int strIndex; // Primo elemento libero dell'array
	private int openBracketIndex; // Indice parentesi aperta, -1 se non trovata
	private int closeBracketIndex; // Indice parentesi chiusa, -1 se non trovata
	private String strToSend; // Stringa pronta da inviare a parsed
	private SerialPort comPort;
	private int baudRate;
	private String commPort;

	/*
	 * Costruttore
	 */
	public Receiver() {
		strRead = new char[1028];
		strIndex = 0;
		openBracketIndex = -1;
		baudRate = 115200;
		commPort = new String("COM5");
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
					if (strRead[strIndex] == '[') { // Salva l'indice della parentesi aperta
						openBracketIndex = strIndex;
					} else if (openBracketIndex != -1) { // Cerco una parentesi chiusa solo se ne ho trovata una aperta
						if (strRead[strIndex] == ']') {
							closeBracketIndex = strIndex;
							createString(); // Chiamata alla funziona che crea la stringa da inviare
						}
					}
					if (strIndex==2047) {
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
		printString();
	}

	/*
	 * Stampa la stringa preparata
	 */
	private void printString() {
		System.out.println(strToSend);
	}

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
	}
}
