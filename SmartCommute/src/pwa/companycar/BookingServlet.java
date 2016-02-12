package pwa.companycar;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;

/**
 * Servlet implementation class BookingServlet
 */
public class BookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BookingManager bookingManager=new BookingManager();
	CarManager carManager=new CarManager();   
	private Logger logger=LoggerFactory.logger(getClass());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher=null;
		// TODO Auto-generated method stub
		logger.info("doGet : request="+request.getRequestURI());
		if("listcars".equals(request.getParameter("action"))){
			response.getWriter().append("Liste de toutes les voitures\n");
			List<Car> list=carManager.getAllCars();
			for(Car C:list){
				response.getWriter().append(C+"\n");
			}
		}else if("listbookings".equals(request.getParameter("action"))){
			response.getWriter().append("Liste de toutes les réservations\n");
			List<Booking> list=bookingManager.listAllBookings();
			for(Booking B:list){
				response.getWriter().append(B+"\n");
			}
		}
		else if("listpendingbookings".equals(request.getParameter("action"))){
			List<Booking> list=bookingManager.listPendingBookings();
			for(Booking B:list){
				response.getWriter().append(B+"\n");
			}
		}
		else if("addcar".equals(request.getParameter("action"))){
			try{
				carManager.createCar(request.getParameter("id"), request.getParameter("desc"), Integer.parseInt(request.getParameter("nb")));
				response.sendRedirect("booking");
			}catch(NumberFormatException e){
				response.getWriter().append("Nombre de places incorrect");				
			}
			catch(Exception e){
				response.getWriter().append("Impossible d'ajouter ce véhicule (Vérifier les données");
			}
			
		}
		else if("addbooking".equals(request.getParameter("action"))){
			try{
				logger.info("start (string)="+request.getParameter("start")+"\n");
				logger.info("end (string)="+request.getParameter("end"));
				Date start=this.extractDateTimeFrom(request.getParameter("start"));
				Date end=this.extractDateTimeFrom(request.getParameter("end"));
				if(end.after(start)){
					Car C=carManager.getCar(request.getParameter("id"));
					if(C==null)response.getWriter().append("Le véhicule n'existe pas");	
					else{
						if(bookingManager.isCarBooked(C, start, end))response.getWriter().append("Le véhicule est réservé à cette période");	
						else{
							try{
								bookingManager.create(request.getParameter("name"), C,start, end, request.getParameter("reason"), request.getRemoteAddr());
								response.sendRedirect("home");
							}catch(Exception e){
								response.getWriter().append("Impossible d'ajouter cette réservation (Vérifier les données");
								e.printStackTrace();
							}
						}
					}
				}else response.getWriter().append("Dates incorrectes");
				
			}catch(ParseException e){
				response.getWriter().append("Dates incorrectes");
			}		
		}
		else if("listavailablecars".equals(request.getParameter("action"))){
			if(request.getParameter("start")!=null && request.getParameter("end")!=null){
				dispatcher =this.getServletContext().getRequestDispatcher("/WEB-INF/views/CarsToBook.jsp");
				try{
					Date start=this.extractDateTimeFrom(request.getParameter("start"));
					Date end=this.extractDateTimeFrom(request.getParameter("end"));
					if(end.after(start)){	
						List<Car> list=carManager.getAvailableCars(start, end);
						request.setAttribute("availablecars",list);

					}else{
						response.getWriter().append("Dates incorrectes: "+start+" - "+end);
						request.setAttribute("dateerror",Boolean.TRUE);
					}

				}catch(ParseException e){
					response.getWriter().append("Dates incorrectes");
					request.setAttribute("dateerror",Boolean.TRUE);
					e.printStackTrace();
				}
				dispatcher.forward(request, response);
			}else{
				List<Car> list=carManager.getCurrentAvailableCars();
				for(Car C:list){
					response.getWriter().append(C+"\n");
				}
			}
		
				
			
			
		}
		else if("listbookablecars".equals(request.getParameter("action"))){
			List<Car> list=carManager.getBookableCars();
			for(Car C:list){
				response.getWriter().append(C+"\n");
			}
		}
		else{
			dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/Reservation.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	protected Date extractDateTimeFrom(String datetime) throws ParseException{
		//Pattern is "dd/MM/YYYY HH:mm"
		String day=datetime.substring(0, datetime.indexOf(" "));
		int timehour=Integer.parseInt(datetime.substring(datetime.indexOf(" ")+1,datetime.indexOf(":")));
		int timemin=Integer.parseInt(datetime.substring(datetime.indexOf(":")+1));
		logger.info("date "+day+ " /time="+timehour+"+"+timemin+"\n");
		Date date=DateFormat.getDateInstance(DateFormat.SHORT).parse(day);
		date.setTime(date.getTime()+((timehour*60)+timemin)*(60*1000));
		return date;
	}

}
