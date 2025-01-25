package br.com.projeto.aprendizado.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.projeto.aprendizado.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
