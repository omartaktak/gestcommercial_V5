package tn.taktak.GestCommerciale_V1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.CompteurPiece;
import tn.taktak.GestCommerciale_V1.repository.CompteurPieceRepository;

@Service
public class CompteurPieceService {

	@Autowired
	private CompteurPieceRepository compteurPieceRepository;
	
	public void save(CompteurPiece compteur) {
		compteurPieceRepository.save(compteur);
	}

	public void remove(CompteurPiece compteur) {
		compteurPieceRepository.delete(compteur);
	}
	
	public CompteurPiece findCompteurById(String id)
	{
		CompteurPiece compt= compteurPieceRepository.findOne(id);
		return compt;
	}
}
