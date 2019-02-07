package front_end;

import java.util.Scanner;

import back_end.CommandSender;

public class CommandLineReader implements Runnable{
	
	CommandSender commandSender;
	Scanner sc;
	
	public CommandLineReader(CommandSender commandSender) {
		this.commandSender = commandSender;
		sc = new Scanner(System.in);
	}
	
	private void commandReaded(String readed) {
		String[] splitted = readed.split(";", 2);
		if(splitted.length < 2) commandSender.sendCommand(splitted[0], "");
		else commandSender.sendCommand(splitted[0], splitted[1]);
	}

	@Override
	public void run() {
		while(true){
			this.commandReaded(sc.nextLine());
		}
	}

}
