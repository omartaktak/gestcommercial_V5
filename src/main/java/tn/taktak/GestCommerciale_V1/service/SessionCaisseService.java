package tn.taktak.GestCommerciale_V1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.SessionCaisse;
import tn.taktak.GestCommerciale_V1.repository.SessionCaisseRepository;

@Service
public class SessionCaisseService {

	@Autowired
	private SessionCaisseRepository sessionCaisseRepository;
	
	public List<SessionCaisse> findAll()
	{
		return sessionCaisseRepository.findAll();
	}
	
	public void save(SessionCaisse sessioncaisse)
	{
		sessionCaisseRepository.save(sessioncaisse);
	}
	
	public void remove(SessionCaisse sessionCaisse)
	{
		sessionCaisseRepository.delete(sessionCaisse);
	}
}
