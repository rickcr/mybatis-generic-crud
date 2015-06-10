package net.learntechnology.genericcrud.mapper;

import net.learntechnology.genericcrud.domain.SearchCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GenericCrudMapper<T, PK> {
	Object fetchById(PK id);
	List<T> fetch(@Param("searchCriteria") SearchCriteria searchCriteria);
	void insert(T o);
	void update(T o);
	void delete(T o);
}
