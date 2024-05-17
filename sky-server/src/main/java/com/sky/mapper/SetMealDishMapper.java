package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetMealDishMapper {

    /**根据菜品id 查询套餐id
     *
     */
    List<Long> getSetMealIdsByDishIds(List<Long> dishIds);

}
