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
import by.itacademy.matveenko.jd2.util.ConnectorStatus;
import by.itacademy.matveenko.jd2.util.JspPageName;
import by.itacademy.matveenko.jd2.util.NewsParameterName;
import by.itacademy.matveenko.jd2.util.PageUrl;
import by.itacademy.matveenko.jd2.util.UserRole;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GoToEditNewsPage implements Command {
	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
	private static final Logger log = LogManager.getLogger(GoToEditNewsPage.class);
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		News news = null;
		
		try {
			HttpSession getSession = request.getSession(false);
			if (getSession == null) {
				response.sendRedirect(JspPageName.INDEX_PAGE);
				} else {
					String role = (String) getSession.getAttribute(AttributsName.ROLE);
					if (!role.equals(UserRole.ADMIN.getName())) {
						response.sendRedirect(JspPageName.ERROR_PAGE);
						} else {
							String id = request.getParameter(NewsParameterName.JSP_ID_NEWS);
							news = newsService.findById(Integer.parseInt(id));
							if (news == null) {
								response.sendRedirect(JspPageName.ERROR_PAGE);
								} else {
									request.setAttribute(AttributsName.NEWS, news);
									getSession.setAttribute(AttributsName.USER_STATUS, ConnectorStatus.ACTIVE);
									getSession.setAttribute(AttributsName.NEWS_COMMANDS_NAME, AttributsName.EDIT_NEWS);
									getSession.setAttribute(AttributsName.NEWS_ID, request.getParameter(NewsParameterName.JSP_ID_NEWS));
									StringBuilder urlForRedirect = new StringBuilder(PageUrl.EDIT_NEWS_PAGE);
									urlForRedirect.append(id);
									getSession.setAttribute(AttributsName.PAGE_URL, urlForRedirect.toString());
									request.getRequestDispatcher(JspPageName.BASELAYOUT_PAGE).forward(request, response);
									getSession.removeAttribute(AttributsName.NEWS_COMMANDS_NAME);
									}
							}
					}
			} catch (ServiceException e) {
				log.error(e);
				response.sendRedirect(JspPageName.ERROR_PAGE);
				}
		}
}