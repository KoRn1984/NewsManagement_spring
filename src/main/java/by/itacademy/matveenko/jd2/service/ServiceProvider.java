package by.itacademy.matveenko.jd2.service;

import by.itacademy.matveenko.jd2.service.impl.NewsServiceImpl;
import by.itacademy.matveenko.jd2.service.impl.UserServiceImpl;

public final class ServiceProvider {
	private static final ServiceProvider instance = new ServiceProvider();
	private final IUserService userService = new UserServiceImpl();
	private final INewsService newsService = new NewsServiceImpl();
	
	private ServiceProvider() {}
	
	public static ServiceProvider getInstance() {
		return instance;
	}	
	
	public IUserService getUserService() {
		return userService;
	}	
	
	public INewsService getNewsService() {
		return newsService;
	}		
}