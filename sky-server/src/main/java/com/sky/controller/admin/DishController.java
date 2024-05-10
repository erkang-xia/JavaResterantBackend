package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.JsonProjectingMethodInterceptorFactory;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/dish")
@RestController
@Api("菜品相关")
@Slf4j
public class DishController {

    @Autowired
    DishService dishService;

    @PostMapping
    @ApiOperation("upload 新增菜品")
    public Result upload(@RequestBody DishDTO dishDTO) {
        dishService.save(dishDTO);
        return Result.success();

    }
}
