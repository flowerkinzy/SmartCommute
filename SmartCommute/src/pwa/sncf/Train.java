package pwa.sncf;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Train {
private Date dateDepart;
private String nom;
public Date getDateDepart() {
	return dateDepart;
}
public void setDateDepart(  Date dateDepart) {
	this.dateDepart = dateDepart;
}
public String getNom() {
	return nom;
}
public void setNom(String nom) {
	this.nom = nom;
}
}
