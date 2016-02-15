package tn.taktak.GestCommerciale_V1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.Article;
import tn.taktak.GestCommerciale_V1.repository.ArticleRepository;

@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;
	
	public List<Article> findAll() {
		return articleRepository.findAll();
	}

	public void save(Article article) {
		articleRepository.save(article);
	}

	public void remove(Article article) {
		articleRepository.delete(article);
	}
	
	public List<Article> filterArticle(String t)
	{
		return articleRepository.filterArticle(t);
	}
	
	public Article findArticleById(String id)
	{
//		Article art1=articleRep.findOne(id);
//		Article art2=articleRep.getOne(id);

		Article art1= articleRepository.findOne(id);
		return art1;

	}
}
