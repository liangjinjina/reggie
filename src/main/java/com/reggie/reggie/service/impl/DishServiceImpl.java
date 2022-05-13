package com.reggie.reggie.service.impl;

import com.reggie.reggie.entity.Dish;
import com.reggie.reggie.mapper.DishMapper;
import com.reggie.reggie.service.DishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜品管理 服务实现类
 * </p>
 *
 * @author fanshuai
 * @since 2022-05-12
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

}
