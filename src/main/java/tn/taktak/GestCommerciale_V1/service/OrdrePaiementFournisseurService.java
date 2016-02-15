package tn.taktak.GestCommerciale_V1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.OrdrePaiementClient;
import tn.taktak.GestCommerciale_V1.entity.OrdrePaiementFournisseur;
import tn.taktak.GestCommerciale_V1.repository.OrdrePaiementFournisseurRepository;

@Service
public class OrdrePaiementFournisseurService {

	@Autowired
	private OrdrePaiementFournisseurRepository ordrePaiementFournisseurRepository;
	
	public List<OrdrePaiementFournisseur> findAll() {
		return ordrePaiementFournisseurRepository.findAll();
	}

	public void save(OrdrePaiementFournisseur opf) {
		ordrePaiementFournisseurRepository.save(opf);
	}

	public void remove(OrdrePaiementFournisseur opf) {
		ordrePaiementFournisseurRepository.delete(opf);
	}
	
	public List<OrdrePaiementFournisseur> findListOfOrdrePayFournisseur(String idFourn)
	{
		return ordrePaiementFournisseurRepository.findListOfOrdrePayFournisseur(idFourn);
	}
	
	public OrdrePaiementFournisseur findOPFById(String id)
	{
		return ordrePaiementFournisseurRepository.findOne(id);
	}
	
	public List<OrdrePaiementFournisseur> findAllListOfOrdrePayFournisseur(String idFourn)
	{
		return ordrePaiementFournisseurRepository.findAllListOfOrdrePayFournisseur(idFourn);
	}
	
}
