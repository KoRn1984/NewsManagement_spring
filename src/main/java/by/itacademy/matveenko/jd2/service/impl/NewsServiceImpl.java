package by.itacademy.matveenko.jd2.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.itacademy.matveenko.jd2.bean.News;
import by.itacademy.matveenko.jd2.dao.INewsDao;
import by.itacademy.matveenko.jd2.dao.NewsDaoException;
import by.itacademy.matveenko.jd2.service.INewsService;
import by.itacademy.matveenko.jd2.service.ServiceException;
import by.itacademy.matveenko.jd2.util.validation.NewsDataValidation;

@Service
public class NewsServiceImpl implements INewsService{
	private static final String NEWS_PUBLISHED = "yes";
	private static final String NEWS_UNPUBLISHED = "no";
	
	@Autowired
	private INewsDao newsDao;
	
	@Override
	@Transactional
	public List<News> latestList(int count) {		
		return newsDao.getLatestList(5);
	}

	@Override
	@Transactional
	public List<News> newsList(Integer pageNumber, Integer pageSize) {
		return newsDao.getNewsList(pageNumber, pageSize);
	}

	@Override
	@Transactional
	public News findById(Integer idNews) {
		return newsDao.fetchById(idNews);
	}
	
	@Override
	@Transactional
	public boolean save(News news) throws ServiceException {
		news.setPublished(NEWS_PUBLISHED);
		NewsDataValidation.ValidBuilder valid = new NewsDataValidation.ValidBuilder();			
		NewsDataValidation validNewsData = valid.titleValid(news.getTitle())
				.briefValid(news.getBrief())
				.contentValid(news.getContent())
				.dateValid(news.getDate())
				.authorValid(news.getAuthor())
				.build();
		if(!validNewsData.getDataValid().isEmpty()) {
			throw new ServiceException("The entered news data is not valid!");
		} else if (!(newsDao.addNews(news))) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public boolean update(News news) throws ServiceException {
		NewsDataValidation.ValidBuilder valid = new NewsDataValidation.ValidBuilder();			
		NewsDataValidation validNewsData = valid.titleValid(news.getTitle())
				.briefValid(news.getBrief())
				.contentValid(news.getContent())					
				.dateValid(news.getDate())
				.authorValid(news.getAuthor())
				.build();
		if(!validNewsData.getDataValid().isEmpty()) {
			throw new ServiceException("The entered news data is not valid!");
		} else if (!(newsDao.updateNews(news))) {
			return false;
		}
		return true;
	}
	
	@Override
	@Transactional
	public boolean unpublishNewsById(String[] idNews) throws ServiceException {
		try {
			if (!(newsDao.unpublishNews(idNews))) {
				return false;
			}
			return true;
		} catch (NewsDaoException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	@Transactional
	public boolean deleteNewsById(String[] idNews) throws ServiceException {		
		try {
			if (!(newsDao.deleteNews(idNews))) {							
				return false;
			}
			return true;
		} catch (NewsDaoException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	@Transactional
	public int countPage(int countNewsPage) throws ServiceException {
		try {
			int countNews = newsDao.countNews();
			if (countNews == 0) {
				return 0;
			}
			return (int) Math.ceil(countNews / (double) countNewsPage);
		} catch (NewsDaoException e) {
			throw new ServiceException(e);
		}
	}
}