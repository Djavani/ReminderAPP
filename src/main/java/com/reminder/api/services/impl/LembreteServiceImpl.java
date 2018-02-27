package com.reminder.api.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reminder.api.models.Lembrete;
import com.reminder.api.repositories.LembreteRepository;
import com.reminder.api.services.LembreteService;

@Service
public class LembreteServiceImpl implements LembreteService {

	@Autowired
	LembreteRepository lembreteRepository;
	
	@Override
	public List<Lembrete> listarTodos() {		
		return lembreteRepository.findAll();
	}

	@Override
	public Lembrete listarPorId(String id) {		
		return lembreteRepository.findOne(id);
	}

	@Override
	public Lembrete cadastrar(Lembrete lembrete) {
		return lembreteRepository.save(lembrete);
	}

	@Override
	public Lembrete atualizar(Lembrete lembrete) {		
		return lembreteRepository.save(lembrete);
	}

	@Override
	public void remover(String id) {
		lembreteRepository.delete(id);
	}

}
