package pwa.sncf;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Train {
private Integer id;
private Date dateDepart;
private String nom;
private String etat;
public Boolean tempsReel=true;
private Long arriveeId;

public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
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
public String getEtat() {
	return etat;
}
public void setEtat(String etat) {
	this.etat = etat;
}

public Long getArriveeId() {
	return arriveeId;
}
public void setArriveeId(Long arriveeId) {
	this.arriveeId = arriveeId;
}

public enum Etat{
	NORMAL(null,""),
	RETARDE("R", "Retardé"),
	SUPPRIME("S","Supprime");
	private String code;
	private String label;
	
	Etat(String code,String label){
		this.code = code;
		this.label = label;
	}
	
	public String toString(){
		return label;
	}
	
	static Etat get(String code){
		for(Etat e:Etat.values()){
			if(code==null)return NORMAL;
			if(code.equals(e.code))return e;
		}
		return null;
	}
};
public long getTempsAttenteEnMin(){
	return ((dateDepart.getTime()-new Date().getTime())/(1000*60));
}

public String toString(){
	SimpleDateFormat df=new SimpleDateFormat("HH:mm");
	String depart=df.format(getDateDepart());
	
	String arrivee="";
	if(arriveeId!=null){
		SncfGare G;
		SNCFGareManager gareManager=new SNCFGareManager();
		G = gareManager.UICToGare(this.arriveeId);
		if(G!=null)arrivee=G.getNom();
	}
	
	String S=nom+"\t"+depart+"\t"+arrivee+"\t"+Etat.get(etat);
	return S;
}

public boolean equals(Train T){
	return this.id==T.id;
}

}
