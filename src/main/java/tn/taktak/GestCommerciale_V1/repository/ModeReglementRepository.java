package tn.taktak.GestCommerciale_V1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.taktak.GestCommerciale_V1.entity.ModeReglement;


public interface ModeReglementRepository extends JpaRepository<ModeReglement, String> {

	@Query("select f from ModeReglement f where f.cmodeReglement like %?1% or f.desModeReglement like %?1% ")
	List<ModeReglement> filterModeReglement(String t);
}
