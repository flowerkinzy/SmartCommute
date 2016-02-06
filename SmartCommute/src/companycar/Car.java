package companycar;

public class Car {
	private String immatriculation;
	private String description;
	private Integer numberOfSeats;
	private String notes;
	private String state;
	
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
		return State.get(state);
	}

	public void setState(State state) {
		this.state = state.getCode();
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
	};

}
