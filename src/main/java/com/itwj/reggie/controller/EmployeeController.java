package com.itwj.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itwj.reggie.common_class.R;
import com.itwj.reggie.entity.Employee;
import com.itwj.reggie.service.EmployeeService;
import com.reggie.reggie.service.DishService;
import com.reggie.reggie.service.impl.DishServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired(required = false)//忽略错误
    private EmployeeService employeeService;

    @Autowired(required = false)
    private DishService dishService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {//前台传过来的username、password（要跟列名一样）映射成Employ实体

/* 1、将页面提交的密码password进行md5加密处理
   2、根据页面提交的用户名username查询数据库
   3、如果没有查询到则返回登录失败结果
   4、密码比对，如果不一致则返回登录失败结果
   5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
   6、登录成功，将员工id存入5ession并返回登录成功结果
*/
//1、将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
         password= DigestUtils.md5DigestAsHex(password.getBytes());//md5加密知识

//2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();//mybits-p 查询对象
        queryWrapper.eq(Employee::getUsername, employee.getUsername());//mybits-p 查询条件

        Employee emp = employeeService.getOne(queryWrapper);// mybits-p 用getone因为Employee中usename有唯一约束 封装为Emloyee对象 emp
        emp.toString();
        System.out.println(emp.toString());

//3、如果没有查询到则返回登录失败结果
        if (emp == null) {
            return R.error("无此用户名，登录失败");
        }


//4、密码比对，如果不一致则返回登录失败结果
        if (!emp.getPassword().equals(password)) {
            return R.error("密码错误，登录失败");
        }


//        5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果

        if (emp.getStatus() == 0) {//0:禁用 1：不禁用
            return R.error("用户已禁用");
        }

            request.getSession().setAttribute("employee", emp.getId());

            return R.success(emp);

    }
}

//page 0-11