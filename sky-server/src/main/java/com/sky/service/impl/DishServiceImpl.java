package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void save(DishDTO dishDTO) {
        Dish dish = new Dish();
//        使用自定义AutoFill注解, 不用在这里添加公共字段
        BeanUtils.copyProperties(dishDTO, dish);
//        注意这里要主键回显
        dishMapper.saveDish(dish);
        List<DishFlavor> flavors = dishDTO.getFlavors();
        Long dishId = dish.getId();
        if(flavors != null && !flavors.isEmpty()) {
//            设置dish主键, dishFlavor外键
            flavors.forEach(dishFlavor -> dishFlavor.setDishId(dishId));
            dishFlavorMapper.saveDishFlavor(flavors);
        }
    }

    @Override
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {
        Long cnt = dishMapper.count();
//        计算一页的数量
        Integer offset = (dishPageQueryDTO.getPage() - 1) * dishPageQueryDTO.getPageSize();
        List<DishVO> page = dishMapper.page(dishPageQueryDTO, offset);
        return new PageResult(cnt, page);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void deleteByIds(List<Long> ids) {
//        起售中的菜品不可删除
        for(Long id : ids) {
            Integer status = dishMapper.getStatus(id);
            if(status.equals(StatusConstant.ENABLE)) {
//                售卖中, 抛出异常
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
//        套餐中的菜品不可删除
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if(setmealIds != null && !setmealIds.isEmpty()) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }
//        循环删除菜品和对应口味
        dishMapper.deleteByIds(ids);
        dishMapper.deleteFlavorByIds(ids);
    }

    @Override
    public DishVO getDishById(Long id) {
//        查菜品
        Dish dish = dishMapper.getDishById(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
//        查口味
        List<DishFlavor> flavor = dishFlavorMapper.getFlavorById(id);
        dishVO.setFlavors(flavor);
        return dishVO;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void update(DishDTO dishDTO) {
        Dish dish = new Dish();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        BeanUtils.copyProperties(dishDTO, dish);
//        更新菜品
        dishMapper.update(dish);
//        更新口味
//        先删除全部口味, 再添加口味
        List<Long> list = new ArrayList<>();
        list.add(dishDTO.getId());
        dishMapper.deleteFlavorByIds(list);
        if(flavors != null && !flavors.isEmpty()) {
            flavors.forEach(dishFlavor -> dishFlavor.setDishId(dishDTO.getId()));
            dishFlavorMapper.saveDishFlavor(flavors);
        }
    }
}
