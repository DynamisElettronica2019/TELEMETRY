package front_end;

import java.util.Observable;

import back_end.LapTimer;

public class LapObserver extends MyObserver {
	
	public LapObserver(View myView) {
		super(myView);
	}

	@Override
	public void update(Observable o, Object arg) {
		myView.UpdateLap((LapTimer)o);
	}

}
