package tn.taktak.GestCommerciale_V1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.DetailDevisClient;
import tn.taktak.GestCommerciale_V1.repository.DetailDevisClientRepository;

@Service
public class DetailDevisClientService {

	@Autowired
	private DetailDevisClientRepository detailDevisClientRepository;
	
	public List<DetailDevisClient> findAll()
	{
		return detailDevisClientRepository.findAll();
	}
	
	public void save(DetailDevisClient detaildevisclient)
	{
		detailDevisClientRepository.save(detaildevisclient);
	}
	
	public void remove(DetailDevisClient detaildevisclient)
	{
		detailDevisClientRepository.delete(detaildevisclient);
	}
	
	public void saveList(List<DetailDevisClient> listeDetail)
	{
		detailDevisClientRepository.save(listeDetail);
	}
	
	public List<DetailDevisClient> findDetailOfDevis(String t)
	{
		return detailDevisClientRepository.findDetailOfDevis(t);
	}
}
