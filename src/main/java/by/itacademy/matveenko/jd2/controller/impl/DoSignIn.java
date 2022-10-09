package by.itacademy.matveenko.jd2.controller.impl;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.itacademy.matveenko.jd2.service.INewsService;
import by.itacademy.matveenko.jd2.service.IUserService;
import by.itacademy.matveenko.jd2.bean.News;
import by.itacademy.matveenko.jd2.bean.User;
import by.itacademy.matveenko.jd2.bean.UserRole;
import by.itacademy.matveenko.jd2.controller.Command;
import by.itacademy.matveenko.jd2.service.ServiceException;
import by.itacademy.matveenko.jd2.service.ServiceProvider;
import by.itacademy.matveenko.jd2.util.AttributsName;
import by.itacademy.matveenko.jd2.util.ConnectorStatus;
import by.itacademy.matveenko.jd2.util.JspPageName;
import by.itacademy.matveenko.jd2.util.PageUrl;
import by.itacademy.matveenko.jd2.util.UserParameterName;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DoSignIn implements Command {

	private final IUserService service = ServiceProvider.getInstance().getUserService();
	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
	private static final Logger log = LogManager.getLogger(DoSignIn.class);
	private static final String AUTHENTICATION_ERROR = "&AuthenticationError";
	private static final int COUNT_NEWS = 5;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter(UserParameterName.JSP_LOGIN_PARAM);
		String password = request.getParameter(UserParameterName.JSP_PASSWORD_PARAM);
		HttpSession getSession = request.getSession(true);
		List<News> latestNews;		
		
		if (!dataValidation(login, password)) {
            response.sendRedirect(JspPageName.INDEX_PAGE);
            return;
        }
		try {			
			User user = service.signIn(login, password);
			latestNews = newsService.latestList(COUNT_NEWS);
			if (user == null) {				
				getSession.setAttribute(AttributsName.USER_STATUS, ConnectorStatus.NOT_ACTIVE);
				getSession.removeAttribute(AttributsName.REGISTER_USER);
				getSession.setAttribute(AttributsName.ROLE, UserRole.GUEST);
				request.setAttribute(AttributsName.NEWS, latestNews);				
				StringBuilder urlForRedirect = new StringBuilder(PageUrl.BASE_PAGE);
				urlForRedirect.append(AUTHENTICATION_ERROR);
				response.sendRedirect(urlForRedirect.toString());				
			} else if (!user.getRole().equals(UserRole.GUEST)) {
				getSession.setAttribute(AttributsName.USER_STATUS, ConnectorStatus.ACTIVE);
				getSession.setAttribute(AttributsName.ROLE, user.getRole().getName());
				getSession.setAttribute(AttributsName.USER, user);
				getSession.removeAttribute(AttributsName.REGISTER_USER);
				request.setAttribute(AttributsName.NEWS, latestNews);
				response.sendRedirect(PageUrl.NEWS_LIST_PAGE);
			} 
		} catch (ServiceException e) {
			log.error(e);
			response.sendRedirect(JspPageName.INDEX_PAGE);
		}		
	}	
	
	private boolean dataValidation(String login, String password) {
        if (login == null || password == null) {
            return false;
        }
        return true;
    }	
}