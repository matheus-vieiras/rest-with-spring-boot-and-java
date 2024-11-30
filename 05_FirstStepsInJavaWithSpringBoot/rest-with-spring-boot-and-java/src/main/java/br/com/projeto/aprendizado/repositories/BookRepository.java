package br.com.projeto.aprendizado.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.projeto.aprendizado.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
