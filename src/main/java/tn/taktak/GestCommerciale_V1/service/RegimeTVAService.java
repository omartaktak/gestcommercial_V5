package tn.taktak.GestCommerciale_V1.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.RegimeTva;
import tn.taktak.GestCommerciale_V1.repository.RegTVARepository;

@Service
public class RegimeTVAService  {

	@Autowired
	private RegTVARepository regTVARepository;
	
	public List<RegimeTva> findAll() {
		return regTVARepository.findAll();
	}

	public void save(RegimeTva RegTVA) {
		regTVARepository.save(RegTVA);
	}

	public void remove(RegimeTva RegTVA) {
		regTVARepository.delete(RegTVA);
	}
	
	public List<String> findDesRegimTva(){
	 return	regTVARepository.findDesRegimTva();
	}
}
