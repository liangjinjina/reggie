package com.itwj.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itwj.reggie.common_class.R;
import com.itwj.reggie.entity.Category;
import com.itwj.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import javax.annotation.Resource;
import java.util.List;
//
//@RestController
//@RequestMapping("/category")
//public class CategoryController {
//    @Resource
//    private CategoryService categoryService;
//    /*新增分类*/
//    @PostMapping
//    public R<String> save(@RequestBody Category category){
//        categoryService.save(category);
//        System.out.println(category.getId());
//        return R.success("新增分类成功");
//    }/*分页查询*/
//    @GetMapping("/page")
//    public R<Page> page(int page, int pageSize){
//
//        Page<Category> pageInfo=new Page<>(page,pageSize);
//        //分页构造器
//        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();//条件构造器
//        queryWrapper.orderByAsc(Category::getSort);//添加排序条件，根据sort进行排序
//        //分页查询
//        categoryService.page(pageInfo,queryWrapper);
//
//        return R.success(pageInfo);
//
//    }
//    /*根据id删除*/
//    @DeleteMapping
//    public R<String> delete(Long ids){
//        System.out.println(ids);
//        categoryService.remove(ids);
//        return R.success("分类信息删除成功");
//
//    }
//    @PutMapping
//    public R<String> update(@RequestBody  Category category){
//        categoryService.updateById(category);
//        return R.success("修改信息分类成功");
//    }
//
//    /**
//     * 根据条件查询分类数据
//     * @param category
//     * @return
//     */
//    @GetMapping("/list")
//    public R<List<Category>> list(Category category){
//
//        //条件构造器
//        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
//        //添加条件
//        queryWrapper.eq(category.getType()!=null,Category::getType,category.getType());
//        //添加排序条件
//        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
//
//        List<Category> list = categoryService.list(queryWrapper);
//
//        return R.success(list);
//    }
//}


/**
 * 分类管理
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("category:{}",category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        //分页构造器
        Page<Category> pageInfo = new Page<>(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加排序条件，根据sort进行排序
        queryWrapper.orderByAsc(Category::getSort);

        //分页查询
        categoryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据id删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long ids){
        log.info("删除分类，id为：{}",ids);

        categoryService.remove(ids);
        return R.success("分类信息删除成功");
    }

    /**
     * 根据id修改分类信息
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("修改分类信息：{}",category);

        categoryService.updateById(category);

        return R.success("修改分类信息成功");
    }

    /**
     * 根据条件查询分类数据
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        System.out.println("get500");
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(category.getType() != null,Category::getType,category.getType());
        //添加排序条件
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
    }
}
