package com.reminder.api.controllers.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.reminder.api.services.exception.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	// objeto não encontrado
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objetoNaoEncontrado(ObjectNotFoundException e, HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	// tempo de tentativa de comunicação com BD expirou
	@ExceptionHandler(org.springframework.dao.DuplicateKeyException.class)
	public ResponseEntity<StandardError> duplicateKeyError(org.springframework.dao.DuplicateKeyException e,
			HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Já existe um registro salvo com este nome", System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
	}
}
