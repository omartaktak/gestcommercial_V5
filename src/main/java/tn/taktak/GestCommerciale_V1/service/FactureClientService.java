package tn.taktak.GestCommerciale_V1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.DevisClient;
import tn.taktak.GestCommerciale_V1.entity.FactureClient;
import tn.taktak.GestCommerciale_V1.repository.FactureClientRepository;

@Service
public class FactureClientService {

	@Autowired
	private FactureClientRepository factureClientRepository;
	
	public List<FactureClient> findAll()
	{
		return factureClientRepository.findAll();
	}
	
	public void save(FactureClient factureClient)
	{
		factureClientRepository.save(factureClient);
	}
	
	public void remove (FactureClient factureClient)
	{
		factureClientRepository.delete(factureClient);
	}
	
	public List<FactureClient> filterFacture(String t)
	{
		return factureClientRepository.filterFacture(t);
	}
	public List<FactureClient> findFactureOfClient(String t)
	{
		return  factureClientRepository.findFactureOfClient(t);
	}
}
