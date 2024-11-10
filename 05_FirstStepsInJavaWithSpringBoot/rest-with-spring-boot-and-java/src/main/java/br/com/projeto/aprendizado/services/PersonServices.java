package br.com.projeto.aprendizado.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.aprendizado.data.vo.v1.PersonVo;
import br.com.projeto.aprendizado.exceptions.ResourceNotFoundExceptionException;
import br.com.projeto.aprendizado.repositories.PersonRepository;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	PersonRepository repository;

	public List<PersonVo> findAll() {

		logger.info("Finding all people!");

		return repository.findAll();
	}

	public PersonVo findById(Long id) {

		logger.info("Finding one person!");

		PersonVo person = new PersonVo();
		person.setFirstName("Matheus");
		person.setLastName("Vieira");
		person.setAddress("Praia Grande / São Paulo - Brasil");
		person.setGender("Male");

		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundExceptionException("No records found for this ID"));
	}

	public PersonVo create(PersonVo person) {

		logger.info("Creating one person!");

		return repository.save(person);
	}

	public PersonVo update(PersonVo person) {

		logger.info("Update one person!");

		var entity = repository.findById(person.getId())
				.orElseThrow(() -> new ResourceNotFoundExceptionException("No records found for this ID"));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());

		return repository.save(person);
	}

	public void delete(Long id) {
		logger.info("deleting one person!");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundExceptionException("No records found for this ID"));

		repository.delete(entity);

	}

}
