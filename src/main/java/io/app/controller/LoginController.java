package io.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class LoginController {
	
	 @RequestMapping("/login")
	  public String login() {
	    return "login";
	  }

	  // Login form with error
	  @RequestMapping("/login-error.html")
	  public String loginError(Model model) {
	    model.addAttribute("loginError", true);
	    return "login.html";
	  }
	  
	  @RequestMapping("/dashboard")
	  public String dashboard(Model model) {
	    //model.addAttribute("loginError", true);
	    return "Admin-dashboard.html";
	  }

}
