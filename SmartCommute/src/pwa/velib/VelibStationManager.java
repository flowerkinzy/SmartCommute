package pwa.velib;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class VelibStationManager {
	private Logger logger=LoggerFactory.logger(getClass());

	public List<Station> listeStationsProchesTriées(final Position maPosition, double rayon,int limite) throws IOException{
		URL url = new URL("https://api.jcdecaux.com/vls/v1/stations?contract=Paris&apiKey=476538ecdf63de3988d94cca220af46f8376cc41");
		URLConnection urlConn = url.openConnection();
		System.out.println(urlConn.getInputStream());
		JSONArray ParisStations = null;
		try {
			ParisStations = new JSONArray(new JSONTokener(urlConn.getInputStream()));
			System.out.println("Nombre de stations vélib parisiennes "+ParisStations.length());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Station s;
		List<Station> stationsProximite = new ArrayList<Station>();
		
		
		
		for (int i = 0; i < ParisStations.length(); i++) {
			JSONObject station;
			try {
				station = ParisStations.getJSONObject(i);
				JSONObject pos = station.getJSONObject("position");
				
				double lng=Double.parseDouble(pos.get("lng").toString());
				double lat=Double.parseDouble(pos.get("lat").toString());
				
				if ((Math.sqrt(maPosition.getLat()-lat)<rayon)&&(Math.sqrt(maPosition.getLng()-lng)<rayon))
					{System.out.println(station.toString());
					s= new Station();
					s.setPosition(new Position(lat,lng));
					s.setNumber(station.get("number").toString());
					s.setName(station.get("name").toString());
					s.setAddress(station.get("address").toString());
					s.setAvailableBikes(station.get("available_bikes").toString());
					s.setBikeStands(station.get("bike_stands").toString());
					s.setBonus(station.get("bonus").toString());
					s.setStatus(station.get("status").toString());
					s.setBanking(station.get("banking").toString());
					stationsProximite.add(s);
					}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
			
		}
		/*trier les stations selon leur état et leur proximité*/
		stationsProximite.get(0).setStatus("CLOSED");// juste pour tester pcq les 2 stations sont ouvertes
		
		Collections.sort(stationsProximite, new Comparator<Station>() {
	        @Override
	        public int compare(Station s2, Station s1)
	        {
	        	if(s1.getStatus().equals(s2.getStatus()))return (int) (s1.getPosition().dist(maPosition)-s2.getPosition().dist(maPosition));
	            return  s1.getStatus().compareTo(s2.getStatus());
	        }
	    });
		if(limite<stationsProximite.size())return stationsProximite.subList(0, limite);
		else return stationsProximite;
	}
	
/*
	public void nouvelleStationDeProximite(Station station,Position position){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		station.setProximite(Boolean.TRUE);
		station.setPosition(position);
		session.update(station);
		T.commit();
	}
	public void supprimerStationDeProximite(Station station){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		station.setProximite(Boolean.FALSE);
		session.update(station);
		T.commit();
	}
	public List<String> listeStations(){
		peuplerSiBesoinTable();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		List<String> stations=session.createQuery("select label from Station distinct label").list();
		T.commit();
		return stations;
	}

	
	public List<Station> stationsDeProximite(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		List<Station> stations=session.createQuery("from Station where proximite=1 distinct label").list();
		T.commit();
		return stations;
	}
	
	public Station NUMBERToGare(String number){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		List<Station> stations=session.createQuery("from Station where number="+Long.parseLong(number)).list();
		if(stations.isEmpty()){
			logger.info("Pas de gare trouvée de numero="+number);
			return null;
		}
		else return stations.get(0);		
	}
	
*/
	
}

