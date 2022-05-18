package com.itwj.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itwj.reggie.Encrypt.Encrypt;
import com.itwj.reggie.common_class.R;
import com.itwj.reggie.entity.Employee;
import com.itwj.reggie.service.EmployeeService;
import com.reggie.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired(required = false)//忽略错误
    private EmployeeService employeeService;

    @Autowired(required = false)
    private DishService dishService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, HttpServletResponse response, @RequestBody Employee employee) throws Exception {//前台传过来的username、password（要跟列名一样）映射成Employ实体

/* 1、将页面提交的密码password进行md5加密处理
   2、根据页面提交的用户名username查询数据库
   3、如果没有查询到则返回登录失败结果
   4、密码比对，如果不一致则返回登录失败结果
   5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
   6、登录成功，将员工id存入5ession并返回登录成功结果
*/
//1、将页面提交的密码password进行md5加密处理
        String key="1234567890123456";
        String iv ="1234567890123456";
        // String password=Encrypt.desEncrypt(employee.getPassword(),key,iv);
        String password= DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());//md5加密
        System.out.println(password);
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

            Cookie c = new Cookie("JSESSIONID",request.getSession().getId());//设置相同名的session
            c.setMaxAge(60*60);//设置他的存活时间为1小时
            response.addCookie(c);//防止用户不小心关闭浏览器，消除了cookie，避免用户短时间内再次进行登录

            return R.success(emp);

    }

    @PostMapping("/logout")
    public R<String > loginout(HttpServletRequest request)
    {
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

}

//page 0-11