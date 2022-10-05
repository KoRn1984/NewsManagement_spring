package by.itacademy.matveenko.jd2.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import by.itacademy.matveenko.jd2.bean.User;
import by.itacademy.matveenko.jd2.dao.IUserDao;
import by.itacademy.matveenko.jd2.util.HibernateSessionFactoryUtil;

@Repository
public class UserDao implements IUserDao {
    private SessionFactory sessionFactory;
	
	@Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	@Override
	public User findUserByLogin(String login) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        User user = session.get(User.class, login);
        tx.commit();
        session.close();
        return user;
    }
	
	@Override
    public boolean saveUser(User user) {        
    	Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(user);
        tx.commit();
        session.close();
        return true;
    }
      
    @Override
    public User findById(Integer id) {    	
    	Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        User user = session.get(User.class, id);
        tx.commit();
        session.close();
        return user;
    }    
}