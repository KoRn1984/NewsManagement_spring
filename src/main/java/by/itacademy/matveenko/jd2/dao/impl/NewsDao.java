package by.itacademy.matveenko.jd2.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.query.SelectionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import by.itacademy.matveenko.jd2.bean.News;
import by.itacademy.matveenko.jd2.dao.INewsDao;
import by.itacademy.matveenko.jd2.dao.NewsDaoException;
import by.itacademy.matveenko.jd2.dao.connectionpool.ConnectionPool;
import by.itacademy.matveenko.jd2.dao.connectionpool.ConnectionPoolException;
import by.itacademy.matveenko.jd2.util.HibernateSessionFactoryUtil;

@Repository
public class NewsDao implements INewsDao {
	private static final String NEWS_PUBLISHED = "yes";
	private static final String NEWS_UNPUBLISHED = "no";
	private static final String DATE_PATTERN = "yyyy-MM-dd";
	private static final String MESSAGE_EXCEPTION = "News not saved!";
	
	private SessionFactory sessionFactory;
	
	@Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	@Autowired
	private UserDao userDao;
	
	private static final String SELECT_NEWS_LATEST_LIST = "SELECT * FROM News WHERE published = :paramPublished ORDER BY date DESC LIMIT :paramStart";
	@Override
	public List<News> getLatestList(int pageSize) {
		List<News> newsLatestList = new ArrayList<>();
		int startSize = pageSize;
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        SelectionQuery<News> query = session.createSelectionQuery(SELECT_NEWS_LATEST_LIST, News.class);
        query.setParameter("paramPublished", NEWS_PUBLISHED);
        query.setParameter("paramStart", startSize);
        List<News> latestNews = query.getResultList();
        tx.commit();
        session.close();
        return newsLatestList;
	}			

	private static final String SELECT_NEWS_LIST = "SELECT * FROM News WHERE published = :paramPublished ORDER BY date DESC LIMIT :paramStart, :paramPage";
	@Override
	public List<News> getNewsList(Integer pageNumber, Integer pageSize) {
		List<News> newsList = new ArrayList<>();
		int startSize = (pageNumber - 1) * pageSize;		 
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        SelectionQuery<News> query = session.createSelectionQuery(SELECT_NEWS_LIST, News.class);
        query.setParameter("paramPublished", NEWS_PUBLISHED);
        query.setParameter("paramStart", startSize);
        query.setParameter("paramPage", pageSize);
        List<News> news = query.getResultList();
        tx.commit();
        session.close();
        return newsList;
	}
	
	@Override
	public News fetchById(Integer idNews) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        News news = session.get(News.class, idNews);
        tx.commit();
        session.close();
        return news;
	}
	
	@Override
	public boolean addNews(News news) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(news);
        tx.commit();
        session.close();
        return true;
	}
	
	@Override
	public boolean updateNews(News news) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.refresh(news);
        tx.commit();
        session.close();
        return true;
	}
	
	private static final String UNPUBLISH_NEWS = "UPDATE News SET published = :paramPublished WHERE id = :ids";
	@Override
	public boolean unpublishNews(String[] idNews) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<String> ids = new ArrayList<>();
        for (String id : idNews) {
        	ids.add(id);
        }
        session.flush();
        session.clear();
        Query<News> query = session.createQuery(UNPUBLISH_NEWS, News.class);
        query.setParameter("paramPublished", NEWS_UNPUBLISHED);
        query.setParameter("ids", ids);
        query.executeUpdate();
        tx.commit();
        session.close();
        return true;
	}
	
	private static final String DELETE_NEWS = "DELETE FROM News WHERE id IN (:ids)";
	@Override
	public boolean deleteNews(String[] idNews) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<String> ids = new ArrayList<>();
        for (String id : idNews) {
        	ids.add(id);
        }
        session.flush();
        session.clear();
        Query<News> query = session.createQuery(DELETE_NEWS, News.class);
        query.setParameter("ids", ids);
        query.executeUpdate();
        tx.commit();
        session.close();
        return true;
	}
	
	@Override
	public int countNews() {
		
	}

	private static final String SELECT_COUNT_NEWS = "SELECT COUNT(*) FROM news WHERE published = ?";
	@Override
	public int countNews() throws NewsDaoException {
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		    PreparedStatement ps = connection.prepareStatement(SELECT_COUNT_NEWS)) {
			ps.setString(1, NEWS_PUBLISHED);
				try (ResultSet rs = ps.executeQuery()) {
					if(!rs.next()) {
	                	return 0;
	                	}
					return rs.getInt(1);
					}
				} catch (SQLException | ConnectionPoolException e) {
					throw new NewsDaoException(e);
					}	
	}
}