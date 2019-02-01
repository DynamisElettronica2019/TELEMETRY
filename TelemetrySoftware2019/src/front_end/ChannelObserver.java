package front_end;

import java.util.Observable;
import back_end.Channel;

public class ChannelObserver extends MyObserver{

	public ChannelObserver(View myView) {
		super(myView);
	}

	@Override
	public void update(Observable o, Object arg) {
		myView.UpdateChannel((Channel)o);
	}

}
