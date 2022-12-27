package info.ashutosh.reposetory;

import org.springframework.data.jpa.repository.JpaRepository;

import info.ashutosh.entity.Customer;

public interface CustomerReposetory extends JpaRepository<Customer, Long> {

}
