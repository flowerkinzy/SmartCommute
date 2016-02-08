package pwa.companycar;

import java.util.Date;

public class Booking {
	private Long id;
	private String carID;
	private String name;
	private Date start_time;
	private Date end_time;
	private String reason;
	private String IP;
	
	public Booking(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/*public String getCarID() {
		return carID;
	}

	public void setCarID(String carID) {
		this.carID = carID;
	}*/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
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

	public String toString(){
		String S=this.carID+" pour "+this.name+" du "+this.start_time+" au "+this.end_time;
		return S;
	}
}
