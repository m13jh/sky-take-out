package com.sky.service.impl;

import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public PageResult pageFind(CategoryPageQueryDTO categoryPageQueryDTO) {
        Long cnt = categoryMapper.count();
        System.out.println("cnt: " + cnt);
        List<Category> list = categoryMapper.pageFind(categoryPageQueryDTO);
        return new PageResult(cnt, list);
    }

}
