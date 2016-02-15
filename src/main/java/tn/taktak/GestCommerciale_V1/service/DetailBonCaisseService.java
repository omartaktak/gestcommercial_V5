package tn.taktak.GestCommerciale_V1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.DetailBonCaisse;
import tn.taktak.GestCommerciale_V1.entity.DetailDevisClient;
import tn.taktak.GestCommerciale_V1.repository.DetailBonCaisseRepository;

@Service
public class DetailBonCaisseService {

	@Autowired
	private DetailBonCaisseRepository detailBonCaisseRepository;
	
	public List<DetailBonCaisse> findAll()
	{
		return detailBonCaisseRepository.findAll();
	}
	
	public void save(DetailBonCaisse detailBonCaisse)
	{
		detailBonCaisseRepository.save(detailBonCaisse);
	}
	
	public void remove(DetailBonCaisse detailBonCaisse)
	{
		detailBonCaisseRepository.delete(detailBonCaisse);
	}
	
	public void saveList(List<DetailBonCaisse>listDetail)
	{
		detailBonCaisseRepository.save(listDetail);
	}
	
	public List<DetailBonCaisse> findDetailOfBC(String t)
	{
		return detailBonCaisseRepository.findDetailOfBC(t);
	}
}
