package com.sky.service.impl;

import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public PageResult pageFind(CategoryPageQueryDTO categoryPageQueryDTO) {
        Long cnt = categoryMapper.count();
        System.out.println("cnt: " + cnt);
        Integer offset = (categoryPageQueryDTO.getPage() - 1) * categoryPageQueryDTO.getPageSize();
        List<Category> list = categoryMapper.pageFind(categoryPageQueryDTO, offset);
        return new PageResult(cnt, list);
    }

    @Override
    public void save(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setStatus(StatusConstant.ENABLE);
//        category.setCreateTime(LocalDateTime.now());
//        category.setUpdateTime(LocalDateTime.now());
//        Long currentId = BaseContext.getCurrentId();
//        category.setCreateUser(currentId);
//        category.setUpdateUser(currentId);
        categoryMapper.save(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryMapper.deleteById(id);
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
//        使用了切点的方式, 不用手动添加
//        Long currentId = BaseContext.getCurrentId();
//        category.setUpdateTime(LocalDateTime.now());
//        category.setUpdateUser(currentId);
        categoryMapper.update(category);
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        Category category = new Category();
        category.setStatus(status);
        category.setId(id);
        categoryMapper.update(category);
    }

    @Override
    public List<Category> list(Integer type) {
        return categoryMapper.list(type);
    }

}
