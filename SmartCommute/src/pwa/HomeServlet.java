package pwa;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;

import pwa.companycar.BookingManager;
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
		request.setAttribute("availablecars",carManager.getCurrentAvailableCars());
		
		SncfGare G=gareManager.UICToGare("87384008");
		request.setAttribute("nextTrains", trainManager.prochainsTrains(G));
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
