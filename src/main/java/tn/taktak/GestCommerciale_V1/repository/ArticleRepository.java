package tn.taktak.GestCommerciale_V1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.taktak.GestCommerciale_V1.entity.Article;


public interface ArticleRepository extends JpaRepository<Article, String> {

	@Query("select f from Article f where f.carticle like %?1% or f.desArticle like %?1% or "+
			" f.cunite like %?1% ")
	List<Article> filterArticle(String t);
	
}
