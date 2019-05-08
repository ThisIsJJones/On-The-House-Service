package backend.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Prize {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "prize_id", length = 20)
	private Integer id;
	
	@Column(name = "prize_name", length=100)
	private String prizeName;
	
	@ManyToOne
	@JsonBackReference(value="business-prizes")
	private Business business;
	
	@OneToOne
	@JsonBackReference(value="prize-raffle")
	private Raffle raffle;
	
	@ManyToOne
	@JsonBackReference(value="user-prizes")
	private User winner;
	
	public Prize() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public Raffle getRaffle() {
		return raffle;
	}

	public void setRaffle(Raffle raffle) {
		this.raffle = raffle;
	}

	public User getWinner() {
		return winner;
	}

	public void setWinner(User winner) {
		this.winner = winner;
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
		Prize other = (Prize) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
