package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("dishPageQueryDTO: {}", dishPageQueryDTO);
        PageResult pageResult = dishService.page(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    public Result<Void> save(@RequestBody DishDTO dishDTO) {
        log.info("dishDTO: {}", dishDTO);
        dishService.save(dishDTO);
        return Result.success();
    }

//    删除菜品
    @DeleteMapping
    public Result<Void> deleteByIds(@RequestParam List<Long> ids) {
        log.info("ids: {}", ids);
        dishService.deleteByIds(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<DishVO> getDishById(@PathVariable Long id) {
        log.info("id: {}", id);
        DishVO dishVo = dishService.getDishById(id);
        return Result.success(dishVo);
    }

    @PutMapping
    public Result<Void> update(@RequestBody DishDTO dishDTO) {
        log.info("dishDTO: {}", dishDTO);
        dishService.update(dishDTO);
        return Result.success();
    }

}
