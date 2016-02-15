package tn.taktak.GestCommerciale_V1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.taktak.GestCommerciale_V1.entity.BonAchat;
import tn.taktak.GestCommerciale_V1.entity.FactureClient;


public interface BonAchatRepository extends  JpaRepository<BonAchat, String> {

	@Query("select f from BonAchat f where f.cbonAchat like %?1% or f.cfournisseur like %?1% or "+
			" f.dateCreation like %?1% or f.desAdresseFournisseur like %?1% or f.mtTotalHt like %?1% or f.mtTotalTtc like %?1%  ")
	List<BonAchat> filterBonAchat(String t);
	
	@Query("select f from BonAchat f where f.cfournisseur=?1")
	List<BonAchat> findBAOfFournisseur(String t);
}
