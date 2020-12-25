package vnteleco.com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import vnteleco.com.entity.Category;
import vnteleco.com.entity.dto.CategoryDto;
import vnteleco.com.mapper.CategoryMapper;
import vnteleco.com.service.CategoryService;

@Controller
@Transactional
@EnableWebMvc
public class IndexController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CategoryMapper categoryMapper;
			
	@InitBinder
	public void myInitBinder(WebDataBinder dataBinder) {
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		System.out.println("Target=" + target);
	}

	// GET: Show Index Page
	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String cate(Model model, HttpServletRequest request) {
		
		List<CategoryDto> listOfCategoryDto = (List<CategoryDto>) categoryMapper.transform(categoryService.findAllCategory());
		
		model.addAttribute("listOfCategory", listOfCategoryDto);					
		return "index";
	}



}
