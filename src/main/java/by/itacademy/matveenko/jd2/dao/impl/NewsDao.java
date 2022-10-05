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
import org.hibernate.query.SelectionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import by.itacademy.matveenko.jd2.bean.News;
import by.itacademy.matveenko.jd2.dao.DaoException;
import by.itacademy.matveenko.jd2.dao.INewsDao;
import by.itacademy.matveenko.jd2.dao.NewsDaoException;
import by.itacademy.matveenko.jd2.dao.connectionpool.ConnectionPool;
import by.itacademy.matveenko.jd2.dao.connectionpool.ConnectionPoolException;
import by.itacademy.matveenko.jd2.util.HibernateSessionFactoryUtil;
import by.itacademy.matveenko.jd2.util.NewsParameterName;

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
	
	private static final String SELECT_NEWS_LATEST_LIST = "SELECT * FROM News WHERE published = :paramPublished ORDER BY date DESC LIMIT paramLimit";
	@Override
	public List<News> getLatestList(int pageSize) {
		List<News> newsLatestList = new ArrayList<>();
		int startSize = pageSize;
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        SelectionQuery<News> query = session.createSelectionQuery(SELECT_NEWS_LATEST_LIST, News.class);
        query.setParameter("paramPublished", NEWS_PUBLISHED);
        query.setParameter("paramLimit", startSize);
        List<News> latestNews = query.getResultList();
        tx.commit();
        session.close();
        return newsLatestList;
	}			

	private static final String SELECT_NEWS_LIST = "SELECT * FROM News WHERE published = :paramPublished ORDER BY date DESC LIMIT paramLimit, paramPage";
	@Override
	public List<News> getNewsList(Integer pageNumber, Integer pageSize) {
		List<News> newsList = new ArrayList<>();
		int startSize = (pageNumber - 1) * pageSize;			 
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        SelectionQuery<News> query = session.createSelectionQuery(SELECT_NEWS_LIST, News.class);
        query.setParameter("paramPublished", NEWS_PUBLISHED);
        query.setParameter("paramLimit", startSize);
        query.setParameter("paramPage", pageSize);
        List<News> news = query.getResultList();
        tx.commit();
        session.close();
        return newsList;
	}		
	
	private static final String SELECT_NEWS_BY_ID = "SELECT * FROM news WHERE id = ?";
	@Override
	public News fetchById(Integer idNews) throws NewsDaoException {
		News news = null;		
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
	        PreparedStatement ps = connection.prepareStatement(SELECT_NEWS_BY_ID)) {
			ps.setInt(1, idNews);
			try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {                	
                	news = new News.Builder()
    						.withId(rs.getInt(NewsParameterName.JSP_ID_NEWS))
                            .withTitle(rs.getString(NewsParameterName.JSP_TITLE_NEWS))
                            .withBrief(rs.getString(NewsParameterName.JSP_BRIEF_NEWS))
                            .withContent(rs.getString(NewsParameterName.JSP_CONTENT_NEWS))
                            .withDate(LocalDate.parse(rs.getString(NewsParameterName.JSP_DATE_NEWS)))
                            .withAuthor(userDao.findById(rs.getInt(NewsParameterName.JSP_ID_REPORTER)))
                            .build();
    				}
                }
			} catch (SQLException | ConnectionPoolException | DaoException e) {				
				throw new NewsDaoException(e);
			}
			return news;
	}
	
	private static final String INSERT_NEWS = "INSERT INTO news(title, brief, content, date, reporter, published) VALUES (?, ?, ?, ?, ?, ?)";
	@Override
	public int addNews(News news) throws NewsDaoException {
		int row = 0;		
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		    PreparedStatement ps = connection.prepareStatement(INSERT_NEWS)) {
			ps.setString(1, news.getTitle());
            ps.setString(2, news.getBrief());
            ps.setString(3, news.getContent());            
            ps.setString(4, LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
            ps.setInt(5, news.getAuthor().getId());
            ps.setString(6, NEWS_PUBLISHED);
            row = ps.executeUpdate();
            if (row == 0) {
				throw new NewsDaoException(MESSAGE_EXCEPTION);
			}            
				} catch (SQLException | ConnectionPoolException e) {					
					throw new NewsDaoException(e);
				}
				return row;
		}
	
	private static final String UPDATE_NEWS = "UPDATE news SET title = ?, brief = ?, content = ?, date = ?, reporter = ? WHERE id = ?";
	@Override
	public boolean updateNews(News news) throws NewsDaoException {
		int row = 0;		
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		    PreparedStatement ps = connection.prepareStatement(UPDATE_NEWS)) {
			ps.setString(1, news.getTitle());
			ps.setString(2, news.getBrief());
			ps.setString(3, news.getContent());
			ps.setString(4, LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
            ps.setInt(5, news.getAuthor().getId());
            ps.setInt(6, news.getId());
			row = ps.executeUpdate();
			if (row == 0) {
				throw new NewsDaoException(MESSAGE_EXCEPTION);
				}
			        return true;
			        } catch (SQLException | ConnectionPoolException e) {						
						throw new NewsDaoException(e);
					}					
	}
	
	private static final String UNPUBLISH_NEWS = "UPDATE news SET published = ? WHERE id = ?";
	@Override
	public boolean unpublishNews(String[] idNews) throws NewsDaoException {
		int row = 0;		
		try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
			try {
				connection.setAutoCommit(false);
		        PreparedStatement ps = connection.prepareStatement(UNPUBLISH_NEWS);
		        for (String id : idNews) {
		        	ps.setString(1, NEWS_UNPUBLISHED);
		        	ps.setInt(2, Integer.parseInt (id));
		        	row = ps.executeUpdate();
		        	if (row == 0) {
		        		return false;
			    	}
			    }
		        connection.commit();
		        connection.setAutoCommit(true);
		        } catch (SQLException e) {
		        	connection.rollback();		        			        	
		        	}
			} catch (SQLException | ConnectionPoolException e) {
				throw new NewsDaoException(e);
				}
		return true;
	}

	private static final String DELETE_NEWS = "DELETE FROM news WHERE id IN (?)";
	@Override
	public boolean deleteNews(String[] idNews) throws NewsDaoException {
		int row = 0;		
		try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
			try {
				connection.setAutoCommit(false);
		        PreparedStatement ps = connection.prepareStatement(DELETE_NEWS);
		        for (String id : idNews) {
			    ps.setInt(1, Integer.parseInt (id));
			    row = ps.executeUpdate();
			    if (row == 0) {
			    	return false;
			    	}
			    }
		        connection.commit();
		        connection.setAutoCommit(true);
		        } catch (SQLException e) {
		        	connection.rollback();		        			        	
		        	}
			} catch (SQLException | ConnectionPoolException e) {
				throw new NewsDaoException(e);
				}
		return true;
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