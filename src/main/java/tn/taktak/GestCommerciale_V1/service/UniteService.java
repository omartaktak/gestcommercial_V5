package tn.taktak.GestCommerciale_V1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.Tva;
import tn.taktak.GestCommerciale_V1.entity.Unite;
import tn.taktak.GestCommerciale_V1.repository.UniteRepository;

@Service
public class UniteService {

	@Autowired
	private UniteRepository uniteRepository;
	
	public List<String> findDesUnite(){
		return uniteRepository.findDesUnite();
	}
	
	public List<Unite> findAll() {
		return uniteRepository.findAll();
	}
		

	public void save(Unite unite) {
		uniteRepository.save(unite);
	}

	public void remove(Unite unite) {
		uniteRepository.delete(unite);
	}
	
	public List<Unite> filterUnite(String t)
	{
		return uniteRepository.filterUnite(t);
	}
	
}
