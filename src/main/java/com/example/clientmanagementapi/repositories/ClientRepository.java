package com.example.clientmanagementapi.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.clientmanagementapi.enities.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {

	Client findByMobileNumber(String mobileNumber);
	
	Client findByFirstName(String firstName);
	
	Client findByIdNumber(String idNumber);
	
	
}
