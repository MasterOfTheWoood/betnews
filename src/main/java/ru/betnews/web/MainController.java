package ru.betnews.web;

import javassist.convert.TransformReadField;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.betnews.entity.dto.UserDTO;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 29.08.14
 * Time: 14:01
 *
 */
@Controller
public class MainController {

    private static final Logger log = Logger.getLogger(MainController.class.getName());

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String viewMainPage()
    {
        return "redirect: index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView MainPage()
    {
        return new ModelAndView("index");
    }

}