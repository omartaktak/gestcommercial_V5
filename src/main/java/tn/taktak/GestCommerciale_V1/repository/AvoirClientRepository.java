package tn.taktak.GestCommerciale_V1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.taktak.GestCommerciale_V1.entity.AvoirClient;
import tn.taktak.GestCommerciale_V1.entity.DevisClient;

public interface AvoirClientRepository extends JpaRepository<AvoirClient, String> {

	@Query("select f from AvoirClient f "
			+ " where f.cclient=?1"
			+ " AND f.soldee =null ")
	  List<AvoirClient> findListOfAvoirClient(String idClient);
	
	@Query("select f from AvoirClient f where f.cavoirClient like %?1% or f.cclient like %?1% or "+
			" f.dateCreation like %?1% or f.desAdresseClient like %?1% or f.mtTotalHt like %?1% or f.mtTotalTtc like %?1%  ")
	List<AvoirClient> filterAvClt(String t);
	
}
