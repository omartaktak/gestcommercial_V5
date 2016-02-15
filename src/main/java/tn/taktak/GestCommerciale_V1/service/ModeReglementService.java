package tn.taktak.GestCommerciale_V1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.ModeReglement;
import tn.taktak.GestCommerciale_V1.entity.Unite;
import tn.taktak.GestCommerciale_V1.repository.ModeReglementRepository;

@Service
public class ModeReglementService {

	@Autowired
	private ModeReglementRepository modeReglementRepository;
	
	public List<ModeReglement> findAll() {
		return modeReglementRepository.findAll();
	}		

	public void save(ModeReglement mr) {
		modeReglementRepository.save(mr);
	}

	public void remove(ModeReglement mr) {
		modeReglementRepository.delete(mr);
	}
	
	public List<ModeReglement> filterModeReglement(String t)
	{
		return modeReglementRepository.filterModeReglement(t);
	}

}
