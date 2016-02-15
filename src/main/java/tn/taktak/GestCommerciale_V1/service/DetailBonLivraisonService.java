package tn.taktak.GestCommerciale_V1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.DetailBonLivraison;
import tn.taktak.GestCommerciale_V1.entity.DetailDevisClient;
import tn.taktak.GestCommerciale_V1.repository.DetailBonLivraisonRepository;

@Service
public class DetailBonLivraisonService {

	@Autowired
	private DetailBonLivraisonRepository detailBonLivraisonRepository;
	
	public List<DetailBonLivraison> findAll()
	{
		return detailBonLivraisonRepository.findAll();
	}
	
	public void save(DetailBonLivraison detailBonLivraison){
		detailBonLivraisonRepository.save(detailBonLivraison);
	}
	
	public void remove(DetailBonLivraison detailBonLivraison)
	{
		detailBonLivraisonRepository.delete(detailBonLivraison);
	}
	public void saveList(List<DetailBonLivraison> listDetail)
	{
		detailBonLivraisonRepository.save(listDetail);
	}
	
	public List<DetailBonLivraison> findDetailOfBonLiv(String t)
	{
		return detailBonLivraisonRepository.findDetailOfBonLiv(t);
	}
}
