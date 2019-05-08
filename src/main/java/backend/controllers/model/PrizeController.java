package backend.controllers.model;

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
import backend.models.User;
import backend.repository.BusinessRepo;
import backend.repository.PrizeRepo;
import backend.repository.RaffleRepo;
import backend.repository.UserRepo;

@Controller
@RequestMapping(value="/model/prize")
public class PrizeController {

	
	@RequestMapping(value="/getAllPrizes", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Prize> getAllPrizes() {
		List<Prize> prizes = PrizeRepo.getAll();
		return prizes;
	}
	
	@RequestMapping(value="/getPrize/{prizeId}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Prize getPrize(@PathVariable int prizeId) {
		Prize prize = PrizeRepo.get(prizeId);
		return prize;
	}
	
	@RequestMapping(value="/add/{businessId}", method=RequestMethod.POST)
	@ResponseBody
	public Boolean addPrize(@RequestBody Prize prize, @PathVariable Integer businessId) {
		Boolean successful = true;
		
		Business business = BusinessRepo.get(businessId);
		
		if(business != null && prize.getPrizeName() != null) {
			//prevent duplicate prizenames
			for(Prize businessPrize : business.getPrizes()) {
				if(businessPrize.getPrizeName().equals(prize.getPrizeName())){
					successful = false;
					break;
				}
			}
		}
		
		if(successful) {
			if(PrizeRepo.insert(prize)) {
				prize.setBusiness(business);
				business.getPrizes().add(prize);
				PrizeRepo.update(prize);
				BusinessRepo.update(business);
			}else {
				successful = false;
			}
		}
		
		return successful;
	}
	
	@RequestMapping(value="/remove/{prizeId}", method=RequestMethod.POST)
	@ResponseBody
	public Boolean removePrize(@PathVariable Integer prizeId) {
		Prize prize = PrizeRepo.get(prizeId);
		Raffle prizeRaffle = prize.getRaffle();
		Business prizeBusiness = prize.getBusiness();
		User prizeWinner = prize.getWinner();
		
		if(prizeBusiness != null) {
			prizeBusiness.getPrizes().remove(prize);
			BusinessRepo.update(prizeBusiness);
		}
		
		if(prizeRaffle != null) {
			prizeRaffle.setPrize(null);
			RaffleRepo.update(prizeRaffle);
		}
		
		if(prizeWinner != null) {
			prizeWinner.getPrizesWon().remove(prize);
			UserRepo.update(prizeWinner);
		}
	
		prize.setBusiness(null);
		prize.setRaffle(null);
		prize.setWinner(null);
		
		
		return PrizeRepo.delete(prize);
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	@ResponseBody
	public Boolean updatePrize(@RequestBody Prize prize) {
		Boolean successful = true;
		
		//prevent duplicate prizenames
		for(Prize businessPrizes : prize.getBusiness().getPrizes()) {
			if(businessPrizes.getPrizeName().equals(prize.getPrizeName())){
				successful = false;
					break;
			}
		}
				
		if(successful) {
			successful = PrizeRepo.update(prize);
		}
		
		return successful;
	}
}
