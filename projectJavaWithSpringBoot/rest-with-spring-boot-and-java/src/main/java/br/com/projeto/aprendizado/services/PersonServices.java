package br.com.projeto.aprendizado.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.projeto.aprendizado.converter.DozerConverter;
import br.com.projeto.aprendizado.data.vo.v1.PersonVo;
import br.com.projeto.aprendizado.data.vo.v2.PersonVoV2;
import br.com.projeto.aprendizado.exceptions.ResourceNotFoundExceptionException;
import br.com.projeto.aprendizado.mapper.DozerMapper;
import br.com.projeto.aprendizado.mapper.custom.PersonMapper;
import br.com.projeto.aprendizado.model.Person;
import br.com.projeto.aprendizado.repositories.PersonRepository;
import jakarta.transaction.Transactional;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	PersonRepository repository;

	@Autowired
	PersonMapper mapper;

	public Page<PersonVo> findAll(Pageable pageable) {
		var page = repository.findAll(pageable);
		return page.map(this::convertToPersonVo);
	}
	
	public Page<PersonVo> findPersonsByName(String firstName, Pageable pageable) {
		var page = repository.findPersonsByName(firstName, pageable);
		return page.map(this::convertToPersonVo);
	}

	public PersonVo findById(Long id) {

		logger.info("Finding one person!");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundExceptionException("No records found for this ID"));

		return DozerMapper.parseObject(entity, PersonVo.class);
	}

	public PersonVo create(PersonVo person) {

		logger.info("Creating one person!");
		var entity = DozerMapper.parseObject(person, Person.class);
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVo.class);
		return vo;
	}

	public PersonVoV2 createV2(PersonVoV2 person) {

		logger.info("Creating one person with v2!");
		var entity = mapper.convertVoToEntity(person);
		var vo = mapper.convertEntityToVo(repository.save(entity));
		return vo;
	}

	public PersonVo update(PersonVo person) {

		logger.info("Update one person!");

		var entity = repository.findById(person.getId())
				.orElseThrow(() -> new ResourceNotFoundExceptionException("No records found for this ID"));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());

		var vo = DozerMapper.parseObject(repository.save(entity), PersonVo.class);
		return vo;
	}
	
	@Transactional
	public PersonVo disablePerson(Long id) {

		logger.info("Disabling one person!");
		
		repository.disablePerson(id);

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundExceptionException("No records found for this ID"));

		return DozerMapper.parseObject(entity, PersonVo.class);
	}

	public void delete(Long id) {
		logger.info("deleting one person!");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundExceptionException("No records found for this ID"));

		repository.delete(entity);

	}
	
	private PersonVo convertToPersonVo(Person entity) {
		return DozerConverter.parseObject(entity, PersonVo.class);
	}

}
