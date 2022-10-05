package by.itacademy.matveenko.jd2.controller.impl;

import java.io.IOException;

import by.itacademy.matveenko.jd2.controller.Command;
import by.itacademy.matveenko.jd2.util.AttributsName;
import by.itacademy.matveenko.jd2.util.ConnectorStatus;
import by.itacademy.matveenko.jd2.util.JspPageName;
import by.itacademy.matveenko.jd2.util.PageUrl;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GoToUserPersonalAccount implements Command {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		HttpSession getSession = request.getSession(false);
		if (getSession == null) {
			response.sendRedirect(JspPageName.INDEX_PAGE);
				} else {
					getSession.setAttribute(AttributsName.USER_STATUS, ConnectorStatus.ACTIVE);					
					getSession.setAttribute(AttributsName.NEWS_COMMANDS_NAME, AttributsName.USER_ACCOUNT);
					request.setAttribute(AttributsName.SHOW_NEWS, AttributsName.DO_NOT_SHOW_NEWS);
					getSession.setAttribute(AttributsName.PAGE_URL, PageUrl.USER_PERSONAL_ACCOUNT);
					request.getRequestDispatcher(JspPageName.BASELAYOUT_PAGE).forward(request, response);
					getSession.removeAttribute(AttributsName.NEWS_COMMANDS_NAME);
					}
		}
}