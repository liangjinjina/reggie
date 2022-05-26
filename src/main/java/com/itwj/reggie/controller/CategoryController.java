package com.itwj.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itwj.reggie.common_class.R;
import com.itwj.reggie.entity.Category;
import com.itwj.reggie.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import javax.annotation.Resource;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;
    /*新增分类*/
    @PostMapping
    public R<String> save(@RequestBody Category category){
        categoryService.save(category);
        System.out.println(category.getId());
        return R.success("新增分类成功");
    }/*分页查询*/
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){

        Page<Category> pageInfo=new Page<>(page,pageSize);
        //分页构造器
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        //分页查询
        categoryService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);

    }
    /*根据id删除*/
    @DeleteMapping
    public R<String> delete(Long ids){
        System.out.println(ids);
        categoryService.remove(ids);
        return R.success("分类信息删除成功");

    }
    @PutMapping
    public R<String> update(@RequestBody  Category category){
        categoryService.updateById(category);
        return R.success("修改信息分类成功");
    }
}
