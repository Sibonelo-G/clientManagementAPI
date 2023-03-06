package com.example.clientmanagementapi.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.clientmanagementapi.enities.Client;
import com.example.clientmanagementapi.exception.DuplicateIdentityNumberException;
import com.example.clientmanagementapi.exception.DuplicateMobileNumberException;
import com.example.clientmanagementapi.exception.InvalidIdentityNumberException;
import com.example.clientmanagementapi.repositories.ClientRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class ClientServiceTest {

	@Mock
	private ClientRepository clientRepository;
	
	@InjectMocks
	private ClientService clientService;
	
	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreateClient() throws InvalidIdentityNumberException, DuplicateIdentityNumberException, DuplicateMobileNumberException {
		
		Client newClient = new Client();
		
		newClient.setFirstName("Sibo");
		newClient.setLastName("Gqi");
		newClient.setMobileNumber("");
		newClient.setIdNumber("9405050852085");
		newClient.setPhysicalAddress("");
		
		when(clientRepository.save(newClient)).thenReturn(newClient);
		
		Client savedClient = clientService.createClient(newClient);
		
		Assertions.assertNotNull(savedClient.getFirstName());
		
		Assertions.assertEquals(newClient.getFirstName(), savedClient.getFirstName());
		Assertions.assertEquals(newClient.getLastName(), savedClient.getLastName());
		Assertions.assertEquals(newClient.getMobileNumber(), savedClient.getMobileNumber());
		Assertions.assertEquals(newClient.getIdNumber(), savedClient.getIdNumber());
		Assertions.assertEquals(newClient.getPhysicalAddress(), savedClient.getPhysicalAddress());
	}
	
	@Test
	public void testCreateClientWithInvalidIdNumber() throws InvalidIdentityNumberException, DuplicateIdentityNumberException, DuplicateMobileNumberException {
		Client newClient = new Client();
		
		newClient.setFirstName("Sibo");
		newClient.setLastName("Gqi");
		newClient.setMobileNumber("0712345678");
		newClient.setIdNumber("97010528530829");
		newClient.setPhysicalAddress("");
		
		Assertions.assertThrows(InvalidIdentityNumberException.class, () -> clientService.createClient(newClient));
		
	}
	
	@Test
	public void testCreateClientWithExistingdIdNumber() throws InvalidIdentityNumberException, DuplicateIdentityNumberException, DuplicateMobileNumberException {
		Client existingClient = new Client();
		
		existingClient.setFirstName("Sibo");
		existingClient.setLastName("Gqi");
		existingClient.setMobileNumber("0712345678");
		existingClient.setIdNumber("9405050852085");
		existingClient.setPhysicalAddress("");
		
		when(clientRepository.findByIdNumber(existingClient.getIdNumber())).thenReturn(existingClient);
		
		Client newClient = new Client();
		
		newClient.setFirstName("Siborhino");
		newClient.setLastName("Gqibs");
		newClient.setMobileNumber("0712345678");
		newClient.setIdNumber("9405050852085");
		newClient.setPhysicalAddress("");
		
		Assertions.assertThrows(DuplicateIdentityNumberException.class, () -> clientService.createClient(newClient));

	}

	@Test
	public void testCreateClientWithExistingMobileNumber()throws InvalidIdentityNumberException, DuplicateIdentityNumberException, DuplicateMobileNumberException {
		Client existingClient = new Client();
		
		existingClient.setFirstName("Sibo");
		existingClient.setLastName("Gqi");
		existingClient.setMobileNumber("0712345678");
		existingClient.setIdNumber("9405050852085");
		existingClient.setPhysicalAddress("");
		
		when(clientRepository.findByMobileNumber(existingClient.getMobileNumber())).thenReturn(existingClient);
		
		Client newClient = new Client();
		
		newClient.setFirstName("Siborhino");
		newClient.setLastName("Gqibs");
		newClient.setMobileNumber("0712345678");
		newClient.setIdNumber("9405050852085");
		newClient.setPhysicalAddress("");
		
		Assertions.assertThrows(DuplicateMobileNumberException.class, () -> clientService.createClient(newClient));
	}
	
	
	@Test
	public void testSearchClientByFirstName() {
		
		Client newClient = new Client(1L,"Sibonelo", "Gqiba", "", "9602055285086","");
		Client newClient2 = new Client(2L,"George", "Frei", "", "9602055284186","");
		
		when(clientRepository.findByFirstName(newClient2.getFirstName())).thenReturn(newClient2);
		
		Client client = clientService.searchClientsByFirstName("George");
		
		verify(clientRepository).findByFirstName("George");
		
		Assertions.assertEquals(client.getFirstName(), newClient2.getFirstName());
		
	}
	
	@Test
	public void testSearchClientByIdNumber() {
		
		Client newClient = new Client(1L,"Sibonelo", "Gqiba", "", "9602055285086","");
		Client newClient2 = new Client(2L,"George", "Frei", "", "9602055284186","");
		
		when(clientRepository.findByIdNumber(newClient2.getIdNumber())).thenReturn(newClient2);
		
		Client client = clientService.searchClientsByIdentityNumber("9602055284186");
		
		
		Assertions.assertEquals(client.getIdNumber(), newClient2.getIdNumber());
		
	}
	
	@Test
	public void testSearchClientByMobileNumber() {
		
		Client newClient = new Client(1L,"Sibonelo", "Gqiba", "0785640865", "9602055285086","");
		Client newClient2 = new Client(2L,"George", "Frei", "", "9602055284186","");
		
		when(clientRepository.findByMobileNumber(newClient.getMobileNumber())).thenReturn(newClient);
		
		Client client = clientService.searchClientsByMobileNumber("0785640865");
		
		Assertions.assertEquals(client.getMobileNumber(), newClient.getMobileNumber());
		
	}


}
