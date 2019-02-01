package front_end;

import java.util.Observable;
import back_end.Error;

public class ErrorObserver extends MyObserver{

	public ErrorObserver(View myView) {
		super(myView);
	}

	@Override
	public void update(Observable o, Object arg) {
		myView.UpdateError((Error)o);
	}

}
