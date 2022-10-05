package by.itacademy.matveenko.jd2.controller.impl;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.itacademy.matveenko.jd2.bean.News;
import by.itacademy.matveenko.jd2.service.INewsService;
import by.itacademy.matveenko.jd2.service.ServiceException;
import by.itacademy.matveenko.jd2.service.ServiceProvider;
import by.itacademy.matveenko.jd2.util.AttributsName;
import by.itacademy.matveenko.jd2.util.ConnectorStatus;
import by.itacademy.matveenko.jd2.util.JspPageName;
import by.itacademy.matveenko.jd2.util.PageUrl;
import by.itacademy.matveenko.jd2.controller.Command;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GoToBasePage implements Command{	
	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
	private static final Logger log = LogManager.getLogger(GoToBasePage.class);
	private static final int COUNT_NEWS = 5;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession getSession = request.getSession(true);
		List<News> latestNews;
				
		try {			
			latestNews = newsService.latestList(COUNT_NEWS);			
			request.setAttribute(AttributsName.NEWS, latestNews);
			getSession.setAttribute(AttributsName.PAGE_URL, PageUrl.BASE_PAGE);
		} catch (ServiceException e) {			
			log.error(e);
			response.sendRedirect(JspPageName.ERROR_PAGE);
		} finally {
			request.setAttribute(AttributsName.USER_STATUS, ConnectorStatus.NOT_ACTIVE);
			getSession.setAttribute(AttributsName.PAGE_URL, PageUrl.BASE_PAGE);
			request.getRequestDispatcher(JspPageName.BASELAYOUT_PAGE).forward(request, response);
		}
	}
}