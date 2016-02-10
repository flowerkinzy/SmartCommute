package pwa.sncf;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import util.HibernateUtil;

public class SNCFGareManager {
	
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
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		List<SncfGare> gares=session.createQuery("from SncfGare where proximite=1 distinct label").list();
		T.commit();
		return gares;
	}
	
	public SncfGare UICToGare(String uic){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		List<SncfGare> gares=session.createQuery("from SncfGare where uic="+Long.parseLong(uic)).list();
		if(gares.isEmpty())return null;
		else return gares.get(0);		
	}
}
