package br.com.projeto.aprendizado.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.aprendizado.data.vo.v1.PersonVo;
import br.com.projeto.aprendizado.data.vo.v2.PersonVoV2;
import br.com.projeto.aprendizado.services.PersonServices;
import br.com.projeto.aprendizado.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

//@CrossOrigin
@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "Endpoints for Managing People")
public class PersonController {

	@Autowired
	private PersonServices service;
//	private PersonServices service = new PersonService(); a anotação ja faz a instanciação em tempo de execução

	@GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Finds all People", description = "Finds all People", tags = { "People" }, 
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = {
							@Content(
								mediaType = "application/json",
								array = @ArraySchema(schema = @Schema(implementation = PersonVo.class))
							)
					}), 
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content), 
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content), 
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content), 
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) 
	})
	public ResponseEntity<Page<PersonVo>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "12") Integer size,
			@RequestParam(value = "direction", defaultValue = "asc") String direction
			) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
		return ResponseEntity.ok(service.findAll(pageable));
	}
	
	@GetMapping(value = "/findPersonByName/{firstName}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Finds People by name", description = "Finds People by name", tags = { "People" }, 
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = {
							@Content(
								mediaType = "application/json",
								array = @ArraySchema(schema = @Schema(implementation = PersonVo.class))
							)
					}), 
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content), 
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content), 
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content), 
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) 
	})
	public ResponseEntity<Page<PersonVo>> findPersonsByName(
			@PathVariable(value = "firstName") String firstName,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "12") Integer size,
			@RequestParam(value = "direction", defaultValue = "asc") String direction
			) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
		return ResponseEntity.ok(service.findPersonsByName(firstName, pageable));
	}
	
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Finds a Person", description = "Finds a Person", tags = { "People" }, 
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = PersonVo.class))
			), 
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content), 
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content), 
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content), 
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content), 
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) 
	})
	public PersonVo findById(@PathVariable(value = "id") Long id) throws Exception {
		return service.findById(id);
	}
	
	
	@CrossOrigin(origins = {"http://localhost:8080", "https://github.com/matheus-vieiras?tab=repositories"})
	@Operation(summary = "Adds a new Person", description = "Adds a new Person by passing in a JSON, XML, or YML", tags = { "People" }, 
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = PersonVo.class))
			), 
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content), 
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content), 
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content), 
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) 
	})
	@PostMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }, 
				 consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	public PersonVo create(@RequestBody PersonVo person) {
		return service.create(person);
	}
	
	
	
	@PostMapping(value = "/v2", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML },
								consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	public PersonVoV2 createV2(@RequestBody PersonVoV2 person) {
		return service.createV2(person);
	}
	
	

	@Operation(summary = "Updates a Person", description = "Updated a new Person by passing in a JSON, XML, or YML", tags = { "People" }, 
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = PersonVo.class))
			), 
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content), 
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content), 
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content), 
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) 
	})
	@PutMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }, 
				consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	public PersonVo update(@RequestBody PersonVo person) {
		return service.update(person);
	}
	
	
	@PatchMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Disable a specific Person by your ID", description = "Disable a specific Person by your ID", tags = { "People" }, 
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = PersonVo.class))
			), 
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content), 
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content), 
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content), 
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content), 
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) 
	})
	public PersonVo disablePerson(@PathVariable(value = "id") Long id) throws Exception {
		return service.disablePerson(id);
	}

	
	@Operation(summary = "Delete a Person", description = "Delete a new Person by passing in a JSON, XML, or YML", tags = { "People" }, 
	responses = {
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content), 
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content), 
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content), 
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content), 
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) 
	})
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
