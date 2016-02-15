package tn.taktak.GestCommerciale_V1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.taktak.GestCommerciale_V1.entity.RegimeTva;

public interface RegTVARepository extends JpaRepository<RegimeTva, String> {

	@Query("select desRegimeTva from RegimeTva ")
	  List<String> findDesRegimTva();
}
