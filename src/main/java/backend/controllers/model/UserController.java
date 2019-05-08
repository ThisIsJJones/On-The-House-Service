package backend.controllers.model;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import backend.models.User;
import backend.repository.UserRepo;

@Controller
@RequestMapping(value="/model/user")
public class UserController {

	
	@RequestMapping(value="/getAllUsers", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<User> getAllUsers() {
		List<User> users = UserRepo.getAll();
		return users;
	}
	
	@RequestMapping(value="/getUser/{userId}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public User getUser(@PathVariable int userId) {
		User user = UserRepo.get(userId);
		return user;
	}
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	@ResponseBody
	public Integer addUser() {
		
		User newUser = new User();
		Random r = new Random();
		
		boolean isDuplicateId = false;
		do {
			newUser.setUid(r.nextInt(100000));
			
			
			for(User users : UserRepo.getAll()) {
				if(newUser.getUid().equals(users.getUid()) || newUser.getUid().equals(0)) {
					isDuplicateId = true;
					break;
				}
			}
		
		}while(isDuplicateId);
		
		UserRepo.insert(newUser);
		
		
		return newUser.getUid();
	}
	
	@RequestMapping(value="/remove/{userId}", method=RequestMethod.POST)
	@ResponseBody
	public Boolean removeUser(@PathVariable Integer userId) {
		User user = UserRepo.get(userId);
		return UserRepo.delete(user);
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	@ResponseBody
	public Boolean updateUser(@RequestBody User user) {
		
		//prevent duplicate usernames
		if(UserRepo.getUserWithUsername(user.getUsername()) == null) {
			return UserRepo.update(user);		
		}
		
		return false;
	}
	
}
