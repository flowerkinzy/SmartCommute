package pwa.sncf;

import java.util.ArrayList;
import java.util.List;

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
		List<SncfGare> gares=new ArrayList<SncfGare>();
		List<Long> uics=session.createQuery("select min(uic) from SncfGare where proximite=1 group by label").list();
		logger.info(uics.size()+ "gares de proximit� trouv�es\n");
		if(uics!=null){
			for(Long uic:uics){
				gares.add(this.UICToGare(uic));
			}	
		}
		T.commit();
		return gares;
	}
	
	public SncfGare UICToGare(String uic){
		return UICToGare(Long.parseLong(uic));		
	}
	
	public SncfGare UICToGare(Long uic){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		SncfGare gare=null;
		Long count=(Long) session.createQuery("select count(*) from SncfGare where uic="+uic).uniqueResult();
		if(count>0){
			gare=session.load(SncfGare.class,uic);	
			
		}
		if(gare==null)logger.error("Pas de gare trouv�e de UIC="+uic+"\n");
		else {
			logger.info("La gare d'UIC="+uic+" est "+gare.getNom()+"\n");
			session.update(gare);
		}
		T.commit();
		return gare;
	
	}
	
	private void peuplerSiBesoinTable(){
		/*
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		try{
			Long result = (Long) session.createQuery("select count(*) from SncfGare").list().uniqueResult();
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
