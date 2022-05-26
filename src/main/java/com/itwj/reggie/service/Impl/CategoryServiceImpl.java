package com.itwj.reggie.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itwj.reggie.common_class.CustomException;
import com.itwj.reggie.entity.Category;
import com.itwj.reggie.entity.Dish;
import com.itwj.reggie.entity.Setmeal;
import com.itwj.reggie.mapper.CategoryMapper;
import com.itwj.reggie.service.CategoryService;
import com.itwj.reggie.service.DishService;
import com.itwj.reggie.service.SetmealService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    /*根据id删除分类*/
    @Resource
    private DishService dishService;
    @Resource
    private SetmealService setmealService;

    @Override
    public void remove(Long id) {
        //查询当前分类是狗关联了菜品
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper=new LambdaQueryWrapper<>();
        //添加查询条件
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
       int count1= dishService.count(dishLambdaQueryWrapper);
        if(count1>0){
        throw new CustomException("当前分类关联了菜品不能删除");

        }
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();
        //添加查询条件
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2= setmealService.count(setmealLambdaQueryWrapper);
        if(count2>0){
            throw new CustomException("当前分类关联了套餐不能删除");
        }
        //删除
        this.removeById(id);
    }
}
