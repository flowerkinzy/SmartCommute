package pwa.sncf;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;

import util.HibernateUtil;

public class SNCFGareManager {
	private Logger logger=LoggerFactory.logger(getClass());
	
	public void nouvelleGareDeProximite(SncfGare gare,Integer timeToReach){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		gare.setProximite(Boolean.TRUE);
		gare.setTimeToReach(timeToReach);
		session.update(gare);
		T.commit();
	}
	public void supprimerGareDeProximite(SncfGare gare){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		gare.setProximite(Boolean.FALSE);
		gare.setTimeToReach(null);
		session.update(gare);
		T.commit();
	}
	
	public List<String> listeGares(){
		peuplerSiBesoinTable();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		List<String> gares=session.createQuery("select label from SncfGare distinct label").list();
		T.commit();
		return gares;
	}

	private List<SncfGare> garesIdentiques(SncfGare G){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		List<SncfGare> gares=session.createQuery("from SncfGare where label='"+G.getNom()+"'").list();
		T.commit();
		return gares;
	}
	
	public List<SncfGare> garesDeProximite(){
		peuplerSiBesoinTable();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		List<SncfGare> gares=session.createQuery("from SncfGare where proximite=1 distinct label").list();
		T.commit();
		return gares;
	}
	
	public SncfGare UICToGare(String uic){
		peuplerSiBesoinTable();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		List<SncfGare> gares=session.createQuery("from SncfGare where uic="+Long.parseLong(uic)).list();
		if(gares.isEmpty()){
			logger.info("Pas de gare trouvée de UIC="+uic);
			return null;
		}
		else return gares.get(0);		
	}
	
	private void peuplerSiBesoinTable(){
		/*
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		try{
			Long result = (Long) session.createQuery("select count(*) from SncfGare").list().get(0);
			if(result>0)return;
		}finally{
			logger.info("Chargement de la table des stations (2min)");
			try {
				FileReader reader=new FileReader("../../../ressources/SNCFStation.sql");
				char[] chars=null;
				int code;
				String statement="";
				while((code=reader.read())!=-1){
					if(code==';'){
						session.createSQLQuery(statement).executeUpdate();
					}else{
						statement+=chars;
					}
				}
				logger.info("Fin du chargement de la table");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				logger.error("Echec du chargement de la table");
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				logger.error("Echec du chargement de la table");
				e1.printStackTrace();
			}
			
		}
		
		T.commit();
		*/
	}
}
