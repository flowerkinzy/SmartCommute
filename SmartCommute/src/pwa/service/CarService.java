package pwa.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;

import pwa.companycar.Car;
import pwa.companycar.CarManager;

@Path("/car")
public class CarService {
	CarManager carManager = new CarManager();
	private Logger logger=LoggerFactory.logger(getClass());

	   @GET
	   @Path("/list/bookable")
	   @Produces(MediaType.APPLICATION_JSON)
	   public Response getAvailableCars(){
		   logger.info("Récupération de toutes les voitures pouvant être réservées");
		   try{
			   return Response.ok(new GenericEntity<List<Car>>(carManager.getBookableCars()){}).build();  
		   }catch(Exception e){
			   logger.error("erreur lors de la récupération des voitures");
			   e.printStackTrace();
			   return Response.serverError().build();
		   }
		   
	   }	
	   
	   @GET
	   @Path("/list")
	   @Produces(MediaType.APPLICATION_JSON)
	   public Response getAllCars(){
		   logger.info("Récupération de toutes les voitures");
		   try{
			   return Response.ok(new GenericEntity<List<Car>>(carManager.getAllCars()){}).build();  
		   }catch(Exception e){
			   logger.error("erreur lors de la récupération des voitures");
			   e.printStackTrace();
			   return Response.serverError().build();
		   }
	   }	
}
