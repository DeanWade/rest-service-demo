package hello.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hello.domain.CustomerJPA;

@Repository
public interface CustomerJPARepository extends CrudRepository<CustomerJPA, String> {
	
	CustomerJPA getByFirstName(String firstName);
}
