package by.itacademy.matveenko.jd2.service;

import by.itacademy.matveenko.jd2.bean.User;

public interface IUserService {	
	User signIn(String login, String password) throws ServiceException;
	boolean registration(User user) throws ServiceException;
}