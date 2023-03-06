package com.example.clientmanagementapi.services;

import org.springframework.stereotype.Service;

import com.example.clientmanagementapi.enities.Client;
import com.example.clientmanagementapi.exception.DuplicateIdentityNumberException;
import com.example.clientmanagementapi.exception.DuplicateMobileNumberException;
import com.example.clientmanagementapi.exception.InvalidIdentityNumberException;
import com.example.clientmanagementapi.repositories.ClientRepository;

import java.util.Optional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

  
    public Client createClient(Client client) throws InvalidIdentityNumberException,
    	DuplicateIdentityNumberException, DuplicateMobileNumberException {
    	
    	if(client.getIdNumber().length() != 13) {
    		
    		throw new InvalidIdentityNumberException("ID Number must be 13 Digits");
    	} 
    	
    	
    	Client extistingClientIdNumber = clientRepository.findByIdNumber(client.getIdNumber());
    	
    	if(extistingClientIdNumber != null) {
    		throw new DuplicateIdentityNumberException("ID Number Already Exists");
    	}
    	
    	Client existingClientMobileNumber = clientRepository.findByMobileNumber(client.getMobileNumber());
    	
    	if(existingClientMobileNumber != null) {
    		throw new DuplicateMobileNumberException("ID Number Already Exists");
    	}
    	
    	return clientRepository.save(client);
    		

    }

    public Client updateClient(Client client) throws InvalidIdentityNumberException,
    	DuplicateIdentityNumberException, DuplicateMobileNumberException  {
    	
        return createClient(client);
    }


    public List<Client> findAllClients() {
    	
    	return (List<Client>) clientRepository.findAll();
    	
    }
    
    
    public Client searchClientsByFirstName(String firstName) {
    	
    	return clientRepository.findByFirstName(firstName);
    	
    }
    
    public Client searchClientsByMobileNumber(String mobileNumber) {
  
    	return clientRepository.findByMobileNumber(mobileNumber);
    	
    }
    
    public Client searchClientsByIdentityNumber(String identityNumber) {
    	
    	return clientRepository.findByIdNumber(identityNumber);
    
    }


	public void deleteClient(Long id) {
		
		clientRepository.deleteById(id);
		
	}
    
}
