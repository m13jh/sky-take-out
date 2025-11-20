package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    @AutoFill(OperationType.INSERT)
    void saveDish(Dish dish);

    @Select("select count(1) from dish")
    Long count();

    List<DishVO> page(DishPageQueryDTO dishPageQueryDTO, Integer offset);

    @Select("select status from dish where id = #{id}")
    Integer getStatus(Long id);

    void deleteByIds(List<Long> ids);

    void deleteFlavorByIds(List<Long> dishIds);

    @Select("select * from dish where id = #{id}")
    Dish getDishById(Long id);

    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);
}
