package tn.taktak.GestCommerciale_V1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.AvoirClient;
import tn.taktak.GestCommerciale_V1.entity.DevisClient;
import tn.taktak.GestCommerciale_V1.entity.OrdrePaiementClient;
import tn.taktak.GestCommerciale_V1.repository.AvoirClientRepository;


@Service
public class AvoirClientService {


	@Autowired
	private AvoirClientRepository avoirClientRepository;
	
	public List<AvoirClient> findAll()
	{
		return avoirClientRepository.findAll();
	}
	
	public void save(AvoirClient avoirClient)
	{
		avoirClientRepository.save(avoirClient);
	}
	
	public void remove (AvoirClient avoirClient)
	{
		avoirClientRepository.delete(avoirClient);
	}
	
	public List<AvoirClient> findListOfAvoirClient(String idClient)
	{
		return avoirClientRepository.findListOfAvoirClient(idClient);
	}
	
	public AvoirClient findAVCById(String id)
	{
		return avoirClientRepository.findOne(id);
	}
	
	public List<AvoirClient> filterAvClt(String t)
	{
		return avoirClientRepository.filterAvClt(t);
	}
}
