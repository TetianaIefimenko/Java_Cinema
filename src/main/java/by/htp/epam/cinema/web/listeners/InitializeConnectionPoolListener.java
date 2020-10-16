package by.htp.epam.cinema.web.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import by.htp.epam.cinema.db.pool.impl.CustomConnectionPool;


public class InitializeConnectionPoolListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		CustomConnectionPool.initializeConnectionPool();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		CustomConnectionPool.destroyConnectionPool();
	}
}
