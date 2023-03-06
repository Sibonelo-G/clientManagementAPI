package com.example.clientmanagementapi.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.clientmanagementapi.enities.Client;
import com.example.clientmanagementapi.services.ClientService;

@RestController
@RequestMapping("/client")
public class ClientController {

	
	@Autowired
	ClientService service;
	
    @GetMapping("/")
    public ResponseEntity<List<Client>> findAll() {
        try {
           
        	List<Client> clients = service.findAllClients();
        	
            return new ResponseEntity<>(clients, HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Client dto) {
        try {
            
        	Client resp = service.createClient(dto);
        		
        	return new ResponseEntity<>(resp, HttpStatus.OK);
        	
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Client dto) {
        try {
        	Client resp = service.updateClient(dto);
    		
        	return new ResponseEntity<>(resp, HttpStatus.OK);
        	
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            service.deleteClient(id);
            return new ResponseEntity<>("Destroy Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
