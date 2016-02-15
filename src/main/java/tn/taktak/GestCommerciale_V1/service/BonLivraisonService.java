package tn.taktak.GestCommerciale_V1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.BonLivraison;
import tn.taktak.GestCommerciale_V1.entity.DevisClient;
import tn.taktak.GestCommerciale_V1.repository.BonLivraisonRepository;

@Service
public class BonLivraisonService {

	@Autowired
	private BonLivraisonRepository bonLivraisonRepository;
	
	public List<BonLivraison> findAll()
	{
		return bonLivraisonRepository.findAll();
	}
	
	public void save(BonLivraison bonLivraison)
	{
		bonLivraisonRepository.save(bonLivraison);
	}
	
	public void remove(BonLivraison bonLivraison)
	{
		bonLivraisonRepository.delete(bonLivraison);
	}
	
	public List<BonLivraison> filterBonLiv(String t)
	{
		return bonLivraisonRepository.filterBonLiv(t);
	}
}
