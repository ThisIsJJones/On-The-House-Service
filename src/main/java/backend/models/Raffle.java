package backend.models;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Raffle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "raffle_id", length = 20)
	private Integer id;
	
	@Column(name = "date")
	private Date startDate;
	
	@Column(name = "start_time")
	private LocalTime startTime;
		
	@Column(name = "end_time")
	private LocalTime endTime;

	@Column(name = "starting_qty")
	private Integer startingQty;
	
	@ManyToOne
	@JsonBackReference(value="business-raffles")
	private Business business;
	
	@OneToOne
	@JsonManagedReference(value="prize-raffle")
	private Prize prize;
	
	@OneToMany
	@JsonManagedReference(value="raffle-users")
	private List<User> usersEntered;
	
	@ManyToOne
	@JsonBackReference(value="user-raffles")
	private User winningUser;
	
	public Raffle() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public Prize getPrize() {
		return prize;
	}

	public void setPrize(Prize prize) {
		this.prize = prize;
	}

	public List<User> getUsersEntered() {
		return usersEntered;
	}

	public void setUsersEntered(List<User> usersEntered) {
		this.usersEntered = usersEntered;
	}

	public Integer getStartingQty() {
		return startingQty;
	}

	public void setStartingQty(Integer startingQty) {
		this.startingQty = startingQty;
	}

	public User getWinningUser() {
		return winningUser;
	}

	public void setWinningUser(User winningUser) {
		this.winningUser = winningUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Raffle other = (Raffle) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
