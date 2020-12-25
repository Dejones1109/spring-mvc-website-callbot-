package vnteleco.com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vnteleco.com.entity.Callbot;
import vnteleco.com.entity.Category;
import vnteleco.com.responsity.CallbotRepository;
import vnteleco.com.responsity.CategoryRepository;
import vnteleco.com.service.CallbotService;
import vnteleco.com.service.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public List<Category> findAllCategory() {
		// TODO Auto-generated method stub
		return categoryRepository.findAllCategory();
	}
	


}
