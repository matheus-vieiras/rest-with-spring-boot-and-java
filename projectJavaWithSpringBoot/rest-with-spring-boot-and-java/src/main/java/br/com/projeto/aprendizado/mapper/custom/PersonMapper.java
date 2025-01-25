package br.com.projeto.aprendizado.mapper.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.projeto.aprendizado.data.vo.v2.PersonVoV2;
import br.com.projeto.aprendizado.model.Person;

@Service
public class PersonMapper {

	public PersonVoV2 convertEntityToVo(Person person) {
		PersonVoV2 vo = new PersonVoV2();
		vo.setId(person.getId());
		vo.setAddress(person.getAddress());
		vo.setBirthDay(new Date());
		vo.setFirstName(person.getFirstName());
		vo.setLastName(person.getLastName());
		vo.setGender(person.getGender());
		return vo;
	}

	public Person convertVoToEntity(PersonVoV2 person) {
		Person entity = new Person();
		entity.setId(person.getId());
		entity.setAddress(person.getAddress());
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setGender(person.getGender());
		return entity;
	}

}
