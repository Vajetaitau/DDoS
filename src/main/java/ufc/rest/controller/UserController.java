package ufc.rest.controller;

import ufc.constants.Urls;
import ufc.core.exceptions.GeneralException;
import ufc.core.service.firstLayer.UserService;
import ufc.rest.response.ResponseMessage;
import ufc.dto.user.PrivateUserDetails;
import ufc.dto.user.PublicUserDetails;
import ufc.rest.request.UserAuthenticationRequest;
import ufc.rest.response.CurrentUserResponse;
import ufc.rest.response.UserAuthenticationResponse;
import ufc.spring.CustomUserDetailsService;
import ufc.spring.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController extends AbstractController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager,
                          CustomUserDetailsService customUserDetailsService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
    }

    @RequestMapping(value = Urls.USER, method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage create(@RequestBody PrivateUserDetails privateUserDetails) {
        ResponseMessage operation = null;
        try {
            userService.create(privateUserDetails);
        } catch (GeneralException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = Urls.AUTHENTICATE, method = RequestMethod.POST)
    @ResponseBody
    public UserAuthenticationResponse authenticate(@RequestBody UserAuthenticationRequest userAuthenticationRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                (userAuthenticationRequest.getUsername(), userAuthenticationRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userAuthenticationRequest.getUsername());

        String token = TokenUtils.createToken(userDetails);
        return new UserAuthenticationResponse(token);
    }

    @RequestMapping(value = Urls.CURRENT_USER, method = RequestMethod.GET)
    @ResponseBody
    public CurrentUserResponse getCurrentUser() {
        PublicUserDetails publicUserDetails = userService.getCurrentUser();
        return new CurrentUserResponse(publicUserDetails);
    }

}
