package com.loginapp.web.ui.controllers;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.loginapp.contstants.SecurityConstants;
import com.loginapp.security.jwt.util.CookieUtil;
import com.loginapp.security.jwt.util.JWTAuthorizationUtil;
import com.loginapp.security.user.dataobjects.UserInfo;
import com.loginapp.security.user.service.IUserServiceWrapper;

@Controller
public class WebController {
	 
	
	@Autowired
	private JWTAuthorizationUtil jwtUtil;
	
	@Autowired
	private IUserServiceWrapper userService;

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	
	@GetMapping("api/dashboard")
	public String getDashBoard(@RequestParam("username") String username,HttpServletResponse response){
		String token = jwtUtil.generateToken(username);
		CookieUtil.create(response, SecurityConstants.jwtTokenCookieName, token, false, -1, "localhost");
		return "dashboard";
	}
	
	@GetMapping("/createUpdateUser")
	public String createUpdateUser(Model model){
		List<UserInfo> users = userService.findAllUsers();
		model.addAttribute("users",users);
		
		
		return "registration";
	}
	@PostMapping("/submitForm")
	public String submitUserDetails(
			@ModelAttribute("userFormData") UserInfo userInfo, Model model) {
		if (userInfo.getUsername() != null && !userInfo.getUsername().isEmpty()
				&& userInfo.getPassword() != null
				&& !userInfo.getPassword().isEmpty()) {
			String[] nameArr = userInfo.getUsername().split(",");
			String[] passwordArr = userInfo.getPassword().split(",");
			int i = 0;
			for (String name : nameArr) {
				UserInfo newUser=userService.findByUserName(name);
				if(newUser!=null){
				newUser.setPassword(passwordArr[i]);
				newUser.setUsername(name);
				}
				else{
					newUser=new UserInfo();
					newUser.setPassword(passwordArr[i]);
					newUser.setUsername(name);
				}
				userService.createOrUpdateUsers(newUser);
				i++;
			}
			return createUpdateUser(model);
		}
		return null;
	}
    @RequestMapping(value="/logout", method=RequestMethod.GET)  
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) { 
    	System.out.println("---------Logout-------------------");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();  
        if (auth != null){      
           new SecurityContextLogoutHandler().logout(request, response, auth);  
        }  
         return "/login";  
     }
    
    

}

