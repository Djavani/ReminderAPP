package com.reminder.api.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.reminder.api.models.Lembrete;

public interface LembreteRepository extends MongoRepository<Lembrete, String> {

	public List<Lembrete> findAllBy(TextCriteria criteria, Pageable pages);
	
	public List<Lembrete> findByDescricaoLikeIgnoreCase(String descricao);
	
	public List<Lembrete> findByDataBetween(Date dataDe, Date dataAte);
}
