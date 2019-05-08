package backend.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "on_the_house_user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id", length = 20)
	private Integer id;
	
	@Column(name = "uid")
	private Integer uid;
	
	@Column(name = "full_name", length=100)
	private String fullName;
	
	@Column(name = "username", length=100)
	private String username;
	
	@Column(name = "email", length=100)
	private String email;
	
	@Column(name = "password", length=100)
	private String password;
	
	@Column(name = "salt", length = 30)
	private String salt;
	
	@Column(name = "hash", length = 60)
	private String hash;
	
	@Column(name = "ticketQty")
	private Integer ticketQty;
	
	@OneToMany
	@JsonManagedReference(value="user-prizes")
	private List<Prize> prizesWon;
	
	@ManyToOne
	@JsonBackReference(value="raffle-users")
	private Raffle enteredRaffle;
	
	@OneToMany
	@JsonManagedReference(value="user-raffles")
	private List<Raffle> rafflesWon;
	
	public User() {
		this.prizesWon = new ArrayList<Prize>(); 
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getTicketQty() {
		return ticketQty;
	}

	public void setTicketQty(Integer ticketQty) {
		this.ticketQty = ticketQty;
	}


	public List<Prize> getPrizesWon() {
		return prizesWon;
	}

	public void setPrizesWon(List<Prize> prizesWon) {
		this.prizesWon = prizesWon;
	}

	public Raffle getEnteredRaffle() {
		return enteredRaffle;
	}

	public void setEnteredRaffle(Raffle enteredRaffle) {
		this.enteredRaffle = enteredRaffle;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public List<Raffle> getRafflesWon() {
		return rafflesWon;
	}

	public void setRafflesWon(List<Raffle> rafflesWon) {
		this.rafflesWon = rafflesWon;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

