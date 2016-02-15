package tn.taktak.GestCommerciale_V1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.taktak.GestCommerciale_V1.entity.BonLivraison;

public interface BonLivraisonRepository extends JpaRepository<BonLivraison, String> {

	@Query("select f from BonLivraison f where f.cbonLivraison like %?1% or f.cclient like %?1% or "+
			" f.dateCreation like %?1% or f.desAdresseClient like %?1% or f.mtTotalHt like %?1% or f.mtTotalTtc like %?1%  ")
	List<BonLivraison> filterBonLiv(String t);
	
}
