package front_end;

import java.util.Observable;
import back_end.State;

public class StateObserver extends MyObserver{

	public StateObserver(View myView) {
		super(myView);
	}

	@Override
	public void update(Observable o, Object arg) {
		myView.UpdateState((State)o);
	}

}
