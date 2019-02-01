package front_end;

import java.util.Observable;

import back_end.Command;

public class CommandObserver extends MyObserver{

	public CommandObserver(View myView) {
		super(myView);
	}

	@Override
	public void update(Observable o, Object arg) {
		myView.UpdateCommand((Command)o);
	}

}
