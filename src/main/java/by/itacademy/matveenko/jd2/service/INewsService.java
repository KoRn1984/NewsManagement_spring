package by.itacademy.matveenko.jd2.service;

import java.util.List;

import by.itacademy.matveenko.jd2.bean.News;

public interface INewsService {	  
	  List<News> latestList(int count)  throws ServiceException;
	  List<News> newsList(Integer pageNumber, Integer pageSize)  throws ServiceException;
	  News findById(Integer idNews) throws ServiceException;
	  boolean save(News news) throws ServiceException;
	  boolean update(News news) throws ServiceException;
	  boolean unpublishNewsById(String[] idNews) throws ServiceException;
	  boolean deleteNewsById(String[] idNews) throws ServiceException;
	  int countPage(int countNewsPage) throws ServiceException;
	}