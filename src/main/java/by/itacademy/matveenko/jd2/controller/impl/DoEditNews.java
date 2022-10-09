package by.itacademy.matveenko.jd2.controller.impl;

import java.io.IOException;
import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.itacademy.matveenko.jd2.bean.News;
import by.itacademy.matveenko.jd2.bean.User;
import by.itacademy.matveenko.jd2.bean.UserRole;
import by.itacademy.matveenko.jd2.controller.Command;
import by.itacademy.matveenko.jd2.service.ServiceException;
import by.itacademy.matveenko.jd2.service.impl.NewsServiceImpl;
import by.itacademy.matveenko.jd2.util.AttributsName;
import by.itacademy.matveenko.jd2.util.ConnectorStatus;
import by.itacademy.matveenko.jd2.util.JspPageName;
import by.itacademy.matveenko.jd2.util.NewsParameterName;
import by.itacademy.matveenko.jd2.util.PageUrl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DoEditNews implements Command {	
	private final NewsServiceImpl newsService = new NewsServiceImpl();
	private static final Logger log = LogManager.getLogger(DoEditNews.class);
	private static final String ERROR_EDIT_NEWS_MESSAGE = "&EditNewsError";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer id = Integer.parseInt((String)request.getSession().getAttribute(AttributsName.NEWS_ID));
		String title = request.getParameter(NewsParameterName.JSP_TITLE_NEWS);
		String brief = request.getParameter(NewsParameterName.JSP_BRIEF_NEWS);
		String content = request.getParameter(NewsParameterName.JSP_CONTENT_NEWS);		
		
		try {
			HttpSession getSession = request.getSession(false);
			if (getSession == null) {
				response.sendRedirect(JspPageName.INDEX_PAGE);
				} else {
					String role = (String) getSession.getAttribute(AttributsName.ROLE);
					var news = new News.Builder()
							.withId(id)
							.withTitle(title)
							.withBrief(brief)
							.withContent(content)
							.withDate(LocalDate.now())
							.withAuthor((User)getSession.getAttribute(AttributsName.USER))
							.build();
					if (!role.equals(UserRole.ADMIN.getName())) {
						response.sendRedirect(JspPageName.ERROR_PAGE);
						} else {
							if (newsService.update(news)) {
								getSession.setAttribute(AttributsName.USER_STATUS, ConnectorStatus.ACTIVE);
								getSession.setAttribute(AttributsName.EDIT_NEWS, AttributsName.COMMAND_EXECUTED);
								response.sendRedirect(PageUrl.NEWS_LIST_PAGE);
								} else {
									response.sendRedirect(JspPageName.ERROR_PAGE);
									}
							}
					}
			} catch (ServiceException e) {
				log.error(e);
				StringBuilder urlForRedirect = new StringBuilder(PageUrl.EDIT_NEWS_PAGE);
				urlForRedirect.append(id);
				urlForRedirect.append(ERROR_EDIT_NEWS_MESSAGE);
				response.sendRedirect(urlForRedirect.toString());
				}
		}
}