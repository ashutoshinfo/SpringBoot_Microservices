package info.ashutosh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Long id;

	@Column(name = "first_name")
	public String firstName;

	@Column(name = "last_name")
	public String lastName;

	@Column(name = "email")
	public String email;

	@Column(name = "gender")
	public String gender;

	@Column(name = "mobile")
	public String mobile;

	@Column(name = "country")
	public String country;

	@Column(name = "date_of_birth")
	public String dateOfBirth;
}
