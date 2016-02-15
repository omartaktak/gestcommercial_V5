package tn.taktak.GestCommerciale_V1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.DetailDevisClient;
import tn.taktak.GestCommerciale_V1.entity.DetailFactureClient;
import tn.taktak.GestCommerciale_V1.repository.DetailFactureClientRepository;

@Service
public class DetailFactureClientService {

	@Autowired
	private DetailFactureClientRepository detailFactureClientRepository;
	
	public List<DetailFactureClient> findAll()
	{
		return detailFactureClientRepository.findAll();
	}
	
	public void save(DetailFactureClient detailfactureClient)
	{
		detailFactureClientRepository.save(detailfactureClient);
	}
	
	public void remove(DetailFactureClient detailFactureClient)
	{
		detailFactureClientRepository.delete(detailFactureClient);
	}
	
	public void saveList(List<DetailFactureClient> listDetail)
	{
		detailFactureClientRepository.save(listDetail);
	}
	
	public List<DetailFactureClient> findDetailOfFacture(String t)
	{
		return detailFactureClientRepository.findDetailOfFacture(t);
	}
	
}
