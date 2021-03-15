package br.com.github.repo.scraping.exception.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.github.repo.scraping.dto.ErrorDTO;
import br.com.github.repo.scraping.exception.RepositoryException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { RepositoryException.class })
	protected ResponseEntity<Object> handleRepositoryException(Exception ex, WebRequest request) {
		
		return handleExceptionInternal(ex, ErrorDTO.builder().message(ex.getMessage()).build(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
}
