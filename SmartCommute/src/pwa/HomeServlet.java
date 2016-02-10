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

import pwa.companycar.BookingManager;
import pwa.companycar.Car;
import pwa.companycar.CarManager;
import pwa.sncf.SNCFGareManager;
import pwa.sncf.SncfGare;
import pwa.sncf.TrainManager;

/**
 * Servlet implementation class HomeServlet
 */
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BookingManager bookingManager=new BookingManager();
	CarManager carManager=new CarManager();  
	TrainManager trainManager=new TrainManager();
	SNCFGareManager gareManager = new SNCFGareManager();
	private Logger logger=LoggerFactory.logger(getClass());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/Accueil.jsp");
		List<Car>cars=carManager.getCurrentAvailableCars();
		request.setAttribute("availablecars",cars);
		
		request.setAttribute("nextTrains", trainManager.prochainsTrainsToutesGaresProches(5));
		request.setAttribute("listOfstations", 0);
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
