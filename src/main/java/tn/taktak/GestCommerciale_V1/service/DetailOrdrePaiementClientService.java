package tn.taktak.GestCommerciale_V1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.DetailOrdrePaiementClient;
import tn.taktak.GestCommerciale_V1.repository.DetailOrdrePaiementClientRepository;

@Service
public class DetailOrdrePaiementClientService {

	@Autowired
	private DetailOrdrePaiementClientRepository detailOrdrePaiementClientRepository;
	
	public List<DetailOrdrePaiementClient> findAll()
	{
		return detailOrdrePaiementClientRepository.findAll();
	}
	
	public void save(DetailOrdrePaiementClient detaildevisclient)
	{
		detailOrdrePaiementClientRepository.save(detaildevisclient);
	}
	
	public void remove(DetailOrdrePaiementClient detaildevisclient)
	{
		detailOrdrePaiementClientRepository.delete(detaildevisclient);
	}
	
	public void saveList(List<DetailOrdrePaiementClient> listeDetail)
	{
		detailOrdrePaiementClientRepository.save(listeDetail);
	}
	
	public List<DetailOrdrePaiementClient> findDetailOfOrdrePayClient(String t)
	{
		return detailOrdrePaiementClientRepository.findDetailOfOrdrePayClient(t);
	}
	
	public   List<DetailOrdrePaiementClient> findListFactureDetail(String t)
	{
		return detailOrdrePaiementClientRepository.findListFactureDetail(t);
	}
}
