package tn.taktak.GestCommerciale_V1.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.Tva;
import tn.taktak.GestCommerciale_V1.repository.TVARepository;

@Service
public class TvaService  {

	@Autowired
	private TVARepository tVARepository;
	
	public List<Tva> findAll() {
		return tVARepository.findAll();
	}

	public void save(Tva tva) {
		tVARepository.save(tva);
	}

	public void remove(Tva tva) {
		tVARepository.delete(tva);
	}
	
	public List<BigDecimal> findTauxTva(){
	 return	tVARepository.findTauxTva();
	}
	
	public List<Tva> filterTva(String t)
	{
		return tVARepository.filterTva(t);
	}
	
	public Tva findTvaById(String id)
	{

		Tva tva= tVARepository.findOne(id);
		return tva;

	}
	
}
