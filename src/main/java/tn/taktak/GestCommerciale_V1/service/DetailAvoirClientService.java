package tn.taktak.GestCommerciale_V1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.DetailAvoirClient;
import tn.taktak.GestCommerciale_V1.entity.DetailDevisClient;
import tn.taktak.GestCommerciale_V1.repository.DetailAvoirClientRepository;


@Service
public class DetailAvoirClientService {

	@Autowired
	private DetailAvoirClientRepository detailAvoirClientRepository;
	
	public List<DetailAvoirClient> findAll()
	{
		return detailAvoirClientRepository.findAll();
	}
	
	public void save(DetailAvoirClient detailAvoirClient)
	{
		detailAvoirClientRepository.save(detailAvoirClient);
	}
	
	public void remove(DetailAvoirClient detailAvoirClient)
	{
		detailAvoirClientRepository.delete(detailAvoirClient);
	}
	
	public void saveList(List<DetailAvoirClient> listDetail)
	{
		detailAvoirClientRepository.save(listDetail);
	}
	
	public List<DetailAvoirClient> findDetailOfAvClt(String t)
	{
		return detailAvoirClientRepository.findDetailOfAvClt(t);
	}
}
