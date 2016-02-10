package pwa.sncf;

public class Station {
	private String name;
	private String ID;
	private Integer timeToReach;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getTimeToReach() {
		return timeToReach;
	}
	public void setTimeToReach(Integer timeToReach) {
		this.timeToReach = timeToReach;
	}
}
