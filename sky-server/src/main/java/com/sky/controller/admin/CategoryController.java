package com.sky.controller.admin;

import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

//    查询菜品以及分类
    @GetMapping("/page")
    public Result<PageResult> page(CategoryPageQueryDTO CategoryPageQueryDTO) {
        log.info("CategoryPageQueryDTO: {}", CategoryPageQueryDTO);
        PageResult pageResult = categoryService.pageFind(CategoryPageQueryDTO);
        return Result.success(pageResult);
    }

}
