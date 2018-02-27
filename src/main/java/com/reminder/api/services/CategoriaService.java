package com.reminder.api.services;

import java.util.List;

import com.reminder.api.models.Categoria;

public interface CategoriaService {
	
	List<Categoria> listarTodos();
	
	Categoria listarPorId(String id);
	
	Categoria cadastrar(Categoria categoria);
	
	Categoria atualizar(Categoria categoria);
	
	void remover(String id);
}
