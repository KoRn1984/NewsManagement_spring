package by.itacademy.matveenko.jd2.controller.impl;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.itacademy.matveenko.jd2.bean.User;
import by.itacademy.matveenko.jd2.bean.UserRole;
import by.itacademy.matveenko.jd2.service.ServiceException;
import by.itacademy.matveenko.jd2.controller.Command;
import by.itacademy.matveenko.jd2.service.IUserService;
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

public class DoRegistration implements Command {
	
	private final IUserService service = ServiceProvider.getInstance().getUserService();
	private static final Logger log = LogManager.getLogger(DoRegistration.class);
	private static final String ERROR_REGISTRATION_MESSAGE = "&RegistrationError";
		
		@Override
		public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String login = request.getParameter(UserParameterName.JSP_LOGIN_PARAM);
		    String password = request.getParameter(UserParameterName.JSP_PASSWORD_PARAM);
			String userName = request.getParameter(UserParameterName.JSP_NAME_PARAM);
		    String userSurname = request.getParameter(UserParameterName.JSP_SURNAME_PARAM);
		    String email = request.getParameter(UserParameterName.JSP_EMAIL_PARAM);		    
		    UserRole role = UserRole.USER;	    	    
		    HttpSession getSession = request.getSession(true);
						
			User user = new User.Builder()
					.withLogin(login)
                    .withPassword(password)                   
                    .withUserName(userName)
                    .withUserSurname(userSurname)                    
                    .withEmail(email)
                    .withRole(role)
                    .build();
		    try {		   
				if (service.registration(user)) {
					getSession.setAttribute(AttributsName.USER_STATUS, ConnectorStatus.ACTIVE);
					getSession.setAttribute(AttributsName.ROLE, user.getRole().getName());
					getSession.setAttribute(AttributsName.USER, user);
					getSession.setAttribute(AttributsName.REGISTER_USER, ConnectorStatus.REGISTERED);					
					response.sendRedirect(PageUrl.NEWS_LIST_PAGE);
				}
				else {					
					getSession.setAttribute(AttributsName.REGISTER_USER, ConnectorStatus.NOT_REGISTERED);
					request.setAttribute(AttributsName.SHOW_NEWS, AttributsName.DO_NOT_SHOW_NEWS);
					request.getRequestDispatcher(JspPageName.BASELAYOUT_PAGE).forward(request, response);
					}										
			} catch (ServiceException e) {
				log.error(e);
				StringBuilder urlForRedirect = new StringBuilder(PageUrl.REGISTRATION_PAGE);
				urlForRedirect.append(ERROR_REGISTRATION_MESSAGE);
				response.sendRedirect(urlForRedirect.toString());				
		    } 
	}
}