package com.reminder.api.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.reminder.api.models.Categoria;

public interface CategoriaRepository extends MongoRepository<Categoria, String> {
	
	public List<Categoria> findAllBy(TextCriteria criteria, Pageable pages);
	
	public List<Categoria> findByDescricaoLikeIgnoreCase(String descricao);

}
