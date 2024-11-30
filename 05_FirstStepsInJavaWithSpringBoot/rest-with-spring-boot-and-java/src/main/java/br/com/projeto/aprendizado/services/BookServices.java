package br.com.projeto.aprendizado.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.aprendizado.data.vo.v1.BookVo;
import br.com.projeto.aprendizado.exceptions.ResourceNotFoundExceptionException;
import br.com.projeto.aprendizado.mapper.DozerMapper;
import br.com.projeto.aprendizado.model.Book;
import br.com.projeto.aprendizado.repositories.BookRepository;

@Service
public class BookServices {

	private Logger logger = Logger.getLogger(BookServices.class.getName());

	@Autowired
	BookRepository repository;

	public List<BookVo> findAll() {

		logger.info("Finding all book!");

		return DozerMapper.parseListObjects(repository.findAll(), BookVo.class);
	}

	public BookVo findById(Long id) {

		logger.info("Finding one book!");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundExceptionException("No records found for this ID"));

		return DozerMapper.parseObject(entity, BookVo.class);
	}

	public BookVo create(BookVo book) {

		logger.info("Creating one book!");
		var entity = DozerMapper.parseObject(book, Book.class);
		var vo = DozerMapper.parseObject(repository.save(entity), BookVo.class);
		return vo;
	}

	public BookVo update(BookVo book) {

		logger.info("Update one book!");

		var entity = repository.findById(book.getId())
				.orElseThrow(() -> new ResourceNotFoundExceptionException("No records found for this ID"));

		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());

		var vo = DozerMapper.parseObject(repository.save(entity), BookVo.class);
		return vo;
	}

	public void delete(Long id) {
		logger.info("deleting one book!");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundExceptionException("No records found for this ID"));

		repository.delete(entity);

	}

}
