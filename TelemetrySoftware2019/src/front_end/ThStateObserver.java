package front_end;

import java.util.Observable;

import back_end.Threshold;

public class ThStateObserver extends MyObserver {

	public ThStateObserver(View myView) {
		super(myView);
	}

	@Override
	public void update(Observable o, Object arg) {
		myView.UpdateTS((Threshold)o);
	}

}
