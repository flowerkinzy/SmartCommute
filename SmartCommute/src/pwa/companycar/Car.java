package pwa.companycar;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Car {
	@XmlElement
	private String immatriculation;
	@XmlElement
	private String description;
	@XmlElement
	private Integer numberOfSeats;
	@XmlElement
	private String notes;
	@XmlElement
	private String stateCode;
	@XmlElement
	private Boolean inUse;
	
	private Set<Booking> bookings;
	
	public Car(){
		
	}
	
	public String getImmatriculation() {
		return immatriculation;
	}

	public void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public State getState() {
		return State.get(stateCode);
	}
	public String getStateCode() {
		return stateCode;
	}

	public void setState(State state) {
		this.stateCode = state.getCode();
	}

	public void setStateCode(String code) {
		this.stateCode = code;
	}
	
	public Boolean getInUse() {
		return inUse;
	}

	public void setInUse(Boolean inUse) {
		this.inUse = inUse;
	}

	public Set<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(Set<Booking> bookings) {
		this.bookings = bookings;
	}

	public enum State{
		BOOKABLE_SAFE("S", "En bon état"),
		BOOKABLE_DEGRADED("D", "Dégradé"),
		OUT_OF_ORDER("O","Hors d'usage/En réparation");
		
		private String code;
		private String label;
		State(String code,String label){
			this.code = code;
			this.label = label;
		}
		public String getCode(){
			return code;
		}
		public String getLabel(){
			return label;
		}
		static State get(String code){
			for(State S:State.values()){
				if(S.getCode().equals(code))return S;
			}
			return null;
		}
		public String toString(){
			return label;
			
		}
	};
	public String toString(){
		String S=this.immatriculation+" _ "+this.description+" ("+this.numberOfSeats+")  "+State.get(stateCode);
		return S;
	}


}
