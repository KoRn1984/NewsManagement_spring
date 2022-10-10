package by.itacademy.matveenko.jd2.controller.impl;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.itacademy.matveenko.jd2.bean.News;
import by.itacademy.matveenko.jd2.controller.Command;
import by.itacademy.matveenko.jd2.service.INewsService;
import by.itacademy.matveenko.jd2.service.ServiceException;
import by.itacademy.matveenko.jd2.service.ServiceProvider;
import by.itacademy.matveenko.jd2.util.AttributsName;
import by.itacademy.matveenko.jd2.util.JspPageName;
import by.itacademy.matveenko.jd2.util.NewsParameterName;
import by.itacademy.matveenko.jd2.util.PageUrl;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GoToViewNews implements Command {	
	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
	//private static final Logger log = LogManager.getLogger(GoToViewNews.class);
			
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		News news = null;
		
		HttpSession getSession = request.getSession(false);
		if (getSession == null) {
			response.sendRedirect(JspPageName.INDEX_PAGE);
		} else {
			String id = request.getParameter(NewsParameterName.JSP_ID_NEWS);
			news = newsService.findById(Integer.parseInt(id));
			StringBuilder urlForRedirect = new StringBuilder(PageUrl.VIEW_NEWS);
			urlForRedirect.append(id);
			if (news == null) {
				response.sendRedirect(JspPageName.ERROR_PAGE);
				} else {
					request.setAttribute(AttributsName.NEWS, news);
					request.setAttribute(AttributsName.PRESENTATION, AttributsName.VIEW_NEWS);
					getSession.setAttribute(AttributsName.PAGE_URL, urlForRedirect.toString());
					request.getRequestDispatcher(JspPageName.BASELAYOUT_PAGE).forward(request, response);
					}
			}
		}
}