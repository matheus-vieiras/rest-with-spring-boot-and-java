package br.com.projeto.aprendizado.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.aprendizado.data.vo.v1.security.AccountCredentialsVo;
import br.com.projeto.aprendizado.services.AuthServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "AuthenticationEndpoint") 
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	AuthServices authServices;
	
	@Operation(summary = "Authenticates a user and returns a token")
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/signin", produces = { "application/json", "application/xml", "application/x-yaml" }, 
			consumes = { "application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity signin(@RequestBody AccountCredentialsVo data) {
		try {
			if (checkIfParamsIsNotNull(data)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
			
			var token = authServices.signin(data);
			if (token == null)	return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
			
			return token;
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}

	private boolean checkIfParamsIsNotNull(AccountCredentialsVo data) {
		return data == null || data.getUserName() == null || data.getUserName().isBlank() || data.getPassword() == null || data.getPassword().isBlank();
	}
}
