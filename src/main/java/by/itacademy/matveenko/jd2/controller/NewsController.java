package by.itacademy.matveenko.jd2.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import by.itacademy.matveenko.jd2.bean.News;
import by.itacademy.matveenko.jd2.service.INewsService;
import by.itacademy.matveenko.jd2.util.AttributsName;
import by.itacademy.matveenko.jd2.util.ConnectorStatus;
import by.itacademy.matveenko.jd2.util.PageUrl;

@Controller
@RequestMapping("/controller")
public class NewsController {	
	
	private INewsService newsService;
	
	@Autowired
    public void setNewsService(INewsService newsService) {
        this.newsService = newsService;
        System.out.println("Hello1!");
    }
	
	//private static final Logger log = LogManager.getLogger(GoToBasePage.class);
	private static final int COUNT_NEWS = 5;

	@Transactional
	@GetMapping()
	public String basePage(Model model, HttpSession session) {
		System.out.println("Hello2!");
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
		return "pages/layouts/baseLayout";
		}
}