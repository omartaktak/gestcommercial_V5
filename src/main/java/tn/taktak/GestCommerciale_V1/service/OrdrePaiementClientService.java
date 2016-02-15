package tn.taktak.GestCommerciale_V1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.Article;
import tn.taktak.GestCommerciale_V1.entity.OrdrePaiementClient;
import tn.taktak.GestCommerciale_V1.repository.OrdrePaiementClientRepository;


@Service
public class OrdrePaiementClientService {

	@Autowired
	private OrdrePaiementClientRepository ordrePaiementClientRepository;
	
	public List<OrdrePaiementClient> findAll() {
		return ordrePaiementClientRepository.findAll();
	}

	public void save(OrdrePaiementClient opc) {
		ordrePaiementClientRepository.save(opc);
	}

	public void remove(OrdrePaiementClient opc) {
		ordrePaiementClientRepository.delete(opc);
	}
	
	public List<OrdrePaiementClient> findListOfOrdrePayClient(String idClient)
	{
		return ordrePaiementClientRepository.findListOfOrdrePayClient(idClient);
	}
	
	public List<OrdrePaiementClient> findAllListOfOrdrePayClient(String idClient)
	{
		return ordrePaiementClientRepository.findAllListOfOrdrePayClient(idClient);
	}
	public OrdrePaiementClient findOPCById(String id)
	{
		return ordrePaiementClientRepository.findOne(id);
	}
//	public List<OrdrePaiementClient> filterDevis(String t)
//	{
//		return ordrePaiementClientRepository.filterDevis(t);
//	}
}
