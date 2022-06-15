package com.itwj.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itwj.reggie.common_class.R;
import com.itwj.reggie.entity.Category;
import com.itwj.reggie.entity.Employee;
import com.itwj.reggie.entity.Orders;
import com.itwj.reggie.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

/**
 * 订单
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("订单数据：{}",orders);
        orderService.submit(orders);
        return R.success("下单成功");
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
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        //添加排序条件，根据sort进行排序
        queryWrapper.orderByAsc(Orders::getOrderTime);

        //分页查询
        orderService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }
    /**
     *订单查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/userPage")
    public R<Page> userpage(int page,int pageSize){
        //分页构造器
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        //添加排序条件，根据sort进行排序
        queryWrapper.orderByAsc(Orders::getOrderTime);

        //分页查询
        orderService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    @PutMapping
    public R<String> Orderfinish(@RequestBody  Orders orders){
        System.out.println(orders.getId());
        Orders order=orderService.getById(orders.getId());
        order.setStatus(order.getStatus()+1);
        orderService.updateById(order);
        return R.success("已派送");
    }

}