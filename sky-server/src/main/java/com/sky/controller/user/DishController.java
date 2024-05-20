package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Api(tags = "客户端菜品")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    //redis-server /usr/local/etc/redis.conf
    @Autowired
    private RedisTemplate redisTemplate;


    @GetMapping("/list")
    @ApiOperation("list 获得菜品信息")
    public Result<List<DishVO>> list(Long categoryId) {

        //构造key
        String key = "dish_list_" + categoryId;
        List<DishVO> dishes = (List<DishVO>) redisTemplate.opsForValue().get(key);
        if (dishes != null && dishes.size() > 0) {
            return Result.success(dishes);
        }


        //如果redis中没有对应缓存
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);
        dishes = dishService.listWithFlavor(dish);

        redisTemplate.opsForValue().set(key, dishes);

        return Result.success(dishes);
    }
}
