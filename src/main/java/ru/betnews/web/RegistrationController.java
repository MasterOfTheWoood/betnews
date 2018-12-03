package ru.betnews.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.betnews.entity.User;
import ru.betnews.entity.dto.UserDTO;
import ru.betnews.service.memory.MemoryUserService;
import ru.betnews.service.security.SecurityContextHelper;
import ru.betnews.service.user.UserService;


/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 28.02.15
 * Time: 15:29
 * Регистрация и всё что с ней связано
 */
@Controller
public class RegistrationController {

    private final UserService userService;
    private final SecurityContextHelper securityContextHelper;
    private final MemoryUserService memoryUserService;

    @Value("${security.salt}")
    private String salt;

    @Autowired
    public RegistrationController(UserService userService,
                                  SecurityContextHelper securityContextHelper,
                                  MemoryUserService memoryUserService
                                  ) {
        this.userService = userService;
        this.securityContextHelper = securityContextHelper;
        this.memoryUserService = memoryUserService;
    }


    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    @ResponseBody public ResponseEntity<UserDTO> registrationUser(@RequestBody UserDTO userDTO)
    {
        try{
            userService.registration(userDTO);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }



    @RequestMapping(value = "/remindPassword", method = RequestMethod.POST)
    @ResponseBody public ResponseEntity<String> sendRemindPasswordMail(@RequestParam("email") String email)
    {
        try{
            userService.sendRemindPasswordMail(email);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("remind.ok", HttpStatus.OK);
    }

    @RequestMapping(value = "/remindPassword", method = RequestMethod.GET)
    @ResponseBody public ResponseEntity<String> remindPassword(@RequestParam("code") String code)
    {
        try{
            userService.remindPassword(code);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("remind.ok", HttpStatus.OK);
    }

    @RequestMapping(value = "/verifyEmail", method = RequestMethod.POST)
    @ResponseBody public ResponseEntity<String> sendVerifyEmailMail()
    {
        try{
            User user = memoryUserService.getUser(securityContextHelper.getCurrentHeroId());
            userService.sendVerifyEmailMail(user);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("remind.ok", HttpStatus.OK);
    }

    @RequestMapping(value = "/verifyEmail", method = RequestMethod.GET)
    @ResponseBody public ResponseEntity<String> verifyEmail(@RequestParam("code") String code)
    {
        try{
            userService.verifyEmail(code);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("remind.ok", HttpStatus.OK);
    }
}
