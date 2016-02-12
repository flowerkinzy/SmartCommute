package pwa.companycar;


import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;

import util.HibernateUtil;

public class BookingManager {

	private Logger logger=LoggerFactory.logger(getClass());
	private SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public Booking create(String name,Car car, Date start, Date end, String reason,String IP){
		if(isCarBooked(car, start, end))return null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		Booking booking = new Booking();
		booking.setName(name);
		
		try{
			booking.setStart_time(start);
			booking.setEnd_time(end);
			booking.setReason(reason);
			booking.setIP(IP);
			booking.setCar(car);
	
			//TODO check time;
			booking.setId((Long) session.save(booking));
			Hibernate.initialize(booking.getCar());
		}catch(Exception e){
			logger.error("Impossible d'enregistrer la réservation");
			e.printStackTrace();
			booking=null;
		}
		T.commit();
		return booking;
	}
	
	public List<Booking> listAllBookings(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		List<Booking> list=session.createQuery("from Booking").list();
		T.commit();
		return list;
	}
	
	public List<Booking> listPendingBookings(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		String now=df.format(new Date());
		List<Booking> list=session.createQuery("from Booking where end_time>'"+now+"'").list();
		T.commit();
		return list;
	}
	
	public void cancel(Booking booking){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		session.delete(booking);
		T.commit();
	}
	
	public void cancel(Long id){
		cancel(get(id));
	}
	
	public Booking get(Long id){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		Booking B = session.load(Booking.class, id);
		T.commit();
		return B;
	}
	public boolean isCarBooked(Car c, Date start,Date end){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		String startT=df.format(start);
		String endT=df.format(end);
		Long count=(Long) session.createQuery("SELECT COUNT(*) FROM Booking WHERE (start_time<='"+startT+"' AND end_time>'"+startT+"') OR (start_time<'"+endT+"' AND end_time>='"+endT+"') OR (start_time>='"+startT+"' AND end_time<='"+endT+"')").uniqueResult();
		T.commit();
		return (count>0);
		
	}
	
	
}
