package companycar;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class BookingManager {

	public Booking create(String name,Car car, Date start, Date end, String reason,String IP){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		Booking booking = new Booking();
		booking.setName(name);
		booking.setCar(car);
		booking.setStart_time(start);
		booking.setEnd_time(end);
		booking.setReason(reason);
		booking.setIP(IP);
		session.save(booking);
		T.commit();
		return booking;
	}
	
	public void cancel(Booking booking){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		session.delete(booking);
		T.commit();
	}
	
	
}
