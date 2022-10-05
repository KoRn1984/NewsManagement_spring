package by.itacademy.matveenko.jd2.dao;

import by.itacademy.matveenko.jd2.dao.DaoProvider;
import by.itacademy.matveenko.jd2.dao.impl.NewsDao;
import by.itacademy.matveenko.jd2.dao.impl.UserDao;

public final class DaoProvider {
	private static final DaoProvider instance = new DaoProvider();
	private final IUserDao userDao = new UserDao();
	private final INewsDao newsDao = new NewsDao();	
	
	private DaoProvider() {}
	
	public static DaoProvider getInstance() {
		return instance;
	}
	
	public IUserDao getUserDao() {
		return userDao;
	}
	
	public INewsDao getNewsDao() {
		return newsDao;
	}
}