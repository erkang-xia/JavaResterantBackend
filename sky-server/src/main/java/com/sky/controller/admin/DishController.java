package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.JsonProjectingMethodInterceptorFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @GetMapping("/page")
    @ApiOperation("page 菜品分页查询")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询:{}", dishPageQueryDTO);
        return Result.success(dishService.pageQuery(dishPageQueryDTO));
    }

    @DeleteMapping
    @ApiOperation("delete 批量删除菜品")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("批量删除数据: {}",ids);
        dishService.deleteBatch(ids);

        return Result.success();

    }

    @GetMapping("/{id}")
    @ApiOperation("get 查询")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("查询菜品信息:{}",id);

       DishVO dishVO =  dishService.getByIdWithFlavor(id);
       return Result.success(dishVO);

    }

    @PutMapping
    @ApiOperation("update 修改菜品 ")
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品:{}",dishDTO);
        dishService.updateWithFlavor(dishDTO);
        return Result.success();

    }

    @PostMapping("/status/{status}")
    @ApiOperation("startOrStop 改变菜品售卖状态")
    public Result startOrStop(@PathVariable Integer status,Long id) {
        dishService.startOrStop(status,id);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("list 根据菜品分类查找菜品")
    public Result<List<Dish>> list(Long categoryId) {
        List<Dish> dishes = dishService.list(categoryId);
        return Result.success(dishes);

    }



}
