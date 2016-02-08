package pwa.service;


import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pwa.companycar.Car;
import pwa.companycar.CarManager;

@Path("/car")
public class CarService {
	CarManager carManager = new CarManager();

	   @GET
	   @Path("/list/available")
	   @Produces(MediaType.APPLICATION_JSON)
	   public List<Car> getAvailableCars(){
	      return carManager.getAvailableCars();
	   }	
}
