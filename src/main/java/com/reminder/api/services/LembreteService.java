package com.reminder.api.services;

import java.util.List;

import com.reminder.api.models.Lembrete;

public interface LembreteService {
	
	List<Lembrete> listarTodos();
	
	Lembrete listarPorId(String id);
	
	Lembrete cadastrar(Lembrete lembrete);
	
	Lembrete atualizar(Lembrete lembrete);
	
	void remover(String id);

}
