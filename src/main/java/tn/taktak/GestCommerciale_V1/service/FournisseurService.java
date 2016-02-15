package tn.taktak.GestCommerciale_V1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.Client;
import tn.taktak.GestCommerciale_V1.entity.Fournisseur;
import tn.taktak.GestCommerciale_V1.repository.FournisseurRepository;

@Service
public class FournisseurService {

	@Autowired
	private FournisseurRepository fournisseurRepository;
	
	public List<Fournisseur> findAll() {
		return fournisseurRepository.findAll();
	}

	public void save(Fournisseur fournisseur) {
		fournisseurRepository.save(fournisseur);
	}

	public void remove(Fournisseur fournisseur) {
		fournisseurRepository.delete(fournisseur);
	}
	
	public List<Fournisseur> filterFournisseur(String t)
	{
		return fournisseurRepository.filterFournisseur(t);
	}
	public Fournisseur findFournisseurById(String id)
	{
		Fournisseur art1= fournisseurRepository.findOne(id);
		return art1;
	}	
}
