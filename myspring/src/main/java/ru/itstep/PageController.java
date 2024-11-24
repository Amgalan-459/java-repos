package ru.itstep;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class PageController {
	
	@Autowired
	MoneyService service;
	@Autowired
	UserDtoService userService;

	@GetMapping("/newpage")
	public String p1(
			@CookieValue(value="userEmail", defaultValue="mail@mail.ru") String email,
			Model model) {
		
	    model.addAttribute("wallet", new Wallet("Лист тимьяна", 156., userService.findByEmail(email).get(0).getId()));
		return "page";  // src/main/resources/templates
	}
	
	@PostMapping("/kupi")
	public String newBuy(
		@ModelAttribute Wallet newBuy,
		@CookieValue(value="userEmail", defaultValue="mail@mail.ru") String email,
		Model model) {
		newBuy.setUserId(userService.findByEmail(email).get(0).getId());
	    service.save(newBuy);
	    model.addAttribute("wallet", newBuy);
	    return "redirect:cart";
	  }
	
	@GetMapping("/cart")
	public String cart(
			@CookieValue(value="userEmail", defaultValue="mail@mail.ru") String email,
			Model model) {
		System.out.println("User: " + email);
		UserDto user = userService.findByEmail(email).get(0);
		
		model.addAttribute("wallets", service.findByUserId(user.getId()));
		model.addAttribute("user", user);
		return "cart";
	}
	
	@GetMapping("/registration")
	public String showRegistrationForm(
            WebRequest request,
            Model model) {
	    UserDto userDto = new UserDto();
	    model.addAttribute("user", userDto);
	    return "registration";
	}
	
	@PostMapping("/registration")
	public String postRegistrationForm(
			@ModelAttribute UserDto user,
			Model model) {
		userService.save(user);
		model.addAttribute("user", user);
		return "redirect:login";
	}
	
	@GetMapping("/login")
	public String getLogin(
			WebRequest request,
			Model model) {
		UserDto user = new UserDto();
		model.addAttribute("user", user);
		return "login";
	}
	
	@PostMapping("/login")
	public String postLogin(
			@ModelAttribute UserDto user,
			HttpServletResponse response) {
		List<UserDto> users = userService.findByEmail(user.getEmail());
		if (users.size() == 0) return "redirect:login";
		boolean f = false;
		for (int i = 0; i < users.size(); i++) {			
			if (users.get(i).equals(user)) f = true;
		}
		if (f) {
			Cookie cokkie = new Cookie("userEmail", user.getEmail());
			response.addCookie(cokkie);
			return "redirect:cart";
		}
		
		System.out.println("Не подошло");
		
		return "redirect:login";
	}
	
	@GetMapping("/admin")
	public String getAdmin(Model model) {
		List<UserDto> users = userService.findByEmail("mail@mail.ru");
		model.addAttribute("users", users);
		return "adminPage";
	}
	
	@GetMapping("/*")
	 public String red() {
	  return "redirect:login";
	 }


	/*@PostMapping("/greeting")
  public String greetingSubmit(
  @ModelAttribute Greeting greeting, Model model) {
    model.addAttribute("greeting", greeting);
    return "result";
  }*/
}
