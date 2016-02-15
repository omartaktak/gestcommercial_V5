package tn.taktak.GestCommerciale_V1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.taktak.GestCommerciale_V1.entity.FactureClient;
import tn.taktak.GestCommerciale_V1.entity.Representant;

public interface RepresentantRepository extends JpaRepository<Representant, String> {

	@Query("select f from Representant f where f.crepresentant like %?1% or "
			+ "f.nom like %?1% or f.prenom like %?1% or f.desAdresse like %?1% "
		 + "or f.pays like %?1% or f.ville like %?1% or f.tel like %?1% "
		 + "or f.gsm1 like %?1% or f.login like %?1% or f.codePostal like %?1% "	)
	List<Representant> filterRepresentant(String t);
	
	@Query("select f from Representant f where f.login=?1")
	Representant finduserByLogin(String t);
}
