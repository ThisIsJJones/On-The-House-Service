package backend.controllers.raffle;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.parsing.Location;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import backend.models.Business;
import backend.models.Raffle;
import backend.models.User;
import backend.repository.BusinessRepo;
import backend.repository.RaffleRepo;
import backend.repository.UserRepo;
import util.OnTheHouseUtil;

@Controller
@RequestMapping(value="/raffle")
public class LiveRaffleController {
	
	@RequestMapping(value="/getLocalBusinesses/{longitude}/{latitude}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Business> getLocalBusinesses(@PathVariable Double longitude, @PathVariable Double latitude) {
		List<Business> businessesWithRaffles = new ArrayList<Business>();

		for(Business business : BusinessRepo.getAll()) {
			if(!business.getRaffles().isEmpty()) {
				//by miles
				if(OnTheHouseUtil.distance(latitude, longitude, business.getLatitude(),  business.getLongitude(), "M") <= .5) {
					businessesWithRaffles.add(business);
				}
			}	
		}
		
		return businessesWithRaffles;
	}
	
	@RequestMapping(value="/getAllRunningRaffles", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Business> getAllRunningRaffles() {
		List<Business> businessesWithRaffles = new ArrayList<Business>();
		
		for(Business business : BusinessRepo.getAll()) {
			for(Raffle raffle : business.getRaffles()) {
				if(OnTheHouseUtil.isRaffleRunning(raffle)) {
					businessesWithRaffles.add(business);
				}
			}
		}
		
		return businessesWithRaffles;
	}

	@RequestMapping(value="/addUserToRaffle/{raffleId}/{userId}", method=RequestMethod.POST)
	@ResponseBody
	public Response addUserToRaffle(@PathVariable Integer raffleId, @PathVariable Integer userId) {
		Response addingUserResponse;
		
		Raffle raffleToEnter = RaffleRepo.get(raffleId);
		User user = UserRepo.get(userId);
		if(raffleToEnter != null && user != null) {

			if(user.getEnteredRaffle() != null) {
				addingUserResponse = Response.status(Response.Status.CONFLICT).entity("User is currently in a raffle").build();
			}else {
				raffleToEnter.getUsersEntered().add(user);
				user.setEnteredRaffle(raffleToEnter);
				user.setTicketQty(raffleToEnter.getStartingQty());
				
				RaffleRepo.update(raffleToEnter);
				UserRepo.update(user);
				addingUserResponse = Response.ok(true).build();
			}
			
		}else {
			addingUserResponse = Response.status(Response.Status.NOT_FOUND).entity("Raffle or user could not be found").build();
		}
		
		return addingUserResponse;
	}
	
	@RequestMapping(value="/removeUserFromRaffle/{userId}", method=RequestMethod.POST)
	@ResponseBody
	public Response removeUserToRaffle(@PathVariable Integer userId) {
		Response removingUserResponse;
		
		User user = UserRepo.get(userId);
		if(user != null && user.getEnteredRaffle() != null) {
			
			Raffle usersRaffle = user.getEnteredRaffle();
			
			if(usersRaffle.getUsersEntered().contains(user)) {
				user.setEnteredRaffle(null);
				user.setTicketQty(0);
				usersRaffle.getUsersEntered().remove(user);
				RaffleRepo.update(usersRaffle);
				UserRepo.update(user);
				removingUserResponse = Response.ok(true).build();
			}else {
				removingUserResponse = Response.status(Response.Status.NOT_FOUND).entity("User is in raffle " + user.getEnteredRaffle().getId() + " but not this one.").build();
			}
		}else {
			removingUserResponse = Response.status(Response.Status.NOT_FOUND).entity("User doesn't exist in raffle").build();
		}
		
		return removingUserResponse;
	}
	
	@RequestMapping(value="/addTicket/{raffleId}/{userId}/{ticketQty}", method=RequestMethod.POST)
	@ResponseBody
	public Response addTicket(@PathVariable Integer raffleId, @PathVariable Integer userId, @PathVariable Integer ticketQty) {
		Response removingUserResponse;
		
		Raffle raffle = RaffleRepo.get(raffleId);
		User user = UserRepo.get(userId);

		if(raffle != null && user != null && ticketQty != null && ticketQty > 0) {
			user.setTicketQty(user.getTicketQty()+ticketQty);
			UserRepo.update(user);
			removingUserResponse = Response.ok(true).build();
		}else {
			removingUserResponse = Response.status(Response.Status.NOT_FOUND).entity("User doesn't exist or ticket quantity is invalid.").build();
		}

		return removingUserResponse;
	}
	
	
	@RequestMapping(value="/chooseAWinner/{raffleId}", method=RequestMethod.GET)
	@ResponseBody
	public User chooseWinner(@PathVariable Integer raffleId) {
		User winner = null;
		
		Raffle raffle = RaffleRepo.get(raffleId);
		if(raffle != null) {
			winner = raffle.getUsersEntered().get(0);
			winner.getPrizesWon().add(raffle.getPrize());
			winner.getRafflesWon().add(raffle);
			raffle.setWinningUser(winner);
			raffle.getPrize().setWinner(winner);
		}
		
		return winner;
	}
}
