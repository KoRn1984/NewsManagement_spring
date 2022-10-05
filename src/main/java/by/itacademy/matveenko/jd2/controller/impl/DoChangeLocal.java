package by.itacademy.matveenko.jd2.controller.impl;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.itacademy.matveenko.jd2.controller.Command;
import by.itacademy.matveenko.jd2.util.AttributsName;
import by.itacademy.matveenko.jd2.util.PageUrl;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DoChangeLocal implements Command {
	private static final Logger log = LogManager.getLogger(DoChangeLocal.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String local = request.getParameter(AttributsName.LOCAL);
		HttpSession getSession = request.getSession(true);
		getSession.setAttribute(AttributsName.LOCAL, local);
		String url = (String) getSession.getAttribute(AttributsName.PAGE_URL);
		if(url == null || url.isEmpty()) {
			log.warn("URL not found!");
			response.sendRedirect(PageUrl.BASE_PAGE);
		}		
		response.sendRedirect(url);
	}
}