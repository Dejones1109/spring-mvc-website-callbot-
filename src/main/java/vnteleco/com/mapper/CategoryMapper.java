package vnteleco.com.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import vnteleco.com.entity.Category;
import vnteleco.com.entity.dto.CategoryDto;
import vnteleco.com.util.FileUtil;


@Component
@PropertySource("classpath:config.properties")
public class CategoryMapper implements EntityDtoMapper<Category, CategoryDto>{
	
	@Value("${url_folder_avatar}")
	private String urlFolderAvatar;
	
	@Value("${default_avatar}")
	private String defaultAvatar;
	
	 @Override
	 public CategoryDto transform(Category category) {
		 CategoryDto categoryDto = new CategoryDto();
		 categoryDto.setId(category.getId());
		 categoryDto.setName(category.getName());
		 		 
		 if (category.getAvatar() !=null && category.getAvatar().length() > 0) {
			 categoryDto.setAvatar(FileUtil.encodedFile(urlFolderAvatar+category.getAvatar()));
		 }else {
			 categoryDto.setAvatar(FileUtil.encodedFile(urlFolderAvatar+defaultAvatar));
		 }
		
		 return categoryDto;
	        
	 }

	@Override
	public Category transformReverse(CategoryDto model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<CategoryDto> transform(Collection<Category> entities) {
		
		List<CategoryDto> listOfCategoryDto = new ArrayList<>();
		for (Category category : entities) {
			CategoryDto categoryDto = transform(category);
			
			listOfCategoryDto.add(categoryDto);
		}
		
		return listOfCategoryDto;
	}

	@Override
	public Collection<Category> transformReverse(Collection<CategoryDto> models) {
		// TODO Auto-generated method stub
		return null;
	}
}
