package by.itacademy.matveenko.jd2.controller.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.itacademy.matveenko.jd2.dao.connectionpool.ConnectionPool;
import by.itacademy.matveenko.jd2.dao.connectionpool.ConnectionPoolException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class NewsManagementContextListener implements ServletContextListener {
	private static final Logger log = LogManager.getRootLogger();
		
	public void contextInitialized(ServletContextEvent event) {
		try {
			ConnectionPool.getInstance().initPoolData();
		} catch (ConnectionPoolException e) {
			log.error(e);
			throw new RuntimeException(e);
		} 
	}

	public void contextDestroyed(ServletContextEvent event) {
		try {
			ConnectionPool.getInstance().dispose();
		} catch (ConnectionPoolException e) {
			log.error(e);
			throw new RuntimeException(e);
		}
	}
}