package com.reminder.api.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reminder.api.models.Categoria;
import com.reminder.api.repositories.CategoriaRepository;
import com.reminder.api.services.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	CategoriaRepository categoriaRepository;

	@Override
	public List<Categoria> listarTodos() {		
		return this.categoriaRepository.findAll();
	}

	@Override
	public Categoria listarPorId(String id) {		
		return this.categoriaRepository.findOne(id);
	}

	@Override
	public Categoria cadastrar(Categoria categoria) {		
		return this.categoriaRepository.save(categoria);
	}

	@Override
	public Categoria atualizar(Categoria categoria) {		
		return this.categoriaRepository.save(categoria);
	}

	@Override
	public void remover(String id) {
		this.categoriaRepository.delete(id);
		
	}
	
	
}
