package backend.controllers.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import backend.models.Business;
import backend.models.Prize;
import backend.models.Raffle;
import backend.repository.BusinessRepo;
import backend.repository.PrizeRepo;
import backend.repository.RaffleRepo;
import util.OnTheHouseUtil;

@Controller
@RequestMapping(value="/model/raffle")
public class RaffleController {

	@RequestMapping(value="/getAllRaffles", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Raffle> getAllRaffles() {
		List<Raffle> raffles = RaffleRepo.getAll();
		return raffles;
	}
	
	@RequestMapping(value="/getRaffle/{raffleId}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Raffle getRaffle(@PathVariable int raffleId) {
		Raffle raffle = RaffleRepo.get(raffleId);
		return raffle;
	}
	
	@RequestMapping(value="/create/{buisnessId}/{prizeId}", method=RequestMethod.POST)
	@ResponseBody
	public Boolean addRaffle(@RequestBody Raffle raffle, @PathVariable Integer buisnessId, @PathVariable Integer prizeId) {
		Boolean successful = false;
		
		
		if(buisnessId != null && prizeId != null) {

			Business business = BusinessRepo.get(buisnessId);
			Prize prize = PrizeRepo.get(prizeId);
		
			if(business != null && 
				prize != null && 
				raffleTimeIsValid(raffle, business) && 
				RaffleRepo.insert(raffle)) {
				
					System.out.println(raffle.getStartDate());
					
					Calendar cal = Calendar.getInstance();
					cal.setTime(raffle.getStartDate());
					System.out.println(cal.getTime());
				
					business.getRaffles().add(raffle);
					raffle.setBusiness(business);
					prize.setRaffle(raffle);
					raffle.setPrize(prize);
					
					
					RaffleRepo.update(raffle);
					PrizeRepo.update(prize);
					BusinessRepo.update(business);
					successful = true;
				}
		}
		
		return successful;
	}
	
	@RequestMapping(value="/remove/{raffleId}", method=RequestMethod.POST)
	@ResponseBody
	public Boolean removeRaffle(@PathVariable Integer raffleId) {
		Raffle raffle = RaffleRepo.get(raffleId);
		return RaffleRepo.delete(raffle);
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	@ResponseBody
	public Boolean updateRaffle(@RequestBody Raffle raffle) {
			return RaffleRepo.update(raffle);
	}
	
	private boolean raffleTimeIsValid(Raffle newRaffle, Business business) {
		boolean result = true;
		
		for(Raffle businessRaffle : business.getRaffles()){
			//check overlapping times -> same day and start or end time is in between any other raffles
			if(newRaffle.getStartDate().equals(businessRaffle.getStartDate()) &&
					((newRaffle.getStartTime().isAfter(businessRaffle.getStartTime()) && 
					newRaffle.getStartTime().isBefore(businessRaffle.getEndTime())) || 
					(newRaffle.getEndTime().isAfter(businessRaffle.getStartTime()) && 
					newRaffle.getEndTime().isBefore(businessRaffle.getEndTime())))){
					result = false;
					break;//found one raffle that conflicts so exit
			}
		}
		
		return result;
	}
}
