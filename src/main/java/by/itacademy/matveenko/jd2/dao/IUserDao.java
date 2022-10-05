package by.itacademy.matveenko.jd2.dao;

import by.itacademy.matveenko.jd2.bean.User;

public interface IUserDao {	
	User findUserByLogin(String login);
	boolean saveUser(User user);
	User findById(Integer id);	
}