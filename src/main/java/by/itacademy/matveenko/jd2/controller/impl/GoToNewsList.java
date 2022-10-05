package by.itacademy.matveenko.jd2.controller.impl;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.itacademy.matveenko.jd2.bean.News;
import by.itacademy.matveenko.jd2.controller.Command;
import by.itacademy.matveenko.jd2.service.INewsService;
import by.itacademy.matveenko.jd2.service.ServiceException;
import by.itacademy.matveenko.jd2.service.ServiceProvider;
import by.itacademy.matveenko.jd2.util.AttributsName;
import by.itacademy.matveenko.jd2.util.JspPageName;
import by.itacademy.matveenko.jd2.util.PageUrl;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GoToNewsList implements Command {	
	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
	private static final Logger log = LogManager.getLogger(GoToNewsList.class);
	private static final String PAGE_NUMBER = "pageNo";
	private static final String CURRENT_PAGE = "currentPage";
	private static final String COUNT_PAGE = "countPage";
			
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession getSession = request.getSession(true);
		List<News> newsList;
		Integer pageNumber;
		Integer pageSize = 5;
		Integer countPage = 0;
		
		try {
			pageNumber = Integer.parseInt(request.getParameter(PAGE_NUMBER));
		} catch (NumberFormatException e) {
			pageNumber = 1;
		}		
		try {
			newsList = newsService.newsList(pageNumber, pageSize);
			countPage = newsService.countPage(pageSize);
			request.setAttribute(CURRENT_PAGE, pageNumber);
			request.setAttribute(COUNT_PAGE, countPage);
			request.setAttribute(AttributsName.NEWS, newsList);
			request.setAttribute(AttributsName.PRESENTATION, AttributsName.NEWS_LIST);
			getSession.setAttribute(AttributsName.PAGE_URL, PageUrl.NEWS_LIST_PAGE);
			request.getRequestDispatcher(JspPageName.BASELAYOUT_PAGE).forward(request, response);			
		} catch (ServiceException e) {
			log.error(e);
			response.sendRedirect(JspPageName.ERROR_PAGE);
		}		
	}
}