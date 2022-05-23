package com.itwj.reggie.controller;

import com.itwj.reggie.common_class.R;
import com.itwj.reggie.entity.Category;
import com.itwj.reggie.service.CategoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
        return R.success("新增分类成功");
    }
}
