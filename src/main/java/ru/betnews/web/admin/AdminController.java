package ru.betnews.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 21.03.15
 * Time: 19:34
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @RequestMapping("/")
    public ModelAndView showAdmin(){
        return new  ModelAndView("/admin/index");
    }
}
