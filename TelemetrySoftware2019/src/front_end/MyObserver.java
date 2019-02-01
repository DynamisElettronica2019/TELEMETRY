package front_end;

import java.util.Observer;

public abstract class MyObserver implements Observer{
	
	protected View myView;
	
	public MyObserver(View myView) {
		this.myView=myView;
	}

}
