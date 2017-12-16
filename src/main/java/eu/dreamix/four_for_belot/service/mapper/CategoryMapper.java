package eu.dreamix.four_for_belot.service.mapper;

import eu.dreamix.four_for_belot.domain.*;
import eu.dreamix.four_for_belot.service.dto.CategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Category and its DTO CategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {

    @Mapping(source = "parent.id", target = "parentId")
    CategoryDTO toDto(Category category); 

    @Mapping(target = "children", ignore = true)
    @Mapping(source = "parentId", target = "parent")
    @Mapping(target = "interestedProfiles", ignore = true)
    @Mapping(target = "gamesWithThisCategories", ignore = true)
    Category toEntity(CategoryDTO categoryDTO);

    default Category fromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}
