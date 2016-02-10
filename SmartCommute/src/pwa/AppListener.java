package pwa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;

import util.HibernateUtil;

import java.util.Hashtable;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException; 

/**
 * Application Lifecycle Listener implementation class AppListener
 *
 */
public class AppListener implements ServletContextListener {

	private Logger logger=LoggerFactory.logger(getClass());
    /**
     * Default constructor. 
     */
    public AppListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	
    	peuplerSiBesoinTable();
    	
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }
	
    private void peuplerSiBesoinTable(){
    		
    		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    		Transaction T = session.beginTransaction();
    		try{
    			Long result = (Long) session.createQuery("select count(*) from SncfGare").list().get(0);
    			logger.info("Nombre de gares trouvées "+result);
    			//if(result>0)return;
    		}catch(Exception e){
    			Hashtable env = new Hashtable();
    	    	env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.fscontext.RefFSContextFactory"); 
    	    	String path="file:"+this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
    	    	path=path.substring(0,path.lastIndexOf("/"));
    	    	path=path.substring(0,path.lastIndexOf("/"));
    	    	path=path.substring(0,path.lastIndexOf("/"));
    	    	path=path.substring(0,path.lastIndexOf("/"));
    	    	env.put(Context.PROVIDER_URL, path+"/asserts/"); 
    			try {
    				Context	ctx = new InitialContext(env);
    				 
            		logger.info("\nContextNameInNAmespace :"+ctx.getNameInNamespace());
            		File fichier = (File) ctx.lookup("SNCFStation.sql");
            		logger.info("Fichier SQL: "+fichier.getAbsolutePath());
        			logger.info("Chargement de la table des stations (1min)");
        			
    				FileReader reader=new FileReader(fichier);
    				char[] chars=null;
    				int code;
    				String statement="";
    				while((code=reader.read())!=-1){
    					chars = Character.toChars(code);
						statement=statement+String.copyValueOf(chars);
    					if(code==';'){
    						session.createSQLQuery(statement).executeUpdate();
    						statement="";
    					}
    				}
    				logger.info("Fin du chargement de la table");
    			} catch (FileNotFoundException e1) {
    				// TODO Auto-generated catch block
    				logger.error("Echec du chargement de la table");
    				e1.printStackTrace();
    			} catch (IOException e1) {
    				// TODO Auto-generated catch block
    				logger.error("Echec du chargement de la table");
    				e1.printStackTrace();
    			}	
    			catch (NamingException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
    		}
    		
    		
    		T.commit();
    	
    	
    }
		
}
