package pwa.sncf;

import java.util.Date;
import java.util.List;
import java.util.Vector;

public class SncfGare {
private String nom;
private long uic;
private List<Train> trains;
public SncfGare(){
	trains=new Vector<Train>();
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

public List<Train> getTrains() {
	return trains;
}

public void setTrains(List<Train> trains) {
	this.trains = trains;
}
}
