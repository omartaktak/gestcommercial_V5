package tn.taktak.GestCommerciale_V1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.DetailBonAchat;
import tn.taktak.GestCommerciale_V1.entity.DetailDevisClient;
import tn.taktak.GestCommerciale_V1.repository.DetailBonAchatRepository;

@Service
public class DetailBonAchatService {

	@Autowired
	private DetailBonAchatRepository detailBonAchatRepository;
	
	public List<DetailBonAchat> findAll()
	{
		return detailBonAchatRepository.findAll();
	}
	
	public void save(DetailBonAchat detailBonAchat)
	{
		detailBonAchatRepository.save(detailBonAchat);
	}
	
	public void remove(DetailBonAchat detailBonAchat)
	{
		detailBonAchatRepository.delete(detailBonAchat);
	}
	
	public void saveList(List<DetailBonAchat> listDetail)
	{
		detailBonAchatRepository.save(listDetail);
	}
	
	public List<DetailBonAchat> findDetailOfBA(String t)
	{
		return detailBonAchatRepository.findDetailOfBA(t);
	}
}
