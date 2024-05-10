package com.sky.service;

import com.sky.dto.DishDTO;

public interface DishService {

    /** 新增dish
     *
     * @param dishDTO
     */
    void save(DishDTO dishDTO);
}
