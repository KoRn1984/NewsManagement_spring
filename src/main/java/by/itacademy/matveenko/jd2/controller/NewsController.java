package by.itacademy.matveenko.jd2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import by.itacademy.matveenko.jd2.service.INewsService;

@Controller
@RequestMapping("/")
public class NewsController {

private INewsService newsService;
	
	@Autowired
    public void setNewsService(INewsService newsService) {
        this.newsService = newsService;
    }
	
	
}