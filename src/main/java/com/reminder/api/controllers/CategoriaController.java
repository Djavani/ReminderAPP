package com.reminder.api.controllers;

import static org.springframework.data.domain.Sort.Direction.ASC;

import java.util.ArrayList;
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

import com.reminder.api.models.Categoria;
import com.reminder.api.repositories.CategoriaRepository;
import com.reminder.api.responses.Response;
import com.reminder.api.services.CategoriaService;

@RestController
@RequestMapping(path = "/api/categorias")
public class CategoriaController {
	
	@Autowired
	CategoriaService categoriaService;
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	@GetMapping
	public ResponseEntity<Response<List<Categoria>>> listarTodos() {
		return ResponseEntity.ok(new Response<List<Categoria>>(this.categoriaService.listarTodos()));
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Response<Categoria>> listarPorId(@PathVariable(name = "id") String id) {
		return ResponseEntity.ok(new Response<Categoria>(this.categoriaService.listarPorId(id)));
	}

	// consulta com Like
	@GetMapping(path = "/{descricao}/like")
	public List<Categoria> listarPorLike(@PathVariable(name = "descricao") String descricao) {
		return categoriaRepository.findByDescricaoLikeIgnoreCase(descricao);
	}

	// consulta com FullTextSearch
	@GetMapping(path = "/{descricao}/fts")
	public List<Categoria> listFullText(@PathVariable(name = "descricao") String descricao) {
		Pageable pages = new PageRequest(0, 10, new Sort(new Order(ASC, "score")));

		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(descricao);

		return categoriaRepository.findAllBy(criteria, pages);
	}

	@PostMapping
	public ResponseEntity<Response<Categoria>> cadastrar(@Valid @RequestBody Categoria Categoria, BindingResult result) {
		if (result.hasErrors()) {
			List<String> erros = new ArrayList<String>();
			result.getAllErrors().forEach(erro -> erros.add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(new Response<Categoria>(erros));
		}
		return ResponseEntity.ok(new Response<Categoria>(this.categoriaService.cadastrar(Categoria)));
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<Response<Categoria>> atualizar(@PathVariable(name = "id") String id,
			@Valid @RequestBody Categoria Categoria, BindingResult result) {
		if (result.hasErrors()) {
			List<String> erros = new ArrayList<String>();
			result.getAllErrors().forEach(erro -> erros.add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(new Response<Categoria>(erros));
		}

		Categoria.setId(id);
		return ResponseEntity.ok(new Response<Categoria>(this.categoriaService.atualizar(Categoria)));
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Response<Integer>> remover(@PathVariable(name = "id") String id) {
		Categoria categoria = this.categoriaService.listarPorId(id);
		this.categoriaService.remover(categoria.getId());
		return ResponseEntity.ok(new Response<Integer>(1));
	}
}
