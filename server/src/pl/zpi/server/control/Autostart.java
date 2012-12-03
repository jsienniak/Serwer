package pl.zpi.server.control;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * Klasa pozwalająca na wystartowanie pewnych elementów nawet kiedy aplikacja się nie załadowała
 * @author mm
 *
 */
public class Autostart implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Servlet context listener: initialized");

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("Servlet context listener: destroyed");

	}

}
