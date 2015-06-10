package net.learntechnology.genericcrud.services;

import net.learntechnology.genericcrud.domain.SearchCriteria;
import net.learntechnology.genericcrud.mapper.GenericCrudMapper;

import java.util.List;

public abstract class GenericCrudService<T, PK> {

	protected GenericCrudMapper mapper;

	public GenericCrudService(GenericCrudMapper mapper) {
		this.mapper = mapper;
	}

	public T fetch(PK id) {
		return (T)mapper.fetchById(id);
	}

	public List<T> fetchAll() {
		return mapper.fetch(null);
	}

	public List<T> fetch(SearchCriteria searchCriteria) {
		return mapper.fetch(searchCriteria);
	}

	public void insert(T o) {
		mapper.insert(o);
	}

	public void update(T o) {
		mapper.update(o);
	}

	public void delete(T o) {
		mapper.delete(o);
	}

}
