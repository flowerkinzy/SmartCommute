package pwa.companycar;

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
		session.save(car);
		T.commit();
		return car;
	}
	
	public List<Car> getAvailableCars(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction T = session.beginTransaction();
		List<Car> list=session.createQuery("from Car where state='S' or state='D'").list();
		T.commit();
		return list;
	}
	
	
	
}
