package tn.taktak.GestCommerciale_V1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.taktak.GestCommerciale_V1.entity.DetailFactureClient;
import tn.taktak.GestCommerciale_V1.entity.FactureClient;

public interface FactureClientRepository extends JpaRepository<FactureClient, String> {

	@Query("select f from FactureClient f where f.cfactureClient like %?1% or f.cclient like %?1% or "+
			" f.dateCreation like %?1% or f.desAdresseClient like %?1% or f.mtTotalHt like %?1% or f.mtTotalTtc like %?1%  ")
	List<FactureClient> filterFacture(String t);
	
	@Query("select f from FactureClient f where f.cclient=?1")
	List<FactureClient> findFactureOfClient(String t);
	
}
