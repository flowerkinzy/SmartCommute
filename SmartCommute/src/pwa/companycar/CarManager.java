package pwa.companycar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;

import util.HibernateUtil;

public class CarManager {
	
	private Logger logger=LoggerFactory.logger(getClass());
	private SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public Car createCar(String immatriculation,String description, Integer numberOfSeats){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		Car car = new Car();
		car.setImmatriculation(immatriculation);
		car.setDescription(description);
		car.setNumberOfSeats(numberOfSeats);
		car.setState(Car.State.BOOKABLE_SAFE);
		car.setInUse(Boolean.FALSE);
		session.save(car);
		T.commit();
		return car;
	}
	
	public List<Car> getBookableCars(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		List<Car> list=session.createQuery("from Car where state='S' or state='D'").list();
		logger.info("Nb véhicules réservable "+list.size()+"\n");
		T.commit();
		return list;
	}
	
	public List<Car> getAvailableCars(Date start,Date end){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		String startT=df.format(start);
		String endT=df.format(end);
		List<Car> list=session.createQuery("from Car C  where NOT(state='O') AND C.immatriculation NOT IN (SELECT car.immatriculation FROM Booking WHERE (start_time<='"+startT+"' AND end_time>'"+startT+"') OR (start_time<'"+endT+"' AND end_time>='"+endT+"') OR (start_time>='"+startT+"' AND end_time<='"+endT+"'))").list();
		logger.info("Nb véhicules disponibles sur cette période "+list.size()+"\n");
		T.commit();
		return list;
	}
	
	
	public List<Car> getCurrentAvailableCars(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		String now=df.format(new Date());
		List<Car> list=session.createQuery("from Car C where NOT(state='O') AND inUse=0 AND  C.immatriculation NOT IN (SELECT car.immatriculation FROM Booking WHERE (start_time<='"+now+"' AND end_time>='"+now+"'))").list();
		logger.info("Nb véhicules disponibles actuellement "+list.size()+"\n");
		T.commit();
		return list;
	}
	
	public List<Car> getAllCars(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		List<Car> list=session.createQuery("from Car").list();
		T.commit();
		return list;
	}
	
	public void useCar(Car c){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		c.setInUse(Boolean.TRUE);
		session.update(c);
		T.commit();
	}
	
	public void releaseCar(Car c){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		c.setInUse(Boolean.FALSE);
		session.update(c);
		T.commit();
	}
	
	public Car getCar(String immatriculation){
		Car C=null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		C = (Car) session.createQuery("from Car where ID='"+immatriculation+"'").uniqueResult();
		T.commit();
		return C;
		
	}
	
}
