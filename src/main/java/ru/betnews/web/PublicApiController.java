package ru.betnews.web;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.betnews.criteria.NewsCriteria;
import ru.betnews.entity.News;
import ru.betnews.entity.NewsTopic;
import ru.betnews.entity.NewsType;
import ru.betnews.entity.dto.*;
import ru.betnews.service.memory.MemoryUserService;
import ru.betnews.service.news.NewsService;
import ru.betnews.service.security.SecurityContextHelper;
import ru.betnews.service.user.UserService;
import ru.betnews.service.utils.ImageService;
import ru.betnews.service.utils.UtilService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by dell on 08.05.2017.
 */
@Controller
public class PublicApiController {

    private final NewsService newsService;
    private final SecurityContextHelper securityContextHelper;
    private final MemoryUserService memoryUserService;
    private final ImageService imageService;

    @Autowired
    public PublicApiController(NewsService newsService,
                               SecurityContextHelper securityContextHelper,
                               MemoryUserService memoryUserService,
                               ImageService imageService) {
        this.newsService = newsService;
        this.securityContextHelper = securityContextHelper;
        this.memoryUserService = memoryUserService;
        this.imageService = imageService;
    }

    @RequestMapping("/news/")
    @ResponseBody
    public List<NewsDTO> getNews(@RequestBody(required = false) NewsCriteria newsCriteria){
        return newsService.getNewsList(newsCriteria);
    }

    @RequestMapping("/topics/")
    @ResponseBody
    public List<TopicDTO> getTopics(){
        return NewsTopic.getTopicDTOs();
    }


    @RequestMapping("/types/")
    @ResponseBody
    public List<TypeDTO> getTypes(){
        return NewsType.getTypesDTOS();
    }


    @RequestMapping(value = "/current", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public UserDTO getCurrentUser(){
        long heroId = securityContextHelper.getCurrentHeroId();
        if(heroId != 0)
            return new UserDTO(memoryUserService.getUser(heroId));
        return new UserDTO();
    }

    @RequestMapping(value = "/news/images/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> getNewsImage(@PathVariable("id") Long newsId) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        News news = newsService.getById(newsId);
        InputStream in = imageService.getImage(news.getIllustration());
        byte[] media = IOUtils.toByteArray(in);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        return new ResponseEntity<>(media, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/carousel", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<List<NewsDTO>> getCarousel(){
        List<NewsDTO> newsDTOList = newsService.getCarousel();
        return new ResponseEntity<>(newsDTOList, HttpStatus.OK);
    }
    @RequestMapping(value = "/popular", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<List<NewsDTO>> getPopularNews(){
        List<NewsDTO> newsDTOList = newsService.getPopularNews();
        return new ResponseEntity<>(newsDTOList, HttpStatus.OK);
    }
}
