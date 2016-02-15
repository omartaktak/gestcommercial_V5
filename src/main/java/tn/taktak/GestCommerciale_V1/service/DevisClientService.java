package tn.taktak.GestCommerciale_V1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.Article;
import tn.taktak.GestCommerciale_V1.entity.DevisClient;
import tn.taktak.GestCommerciale_V1.repository.DevisClientRepository;

@Service
public class DevisClientService {

	@Autowired
	private DevisClientRepository devisClientRepository;
	
	public List<DevisClient> findAll() {
		return devisClientRepository.findAll();
	}

	public void save(DevisClient devisclient) {
		devisClientRepository.save(devisclient);
	}

	public void remove(DevisClient devisclient) {
		devisClientRepository.delete(devisclient);
	}
	
	public List<DevisClient> filterDevis(String t)
	{
		return devisClientRepository.filterDevis(t);
	}
	
}
