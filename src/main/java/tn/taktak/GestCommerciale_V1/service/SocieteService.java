package tn.taktak.GestCommerciale_V1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.Societe;
import tn.taktak.GestCommerciale_V1.repository.SocieteRepository;


@Service
public class SocieteService {

	@Autowired
	private SocieteRepository societeRepository;
	
	
	public List<Societe> findAll()
	{
	   return	societeRepository.findAll();
	}
	
	public void save(Societe rep)
	{
		societeRepository.save(rep);
	}
	
	public void remove(Societe rep)
	{
		societeRepository.delete(rep);
	}
}
