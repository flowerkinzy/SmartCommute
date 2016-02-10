package pwa.companycar;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import util.HibernateUtil;

public class CarManager {
	
	public static void main(String[] args){
		CarManager carDAO = new CarManager();
		Car C=carDAO.createCar("XVF","Twingo noire",5);
		carDAO.createCar("XXO","Renault Clio C4 rouge",5);
	}

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
		T.commit();
		return list;
	}
	
	public List<Car> getAvailableCars(Date start,Date end){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		Timestamp startT = new Timestamp(start.getTime());
		Timestamp endT = new Timestamp(start.getTime());
		List<Car> list=session.createQuery("from Car where NOT(state='O') AND (SELECT COUNT(*) FROM Booking WHERE (start_time<='"+startT+"' AND end_time>'"+startT+"') OR (start_time<'"+endT+"' AND end_time>='"+endT+"') OR (start_time>='"+startT+"' AND end_time<='"+endT+"')=0").list();
		T.commit();
		return list;
	}
	
	
	public List<Car> getCurrentAvailableCars(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		Date date=new Date();
		Timestamp now = new Timestamp(1000*60*(date.getTime()/(1000*60)));
		List<Car> list=session.createQuery("from Car where NOT(state='O') AND inUse=0 AND (SELECT COUNT(*) FROM Booking WHERE (start_time<='"+now+"' AND end_time>='"+now+"'))=0").list();
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
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		Iterator<Car> it = session.createQuery("from Car where ID='"+immatriculation+"'").iterate();
		T.commit();
		if(it.hasNext())return it.next();
		else return null;
		
	}
	
}
