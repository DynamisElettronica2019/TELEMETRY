package front_end.gui_ground;

public class ErrorTableList {
	private String name;
	private String lastOcc;
	private int nOcc;
	
	public ErrorTableList(String name, String lastOcc, int nOcc) {
		this.setName(name);
		this.setLastOcc(lastOcc);
		this.setNOcc(nOcc);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastOcc() {
		return lastOcc;
	}
	public void setLastOcc(String lastOcc) {
		this.lastOcc = lastOcc;
	}
	public int getNOcc() {
		return nOcc;
	}
	public void setNOcc(int nOcc) {
		this.nOcc = nOcc;
	}
	
}
