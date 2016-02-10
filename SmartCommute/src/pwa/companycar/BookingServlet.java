package pwa.companycar;

import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;



import pwa.sncf.SncfGare;

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
			SimpleDateFormat df=new SimpleDateFormat("YYYYMMDD-HHmm", Locale.getDefault());
			try{
				Date start=df.parse(request.getParameter("start"));
				Date end=df.parse(request.getParameter("end"));
				if(end.after(start)){
					Car C=carManager.getCar(request.getParameter("id"));
					if(C==null)response.getWriter().append("Le véhicule n'existe pas");	
					else{
						if(bookingManager.isCarBooked(C, start, end))response.getWriter().append("Le véhicule est réservé à cette période");	
						else{
							try{
								bookingManager.create(request.getParameter("name"), C,start, end, request.getParameter("reason"), request.getRemoteAddr());
								response.sendRedirect("booking?action=listbookings");
							}catch(Exception e){
								response.getWriter().append("Impossible d'ajouter cette réservation (Vérifier les données");
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
			
			SimpleDateFormat df=new SimpleDateFormat("dd/MM/YYYY HH:mm");
			try{
				//Date start=df.parse(request.getParameter("start"));
				//Date end=df.parse(request.getParameter("end"));
				String startdate=request.getParameter("start").substring(0, request.getParameter("start").indexOf(" "));
				int starttimehour=Integer.parseInt(request.getParameter("start").substring(request.getParameter("start").indexOf(" ")+1,request.getParameter("start").indexOf(":")));
				int starttimemin=Integer.parseInt(request.getParameter("start").substring(request.getParameter("start").indexOf(":")+1));
				String enddate=request.getParameter("end").substring(0, request.getParameter("end").indexOf(" "));
				int endtimehour=Integer.parseInt(request.getParameter("end").substring(request.getParameter("end").indexOf(" ")+1,request.getParameter("end").indexOf(":")));
				int endtimemin=Integer.parseInt(request.getParameter("end").substring(request.getParameter("end").indexOf(":")+1));
				logger.info("startdate "+startdate+ " /time="+starttimehour+"+"+starttimemin+"\n");
				logger.info("enddate="+enddate+ " /time="+endtimehour+"+"+endtimemin+"\n");
				Date start=DateFormat.getDateInstance(DateFormat.SHORT).parse(startdate);
				start.setTime(start.getTime()+((starttimehour*60)+starttimemin)*(60*1000));
				Date end=DateFormat.getDateInstance(DateFormat.SHORT).parse(enddate);
				end.setTime(end.getTime()+((endtimehour*60)+endtimemin)*(60*1000));
				if(end.after(start)){
					response.getWriter().append("Liste des véhicules dispo entre "+start+" et "+end+"\n");
					List<Car> list=carManager.getAvailableCars(start, end);
					for(Car C:list){
						response.getWriter().append(C+"\n");
					}
				}else response.getWriter().append("Dates incorrectes: "+start+" - "+end);
				
			}catch(ParseException e){
				response.getWriter().append("Dates incorrectes");
				e.printStackTrace();
			}
			catch(Exception e){
				
				e.printStackTrace();
			}
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

}
