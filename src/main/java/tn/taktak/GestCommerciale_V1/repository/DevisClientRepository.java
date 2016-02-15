package tn.taktak.GestCommerciale_V1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.taktak.GestCommerciale_V1.entity.Article;
import tn.taktak.GestCommerciale_V1.entity.DevisClient;

public interface DevisClientRepository extends JpaRepository<DevisClient, String> {

	@Query("select f from DevisClient f where f.cdevisClient like %?1% or f.cclient like %?1% or "+
			" f.dateCreation like %?1% or f.desAdresseClient like %?1% or f.mtTotalHt like %?1% or f.mtTotalTtc like %?1%  ")
	List<DevisClient> filterDevis(String t);
	
}
