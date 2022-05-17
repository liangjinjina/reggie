package com.reggie.reggie.controller;


import com.reggie.reggie.service.SetmealDishService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * <p>
 * 套餐菜品关系 前端控制器
 * </p>
 *
 * @author fanshuai
 * @since 2022-05-16
 */
@Controller
@RequestMapping("/reggie/setmealDish")
public class SetmealDishController {
   @Resource
    private SetmealDishService setmealDishService;
}

