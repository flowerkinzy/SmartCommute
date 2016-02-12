package pwa;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;

import pwa.companycar.Booking;
import pwa.companycar.BookingManager;
import pwa.companycar.Car;
import pwa.companycar.CarManager;
import pwa.sncf.SNCFGareManager;
import pwa.sncf.SncfGare;
import pwa.sncf.TrainManager;

/**
 * Servlet implementation class HomeServlet
 */
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BookingManager bookingManager=new BookingManager();
	CarManager carManager=new CarManager();  
	TrainManager trainManager=new TrainManager();
	SNCFGareManager gareManager = new SNCFGareManager();
	private Logger logger=LoggerFactory.logger(getClass());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher =null;
		if("cancelbookings".equals(request.getParameter("action"))){
			dispatcher=this.getServletContext().getRequestDispatcher("/WEB-INF/views/BookingsToCancel.jsp");
			List<Booking> bookings =bookingManager.listAllBookings();
			request.setAttribute("bookings",bookings);
			dispatcher.forward(request, response);
		}else if("cancelbooking".equals(request.getParameter("action"))){
			if(request.getParameter("id")==null)response.getWriter().append("Pas d'id renseigné");
			else{
			bookingManager.cancel(Long.parseLong(request.getParameter("id")));
			response.sendRedirect("admin?action=cancelbookings");
			}
		}
		dispatcher=this.getServletContext().getRequestDispatcher("/WEB-INF/Administration.jsp");
		dispatcher.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
