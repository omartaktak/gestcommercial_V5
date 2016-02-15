package tn.taktak.GestCommerciale_V1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.taktak.GestCommerciale_V1.entity.DetailBonCaisse;
import tn.taktak.GestCommerciale_V1.entity.DetailBonCaisseId;

public interface DetailBonCaisseRepository extends JpaRepository<DetailBonCaisse, DetailBonCaisseId> {

	@Query("select f from DetailBonCaisse f where f.cbonCaisse=?1")
	  List<DetailBonCaisse> findDetailOfBC(String t);
}
