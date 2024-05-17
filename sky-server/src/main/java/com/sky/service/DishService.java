package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    /** 新增dish
     *
     * @param dishDTO
     */
    void save(DishDTO dishDTO);

    /** 菜品分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**批量删除
     *
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**查询
     *
     * @param id
     * @return
     */
    DishVO getByIdWithFlavor(Long id);

    /**修改菜品信息
     *
     * @param dishDTO
     */
    void updateWithFlavor(DishDTO dishDTO);

    /**修改菜品状态
     *
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);
}
