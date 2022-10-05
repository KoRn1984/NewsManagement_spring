package by.itacademy.matveenko.jd2.dao;

import java.util.List;

import by.itacademy.matveenko.jd2.bean.News;

public interface INewsDao {
	List<News> getLatestList(int pageSize);
	List<News> getNewsList(Integer pageNumber, Integer pageSize);
	News fetchById(Integer idNews) throws NewsDaoException;
	int addNews(News news) throws NewsDaoException;
	boolean updateNews(News news) throws NewsDaoException;
	boolean unpublishNews(String[] idNews) throws NewsDaoException;
	boolean deleteNews(String[] idNews) throws NewsDaoException;
	int countNews() throws NewsDaoException;	
}