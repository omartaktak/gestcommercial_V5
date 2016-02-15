package tn.taktak.GestCommerciale_V1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.DetailOrdrePaiementFournisseur;
import tn.taktak.GestCommerciale_V1.repository.DetailOrdrePaiementFournisseurRepository;

@Service
public class DetailOrdrePaiementFournisseurService {

	@Autowired
	private DetailOrdrePaiementFournisseurRepository detailOrdrePaiementFournisseurRepository;
	
	public List<DetailOrdrePaiementFournisseur> findAll()
	{
		return detailOrdrePaiementFournisseurRepository.findAll();
	}
	
	public void save(DetailOrdrePaiementFournisseur detaildevisclient)
	{
		detailOrdrePaiementFournisseurRepository.save(detaildevisclient);
	}
	
	public void remove(DetailOrdrePaiementFournisseur detaildevisclient)
	{
		detailOrdrePaiementFournisseurRepository.delete(detaildevisclient);
	}
	
	public void saveList(List<DetailOrdrePaiementFournisseur> listeDetail)
	{
		detailOrdrePaiementFournisseurRepository.save(listeDetail);
	}
	
	public List<DetailOrdrePaiementFournisseur> findDetailOfOrdrePayFournisseur(String t)
	{
		return detailOrdrePaiementFournisseurRepository.findDetailOfOrdrePayFournisseur(t);
	}
	
	public   List<DetailOrdrePaiementFournisseur> findListFactureDetail(String t)
	{
		return detailOrdrePaiementFournisseurRepository.findListBADetail(t);
	}
}
