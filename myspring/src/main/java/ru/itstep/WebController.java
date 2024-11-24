package ru.itstep;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {
	
	@Autowired
	MoneyService service;

	@GetMapping("/")
    public String mainpage(
        @RequestParam(value = "name", defaultValue = "World") String name) {
		List<Wallet> spent = service.findByQuantityGreaterThanAndQuantityLessThan(26., 1100.);
        return String.format("Bought: %s", spent);
   }
	
	@GetMapping("/hello")
    public String hello(
        @RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
   }

	//@PostMapping("/kupi")
	//  Wallet newBuy(@RequestBody Wallet newBuy) {
	//    return service.save(newBuy);
	//  }
	

	  // Single item
	  
	  //@GetMapping("/employees/{id}")
	  //Employee one(@PathVariable Long id) {
	    
	  //  return repository.findById(id)
	  //    .orElseThrow(() -> new EmployeeNotFoundException(id));
	  //}
}
