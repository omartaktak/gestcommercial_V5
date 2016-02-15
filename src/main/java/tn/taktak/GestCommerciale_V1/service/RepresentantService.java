package tn.taktak.GestCommerciale_V1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.Representant;
import tn.taktak.GestCommerciale_V1.entity.Unite;
import tn.taktak.GestCommerciale_V1.repository.RepresentantRepository;

@Service
public class RepresentantService {

	@Autowired
	private RepresentantRepository representantRepository;
	
	public List<Representant> findAll()
	{
	   return	representantRepository.findAll();
	}
	
	public void save(Representant rep)
	{
		representantRepository.save(rep);
	}
	
	public void remove(Representant rep)
	{
		representantRepository.delete(rep);
	}
	
	public List<Representant> filterRepresentant(String t)
	{
		return representantRepository.filterRepresentant(t);
	}
	public Representant findUserByLogin(String login)
	{
		return representantRepository.finduserByLogin(login);
	}
}
