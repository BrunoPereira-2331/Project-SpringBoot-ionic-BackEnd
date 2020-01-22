package com.bruno.projectMc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bruno.projectMc.domain.Category;
import com.bruno.projectMc.dto.CategoryDTO;
import com.bruno.projectMc.repositories.CategoryRepository;
import com.bruno.projectMc.services.exceptions.DataIntegrityException;
import com.bruno.projectMc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repo;

	public Category find(Integer id) {
		Optional<Category> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Category.class.getName()));
	}

	public Category Insert(Category obj) {
		obj.setId(null);
		return repo.save(obj);

	}

	public Category update(Category obj) {
		Category newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
		repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos");
		}
	}
	
	public List<Category> findAll() {
		return repo.findAll();
	}
	
	public Page<Category> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Category fromDTO(CategoryDTO objDto) {
		return new Category(objDto.getId(), objDto.getName());
	}
	
	private void updateData(Category newObj, Category obj) {
		newObj.setName(obj.getName());
	}
	
}
