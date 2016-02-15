package tn.taktak.GestCommerciale_V1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.BonAchat;
import tn.taktak.GestCommerciale_V1.entity.DevisClient;
import tn.taktak.GestCommerciale_V1.entity.FactureClient;
import tn.taktak.GestCommerciale_V1.repository.BonAchatRepository;

@Service
public class BonAchatService {

	@Autowired
	private BonAchatRepository bonAchatRepository;
	
	public List<BonAchat> findAll()
	{
		return bonAchatRepository.findAll();
	}
	
	public void save(BonAchat bonAchat)
	{
		bonAchatRepository.save(bonAchat);
	}
	
	public void remove (BonAchat bonAchat)
	{
		bonAchatRepository.delete(bonAchat);
	}

	public List<BonAchat> filterBonAchat(String t)
	{
		return bonAchatRepository.filterBonAchat(t);
	}
	
	public List<BonAchat> findBAOfFournisseur(String idFourn)
	{
		return  bonAchatRepository.findBAOfFournisseur(idFourn);
	}
	
}
