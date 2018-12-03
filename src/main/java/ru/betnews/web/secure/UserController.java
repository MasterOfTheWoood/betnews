package ru.betnews.web.secure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.betnews.dao.MessageDao;
import ru.betnews.entity.Message;
import ru.betnews.entity.News;
import ru.betnews.entity.NewsTopic;
import ru.betnews.entity.User;
import ru.betnews.entity.dto.*;
import ru.betnews.service.memory.MemoryUserService;
import ru.betnews.service.news.NewsService;
import ru.betnews.service.security.SecurityContextHelper;
import ru.betnews.service.user.MessageService;
import ru.betnews.service.user.UserService;

import java.util.*;

import static org.springframework.http.HttpStatus.OK;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 07.03.15
 * Time: 13:14
 *
 */
@Controller
@RequestMapping("/member")
public class UserController {
    private final SecurityContextHelper securityContextHelper;
    private final MemoryUserService memoryUserService;
    private final MessageService messageService;
    private final UserService userService;

    @Autowired
    public UserController(SecurityContextHelper securityContextHelper,
                          MemoryUserService memoryUserService,
                          MessageService messageService,
                          UserService userService) {
        this.securityContextHelper = securityContextHelper;
        this.memoryUserService = memoryUserService;
        this.messageService = messageService;
        this.userService = userService;
    }

    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<MessageDTO> sendMessage(@RequestParam("to") long userToId, @RequestParam("message") String message){
        User userFrom = memoryUserService.getUser(securityContextHelper.getCurrentHeroId());
        User userTo = memoryUserService.getUser(userToId);
        MessageDTO messageDTO = null;
        if(userFrom != null){
            messageDTO = messageService.sendMessage(message, userFrom, userTo);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/getDialogs", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<List<DialogDTO>>  getDialogs(){
        User user = memoryUserService.getUser(securityContextHelper.getCurrentHeroId());
        List<DialogDTO> dialogDTOs = user != null? messageService.getDialogs(user) : new ArrayList<>();
        return new ResponseEntity<>(dialogDTOs, HttpStatus.OK);
    }

    @RequestMapping(value = "/getMessages", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<List<MessageDTO>>  getMessages(@RequestParam("from") long userFromId, @RequestParam("offset") Integer offset){
        User user = memoryUserService.getUser(securityContextHelper.getCurrentHeroId());
        User userFrom = userService.getUserById(userFromId);
        List<Message> messages = user != null && userFrom != null? messageService.getMessages(user, userFrom, offset) : new ArrayList<>();
        List<MessageDTO> messageDTOs = new ArrayList<>();
        for(Message message : messages){
            messageDTOs.add(new MessageDTO(message));
        }
        Collections.reverse(messageDTOs);
        return new ResponseEntity<>(messageDTOs, HttpStatus.OK);
    }

    @RequestMapping("/users")
    @ResponseBody
    public ResultDTO getUsers(String userName){
        return new ResultDTO(userService.findLikeName(userName));
    }

    @RequestMapping("/user/save/")
    @ResponseBody
    public ResponseEntity<?> getUserSave(@RequestBody UserDTO userDTO){
        userService.editUser(userDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
