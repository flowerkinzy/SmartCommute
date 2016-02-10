package pwa.sncf;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class SncfGare {
private String nom;
private long uic;
private Boolean proximite;
private Integer timeToReach;
private Set<Train> trains;

public SncfGare(){

}

public String getNom() {
	return nom;
}

public void setNom(String nom) {
	this.nom = nom;
}

public long getUic() {
	return uic;
}

public void setUic(long uic) {
	this.uic = uic;
}

public Set<Train> getTrains() {
	return trains;
}

public void setTrains(Set<Train> trains) {
	this.trains = trains;
}

public Boolean getProximite() {
	return proximite;
}

public void setProximite(Boolean proximite) {
	this.proximite = proximite;
}

public Integer getTimeToReach() {
	return timeToReach;
}

public void setTimeToReach(Integer timeToReach) {
	this.timeToReach = timeToReach;
}

public boolean equals(SncfGare G){
	return this.uic==G.uic;
}

}
