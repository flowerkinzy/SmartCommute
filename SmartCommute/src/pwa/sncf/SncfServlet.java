package pwa.sncf;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/* Java 8
 * import java.util.Base64;
 * import java.util.Base64.Encoder;
 */

import java.util.List;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;



import sun.misc.BASE64Encoder;

/**
 * Servlet implementation class SncfServlet
 */
@WebServlet("/sncf")
public class SncfServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SncfServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SncfGare gare= new SncfGare();
		URL url = new URL("http://api.transilien.com/gare/87384008/depart/");
		//String encoding = Base64.getEncoder().encodeToString("tnhtn394:LgS43b5m".getBytes("utf-8")); //Java 8
		String encoding = DatatypeConverter.printBase64Binary("tnhtn394:LgS43b5m".getBytes("utf-8"));
		
        System.out.println("Base64 Encoded String (Basic) :" + encoding);
		HttpURLConnection connexion = (HttpURLConnection) url.openConnection();
		connexion.setRequestMethod("GET");
		connexion.setDoOutput(true);
		connexion.setRequestProperty("Authorization", "Basic " + encoding);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		  DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(connexion.getInputStream());
		
		  List<Train> trains = new ArrayList<Train>();     
		  NodeList fils = doc.getElementsByTagName("train");
		  for (int tmp = 0; tmp < fils.getLength(); tmp++) {      
		    Node nNode = fils.item(tmp);
		    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		      Element eElement = (Element)nNode;        
		      Train t = new Train();               
		      t.setDateDepart(new SimpleDateFormat("dd/MM/yyyy       HH:mm").parse(eElement.getElementsByTagName("date").item(0).getTextContent()));
		      t.setNom(eElement.getElementsByTagName("miss").item(0).getTextContent());
		      trains.add(t);
		      
		      gare.setTrains(trains);
		    }
		  }
		} catch (DOMException e) {
				e.printStackTrace();
		} catch (ParseException e) {
				e.printStackTrace();
		} catch (ParserConfigurationException e1) {
				e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		  
		response.getWriter().append("Served at: ").append(request.getContextPath());  
		
		for(int i=0;i<gare.getTrains().size();i++){
			  //System.out.println(gare.getNom());
			  //System.out.println(gare.getTrains().get(i).getDateDepart());
			  response.getWriter().append(gare.getNom()+"<br/>"+gare.getTrains().get(i).getDateDepart());
		  }
		    
		

	
	}	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
