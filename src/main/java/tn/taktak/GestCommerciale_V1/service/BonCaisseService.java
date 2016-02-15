package tn.taktak.GestCommerciale_V1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.BonCaisse;
import tn.taktak.GestCommerciale_V1.entity.DevisClient;
import tn.taktak.GestCommerciale_V1.repository.BonCaisseRepository;

@Service
public class BonCaisseService {

	@Autowired
	private BonCaisseRepository bonCaisseRepository;
	
	public List<BonCaisse> findAll()
	{
		return bonCaisseRepository.findAll();
	}
	
	public void save(BonCaisse bonCaisse)
	{
		bonCaisseRepository.save(bonCaisse);
	}
	
	public void remove(BonCaisse bonCaisse)
	{
		bonCaisseRepository.delete(bonCaisse);
	}
	
	public List<BonCaisse> filterBonCaisse(String t)
	{
		return bonCaisseRepository.filterBonCaisse(t);
	}
	
}
