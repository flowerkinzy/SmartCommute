package pwa.companycar;


import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;

import util.HibernateUtil;

public class BookingManager {

	private Logger logger=LoggerFactory.logger(getClass());
	
	public Booking create(String name,Car car, Date start, Date end, String reason,String IP){
		if(isCarBooked(car, start, end))return null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		Booking booking = new Booking();
		booking.setName(name);
		//booking.setCarID(car.getImmatriculation());
		booking.setStart_time(start);
		booking.setEnd_time(end);
		booking.setReason(reason);
		booking.setIP(IP);
		//TODO check time
		session.save(booking);
		try{
		car.getBookings().add(booking);
		session.update(car);
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
		Timestamp now=new Timestamp(new Date().getTime());
		List<Booking> list=session.createQuery("from Booking where end_time>"+now).list();
		T.commit();
		return list;
	}
	
	public void cancel(Booking booking){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		session.delete(booking);
		T.commit();
	}
	
	public boolean isCarBooked(Car c, Date start,Date end){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		Timestamp startT = new Timestamp(start.getTime());
		Timestamp endT = new Timestamp(start.getTime());
		Integer count=(Integer) session.createQuery("SELECT COUNT(*) FROM BOOKING WHERE (start_time<="+startT+" AND end_time>"+startT+") OR (start_time<"+endT+" AND end_time>="+endT+") OR (start_time>="+startT+" AND end_time<="+endT).list().get(0);
		T.commit();
		return (count>0);
		
	}
	
	
}
