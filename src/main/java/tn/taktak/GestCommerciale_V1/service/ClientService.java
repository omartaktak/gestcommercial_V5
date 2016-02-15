package tn.taktak.GestCommerciale_V1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.Client;
import tn.taktak.GestCommerciale_V1.repository.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;
	
	public List<Client> findAll() {
		return clientRepository.findAll();
	}

	public void save(Client client) {
		clientRepository.save(client);
	}

	public void remove(Client client) {
		clientRepository.delete(client);
	}
	
	public List<Client> filterClient(String t)
	{
		return clientRepository.filterClient(t);
	}
	
	public Client findClientById(String id)
	{
		Client art1= clientRepository.findOne(id);
		return art1;
	}	
}
