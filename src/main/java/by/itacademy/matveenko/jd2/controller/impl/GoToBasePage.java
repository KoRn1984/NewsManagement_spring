package by.itacademy.matveenko.jd2.controller.impl;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

@Controller
@RequestMapping("/controller?command=go_to_base_page")
public class GoToBasePage {	
	
	private INewsService newsService;
	
	@Autowired
    public void setNewsService(INewsService newsService) {
        this.newsService = newsService;
    }
	
	//private static final Logger log = LogManager.getLogger(GoToBasePage.class);
	private static final int COUNT_NEWS = 5;

	@Transactional
	@RequestMapping("/")
	public String basePage(Model theModel, HttpSession session) {
		//HttpSession getSession = request.getSession(true);
		List<News> latestNews;
				
		try {			
			latestNews = newsService.latestList(COUNT_NEWS);			
			session.setAttribute(AttributsName.NEWS, latestNews);
			session.setAttribute(AttributsName.PAGE_URL, PageUrl.BASE_PAGE);
		} finally {
			session.setAttribute(AttributsName.USER_STATUS, ConnectorStatus.NOT_ACTIVE);
			session.setAttribute(AttributsName.PAGE_URL, PageUrl.BASE_PAGE);
			//session.getRequestDispatcher(JspPageName.BASELAYOUT_PAGE).forward(request, response);
		}
		return "/index.jsp";
}
	}