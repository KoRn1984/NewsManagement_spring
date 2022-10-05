package by.itacademy.matveenko.jd2.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import by.itacademy.matveenko.jd2.bean.News;
import by.itacademy.matveenko.jd2.dao.DaoProvider;
import by.itacademy.matveenko.jd2.dao.INewsDao;
import by.itacademy.matveenko.jd2.dao.NewsDaoException;
import by.itacademy.matveenko.jd2.service.INewsService;
import by.itacademy.matveenko.jd2.service.ServiceException;
import by.itacademy.matveenko.jd2.util.validation.NewsDataValidation;

@Service
public class NewsServiceImpl implements INewsService{
	private final INewsDao newsDao = DaoProvider.getInstance().getNewsDao();
	
	@Override
	public List<News> latestList(int count) throws ServiceException {		
		try {
			return newsDao.getLatestList(5);
		} catch (NewsDaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<News> newsList(Integer pageNumber, Integer pageSize) throws ServiceException {
		try {
			return newsDao.getNewsList(pageNumber, pageSize);
		} catch (NewsDaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public News findById(Integer idNews) throws ServiceException {
		try {
			return newsDao.fetchById(idNews);
		} catch (NewsDaoException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public boolean save(News news) throws ServiceException {
		try {
			NewsDataValidation.ValidBuilder valid = new NewsDataValidation.ValidBuilder();			
			NewsDataValidation validNewsData = valid.titleValid(news.getTitle())
					.briefValid(news.getBrief())
					.contentValid(news.getContent())
					.authorValid(news.getAuthor())
					.dateValid(news.getDate())
					.build();
			if(!validNewsData.getDataValid().isEmpty()) {
				throw new ServiceException("The entered news data is not valid!");
			} else if (newsDao.addNews(news) == 0) {
				return false;
			}
			return true;
		} catch (NewsDaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean update(News news) throws ServiceException {
		try {
			NewsDataValidation.ValidBuilder valid = new NewsDataValidation.ValidBuilder();			
			NewsDataValidation validNewsData = valid.titleValid(news.getTitle())
					.briefValid(news.getBrief())
					.contentValid(news.getContent())
					.authorValid(news.getAuthor())
					.dateValid(news.getDate())
					.build();
			if(!validNewsData.getDataValid().isEmpty()) {
				throw new ServiceException("The entered news data is not valid!");
			} else if (!(newsDao.updateNews(news))) {
				return false;
			}
			return true;
		} catch (NewsDaoException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
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