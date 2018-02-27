package com.reminder.api.controllers;

import static org.springframework.data.domain.Sort.Direction.ASC;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reminder.api.models.Lembrete;
import com.reminder.api.repositories.LembreteRepository;
import com.reminder.api.responses.Response;
import com.reminder.api.services.LembreteService;

@RestController
@RequestMapping( path = "api/lembretes")
public class LembreteController {
	
	@Autowired
	LembreteService lembreteService;
	
	@Autowired
	LembreteRepository lembreteRepository;
	
	@GetMapping
	public ResponseEntity<Response<List<Lembrete>>> listarTodos() {
		return ResponseEntity.ok(new Response<List<Lembrete>>(this.lembreteService.listarTodos()));		
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Response<Lembrete>> listarPorId(@PathVariable(name = "id") String id) {
		return ResponseEntity.ok(new Response<Lembrete>(this.lembreteService.listarPorId(id)));
	}
	
	@GetMapping(path = "/{descricao}/like")
	public List<Lembrete> listarPorLike(@PathVariable(name = "descricao") String descricao) {
		//return ResponseEntity.ok(new Response<Lembrete>(this.lembreteRepository.findByDescricaoLikeIgnoreCase(descricao)));
		return lembreteRepository.findByDescricaoLikeIgnoreCase(descricao);
	}
	
	@GetMapping(path = "/{descricao}/fts")
	public List<Lembrete> listFullTextSearch(@PathVariable(name = "descricao") String descricao) {
		Pageable pages = new PageRequest(0, 10, new Sort(new Order(ASC, "score")));
		
		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(descricao);
		return lembreteRepository.findAllBy(criteria, pages);
	}
	
	@GetMapping(path = "/{de}/{ate}")	
    public List<Lembrete> list(@PathVariable String de, @PathVariable String ate) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date dataDe = formato.parse(de);
        Date dataAte = formato.parse(ate);

        return lembreteRepository.findByDataBetween(dataDe, dataAte);
    }
	
	@PostMapping
	public ResponseEntity<Response<Lembrete>> cadastrar(@Valid @RequestBody Lembrete lembrete, BindingResult result) {
		if (result.hasErrors()) {
			List<String> erros = new ArrayList<String>();
			result.getAllErrors().forEach(erro -> erros.add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(new Response<Lembrete>(erros));
		}
		return ResponseEntity.ok(new Response<Lembrete>(this.lembreteService.cadastrar(lembrete)));
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<Response<Lembrete>> atualizar(@PathVariable(name = "id") String id,
			@Valid @RequestBody Lembrete lembrete, BindingResult result) {
		if (result.hasErrors()) {
			List<String> erros = new ArrayList<String>();
			result.getAllErrors().forEach(erro -> erros.add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(new Response<Lembrete>(erros));
		}

		lembrete.setId(id);
		return ResponseEntity.ok(new Response<Lembrete>(this.lembreteService.atualizar(lembrete)));
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Response<Integer>> remover(@PathVariable(name = "id") String id) {
		this.lembreteService.remover(id);
		return ResponseEntity.ok(new Response<Integer>(1));
	}
}
