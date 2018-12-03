package ru.betnews.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.betnews.criteria.NewsCriteria;
import ru.betnews.entity.News;
import ru.betnews.entity.NewsTopic;
import ru.betnews.entity.NewsType;
import ru.betnews.entity.User;
import ru.betnews.entity.dto.NewsDTO;
import ru.betnews.service.memory.MemoryUserService;
import ru.betnews.service.news.NewsService;
import ru.betnews.service.security.SecurityContextHelper;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 21.03.15
 * Time: 12:21
 * Администрирование новостей
 */
@Controller
@RequestMapping("/admin/news")
@SessionAttributes("newsCriteria")
public class AdminNewsController {
    @Autowired
    SecurityContextHelper securityContextHelper;
    @Autowired
    MemoryUserService memoryUserService;

    @Autowired
    private NewsService newsService;

    @ModelAttribute("newsCriteria")
    public NewsCriteria getCriteria(){
        return new NewsCriteria();
    }

    @ModelAttribute("newsTopics")
    public Collection<NewsTopic> getNewsTopics(){
        return Arrays.asList(NewsTopic.values());
    }

    @ModelAttribute("newsTypes")
    public Collection<NewsType> getNewsTypes(){
        return Arrays.asList(NewsType.values());
    }

    @RequestMapping("/")
    public ModelAndView showList(@ModelAttribute("newsCriteria") NewsCriteria newsCriteria){
        ModelAndView modelAndView = new ModelAndView("/admin/news/news");
        Collection<News> news = newsService.list(newsCriteria);
        modelAndView.addObject("newsList", news);
        return modelAndView;
    }
    @RequestMapping(value = "/action", method = RequestMethod.GET)
    public ModelAndView openCreateNewsPage(@RequestParam("form") String form){
        ModelAndView modelAndView = new ModelAndView("/admin/news/add");
        modelAndView.addObject("newsDTO", new NewsDTO());
        return modelAndView;
    }
    @RequestMapping(value = "/action/{newsId}", method = RequestMethod.GET)
    public ModelAndView openEditNewsPage(@PathVariable("newsId") int newsId, @RequestParam("form") String form){
        ModelAndView modelAndView = new ModelAndView("/admin/news/edit");
        News news = newsService.getById(newsId);
        modelAndView.addObject("newsDTO", news);
        return modelAndView;
    }

    @RequestMapping(value = "/action/{newsId}", method = RequestMethod.DELETE)
    public String deleteNews(@PathVariable("newsId") int newsId){
       newsService.delete(newsId);
        return "redirect:/admin/news/";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createNews(@ModelAttribute("newsDTO")NewsDTO newsDTO){
        User user = memoryUserService.getUser(securityContextHelper.getCurrentHeroId());
        newsService.create(newsDTO, user);
        return "redirect:/admin/news/";
    }
}
