package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

//    新增菜品
    @PostMapping
    public Result<Void> save(@RequestBody CategoryDTO categoryDTO) {
        log.info("categoryDTO: {}", categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }

//    查询菜品以及分类
    @GetMapping("/page")
    public Result<PageResult> page(CategoryPageQueryDTO CategoryPageQueryDTO) {
        log.info("CategoryPageQueryDTO: {}", CategoryPageQueryDTO);
        PageResult pageResult = categoryService.pageFind(CategoryPageQueryDTO);
        return Result.success(pageResult);
    }

//    删除菜品
    @DeleteMapping
    public Result<Void> deleteById(Long id) {
        log.info("id: {}", id);
        categoryService.deleteById(id);
        return Result.success();
    }

//    修改菜品
    @PutMapping
    public Result<Void> update(@RequestBody CategoryDTO categoryDTO) {
        log.info("categoryDTO: {}", categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success();
    }

//    启用禁用套餐
    @PostMapping("status/{status}")
    public Result<Void> startOrStop(@PathVariable("status") Integer status, Long id) {
        log.info("status: {}, Long: {}", status, id);
        categoryService.startOrStop(status, id);
        return Result.success();
    }

//    会被dish调用
    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>> list(Integer type){
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }

}
