package ru.betnews.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.betnews.entity.security.BetNewsUserDetails;
import ru.betnews.service.security.SecurityContextHelper;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dell on 14.04.2018.
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        String token = tokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }


    @RequestMapping(value = "refresh", method = RequestMethod.GET)
    public ResponseEntity<?> authenticationRequest(HttpServletRequest request){
        String token = request.getHeader(AppConstant.tokenHeader);
        String username = tokenUtils.getUsernameFromToken(token);
        BetNewsUserDetails user = (BetNewsUserDetails) userDetailsService.loadUserByUsername(username);
        if(tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordReset())){
            String refreshedToken = tokenUtils.refreshToken(token);
            return ResponseEntity.ok(new AuthenticationResponse(refreshedToken));
        }else {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
