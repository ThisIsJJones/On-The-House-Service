package backend.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Business {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "business_id", length = 20)
	private Integer id;
	
	@Column(name = "business_name", length=100)
	private String businessName;
	
	@Column(name = "email", length=100)
	private String email;
	
	@Column(name = "address", length=100)
	private String address;
	
	@Column(name = "city", length=100)
	private String city;
	
	@Column(name = "state", length=100)
	private String state;
	
	@Column(name = "longitude")
	private Double longitude;
	
	@Column(name = "latitude")
	private Double latitude;
	
	@Column(name = "password", length=100)
	private String password;
	
	@Column(name = "salt", length = 30)
	private String salt;
	
	@Column(name = "hash", length = 60)
	private String hash;
	
	@Column(name = "phone_number", length=10)
	private String phoneNumber;
	
	@Column(name = "membership_type", length=1)
	private MembershipType membershipType;
	
	@Column(name = "beacon_id", length=100)
	private String beaconID;
	
	@OneToMany
	@JsonManagedReference(value="business-raffles")
	private List<Raffle> raffles;
	
	@OneToMany
	@JsonManagedReference(value="business-prizes")
	private List<Prize> prizes;
	
	public Business() {
		this.membershipType = MembershipType.NONE;
		this.raffles = new ArrayList<Raffle>();
		this.prizes = new ArrayList<Prize>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public MembershipType getMembershipType() {
		return membershipType;
	}

	public void setMembershipType(MembershipType membershipType) {
		this.membershipType = membershipType;
	}

	public String getBeaconID() {
		return beaconID;
	}

	public void setBeaconID(String beaconID) {
		this.beaconID = beaconID;
	}

	public List<Raffle> getRaffles() {
		return raffles;
	}

	public void setRaffles(List<Raffle> raffles) {
		this.raffles = raffles;
	}

	public List<Prize> getPrizes() {
		return prizes;
	}

	public void setPrizes(List<Prize> prizes) {
		this.prizes = prizes;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
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
		Business other = (Business) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
