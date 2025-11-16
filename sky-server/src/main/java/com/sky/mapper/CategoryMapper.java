package com.sky.mapper;

import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("select count(1) from category")
    Long count();

    List<Category> pageFind(CategoryPageQueryDTO categoryPageQueryDTO);
}
