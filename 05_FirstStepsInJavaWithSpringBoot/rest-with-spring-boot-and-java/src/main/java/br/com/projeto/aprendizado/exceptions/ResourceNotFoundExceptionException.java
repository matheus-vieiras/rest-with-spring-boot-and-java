package br.com.projeto.aprendizado.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundExceptionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundExceptionException(String ex) {
		super(ex);
	}

}
