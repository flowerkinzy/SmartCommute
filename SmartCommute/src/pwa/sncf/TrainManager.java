package pwa.sncf;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import util.HibernateUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.tomcat.util.res.*;

public class TrainManager {
	private Logger logger=LoggerFactory.logger(getClass());
	
	//TODO Cache
	public HashSet<Train> prochainsTrains(SncfGare G) throws IOException{
		URL url = new URL("http://api.transilien.com/gare/"+G.getUic()+"/depart/");
		//String encoding = Base64.getEncoder().encodeToString("tnhtn394:LgS43b5m".getBytes("utf-8")); //Java 8
		String encoding = DatatypeConverter.printBase64Binary("tnhtn394:LgS43b5m".getBytes("utf-8"));
		HttpURLConnection connexion = (HttpURLConnection) url.openConnection();
		//Si la connexion échoue
		//TODO récupérer les derniers trains enregistrés? API horaires fixes(tempsRéel à false, et ne pas enregistrer)
		//
		connexion.setRequestMethod("GET");
		connexion.setDoOutput(true);
		connexion.setRequestProperty("Authorization", "Basic " + encoding);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		  DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(connexion.getInputStream());
		
		  HashSet<Train> trains = new HashSet<Train>();     
		  NodeList fils = doc.getElementsByTagName("train");
		  for (int tmp = 0; tmp < fils.getLength(); tmp++) {      
		    Node nNode = fils.item(tmp);
		    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		      Element eElement = (Element)nNode;        
		      Train t = new Train(); 
		      t.setDepartId(G.getUic());
		      Date now=new Date();
		      if(G.getTimeToReach()==null)now.setTime(now.getTime());
		      else now.setTime(now.getTime()+(G.getTimeToReach()*60*1000));
		      t.setDateDepart(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(eElement.getElementsByTagName("date").item(0).getTextContent()));
		      if(t.getDateDepart().after(now)){//Retirer les trains qui arrivent tôt par rapport au temps de marhe
			      t.setNom(eElement.getElementsByTagName("miss").item(0).getTextContent());
			      if(eElement.getElementsByTagName("term")!=null)t.setArriveeId(Long.parseLong(eElement.getElementsByTagName("term").item(0).getTextContent()));     
			      trains.add(t);
			     
		      }else  logger.info("Train ignoré car trop tôt: "+t.getDateDepart()+"\n");
		      //TODO Les enregistrer (ou pas)
		      
		      
		    }
		  }
		  G.setTrains(trains);
		  return trains;
		} catch (DOMException e) {
				e.printStackTrace();
		} catch (ParseException e) {
				e.printStackTrace();
		} catch (ParserConfigurationException e1) {
				e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public HashSet<Train> prochainsTrains(SncfGare G,int limit) throws IOException{
		URL url = new URL("http://api.transilien.com/gare/"+G.getUic()+"/depart/");
		//String encoding = Base64.getEncoder().encodeToString("tnhtn394:LgS43b5m".getBytes("utf-8")); //Java 8
		String encoding = DatatypeConverter.printBase64Binary("tnhtn394:LgS43b5m".getBytes("utf-8"));
		HttpURLConnection connexion = (HttpURLConnection) url.openConnection();
		//Si la connexion échoue
		//TODO récupérer les derniers trains enregistrés? API horaires fixes(tempsRéel à false, et ne pas enregistrer)
		//
		connexion.setRequestMethod("GET");
		connexion.setDoOutput(true);
		connexion.setRequestProperty("Authorization", "Basic " + encoding);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		  DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(connexion.getInputStream());
		
		  HashSet<Train> trains = new HashSet<Train>();     
		  NodeList fils = doc.getElementsByTagName("train");
		  for (int tmp = 0; tmp < fils.getLength() && trains.size()<limit; tmp++) {      
		    Node nNode = fils.item(tmp);
		    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		      Element eElement = (Element)nNode;        
		      Train t = new Train();     
		      t.setDepartId(G.getUic());
		      Date now=new Date();
		      if(G.getTimeToReach()==null)now.setTime(now.getTime());
		      else now.setTime(now.getTime()+(G.getTimeToReach()*60*1000));
		      t.setDateDepart(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(eElement.getElementsByTagName("date").item(0).getTextContent()));
		      if(t.getDateDepart().after(now)){//Retirer les trains qui arrivent tôt par rapport au temps de marhe
			      t.setNom(eElement.getElementsByTagName("miss").item(0).getTextContent());
			      if(eElement.getElementsByTagName("term")!=null)t.setArriveeId(Long.parseLong(eElement.getElementsByTagName("term").item(0).getTextContent()));     
			      trains.add(t);
			      
			     
		      }else  logger.info("Train ignoré car trop tôt: "+t.getDateDepart()+"\n");
		      //TODO Les enregistrer (ou pas)
		      
		      
		    }
		  }
		  G.setTrains(trains);
		  return trains;
		} catch (DOMException e) {
				e.printStackTrace();
		} catch (ParseException e) {
				e.printStackTrace();
		} catch (ParserConfigurationException e1) {
				e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public HashSet<Train> prochainsTrainsToutesGaresProches(int limit) throws IOException{
		HashSet<Train> trains=new HashSet<Train>();
		List<SncfGare> gares=new SNCFGareManager().garesDeProximite();
		
		if(gares==null || gares.size()==0 ){
			gares.add(new SNCFGareManager().UICToGare("87384008"));
		}
		for(SncfGare gare:gares){
			trains.addAll(this.prochainsTrains(gare, limit));
		}
		return trains;
	}
	private void save(Train train){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		session.saveOrUpdate(train);
		T.commit();
	}
	
	
}
