package tn.taktak.GestCommerciale_V1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.taktak.GestCommerciale_V1.entity.BonCaisse;

public interface BonCaisseRepository extends JpaRepository<BonCaisse, String> {

	@Query("select f from BonCaisse f where f.cbonCaisse like %?1% or f.cclient like %?1% or "+
			" f.dateCreation like %?1% or f.desAdresseClient like %?1% or f.mtTotalHt like %?1% or f.mtTotalTtc like %?1%  ")
	List<BonCaisse> filterBonCaisse(String t);

}
