package pwa.velib;

public class Station {
	private String number;
	private String name;
	private Position position;
	private String address;
	private String bikeStands;
	private String availableBikes;
	private String bonus;
	private String status;
	private String banking;
	private Boolean proximite;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBikeStands() {
		return bikeStands;
	}
	public void setBikeStands(String bikeStands) {
		this.bikeStands = bikeStands;
	}
	public String getAvailableBikes() {
		return availableBikes;
	}
	public void setAvailableBikes(String availableBikes) {
		this.availableBikes = availableBikes;
	}
	public String getBonus() {
		return bonus;
	}
	public void setBonus(String bonus) {
		this.bonus = bonus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Boolean getProximite() {
		return proximite;
	}

	public void setProximite(Boolean proximite) {
		this.proximite = proximite;
	}
	
	public String getBanking(){
		return banking;
	}
	public void setBanking(String string) {
		this.banking = string;	
	}
	
	public String toString(){
		String S="<p><b>"+this.name+"</b>";
		S=S+((this.getStatus().equals("true"))?" <b><i> BONUS </b></i>":" ");
		S+=((this.getStatus().equals("CLOSED"))?" (FERMEE)":""+"</p>");
		S=S+"<p>Vélos dispo: "+this.availableBikes+" / Attaches libres: "+this.bikeStands+"</p>";
		S=S+"<p><i>"+address+"</i></p>";
		S=S+"<p>Terminal CB "+((banking.equals("true"))?"en marche":"en panne")+"</p>";
		return S;
	}
	
	
}
