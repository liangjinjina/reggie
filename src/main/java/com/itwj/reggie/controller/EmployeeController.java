package com.itwj.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itwj.reggie.Encrypt.Encrypt;
import com.itwj.reggie.common_class.R;
import com.itwj.reggie.entity.Employee;
import com.itwj.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Base64;


@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired(required = false)//忽略错误
    private EmployeeService employeeService;


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
        /*String password=Encrypt.desEncrypt(employee.getPassword(),key,iv);
        byte[] pwd= Base64.getDecoder().decode(employee.getPassword());
        String p2=new String(pwd);
        System.out.println(p2);*/
        String password= DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());//md5加密

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
    /*------------------------新增员工--------------------------*/

    @PostMapping  //backend/page/member/list.html中 addMemberHandle
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        //log.info("新增员工，员工信息：：{}",employee.toString());

        //统一给个设置初始密码12345，后面员工会自己更改密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        //通过登录这个系统的用户的id(存放在session中主键是“emplyee"，登录代码中有)
        Long empId=(Long)request.getSession().getAttribute("employee");//获得到的value是Object所以要转long

        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);


        //利用employService保存新增数据到数据库
        employeeService.save(employee);


        return R.success("新增员工成功");
    }//18-20



    /*
    -------------------------------员工信息分页查询--------------------------------

    @param page//页数  默认1  list.html中可以改
    @param pageSize//条数  默认10
    @param name//搜索条目
    @return
     */

    @GetMapping("/page") //backend/page/member/list.html 中init()方法调用了member.js中的getMemberList方法，url: '/employee/page'
    public R<Page> pagemethod(int page, int pageSize, String name){//因为是get方式所以要直接传参而非json
        //log.info("page={},pageSize={},name={}",page,pageSize,name);

        //构造分页构造器
        Page pageInfo=new Page(page,pageSize);//Page是mp封装的类可以看看它的源码

        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper();

        //添加过滤条件
        queryWrapper.like(!StringUtils.isEmpty(name),Employee::getName,name);//参数1：name不为空 参数2：数据库的getName和name有相同字段

        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        //执行查询
        employeeService.page(pageInfo,queryWrapper);


        return R.success(pageInfo);

    }//24-29


/*
禁用员工：
只用admin管理员有禁用启用员工权限，其他员工不能启用禁用
list.html中: 86 @click="statusHandle(scope.row)"<!--调用方法statusHandle(scope.row)-->180
             87 v-if="user === 'admin'" <!--判断是否是管理员-->

statusHandle(scope.row)中调用member.js中方法enableOrDisableEmployee(参数id status json) url: '/employee',


 */

    /*
    根据id修改员工信息
    @param employee (将id status 映射为employee对象)
    @return
     */
    /*--------------------------更新员工-----------------------------------*/
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee)
    {
        //log.info(employee.toString());

        Long empId=(Long)request.getSession().getAttribute("employee");
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empId);
//-------------------前面都是对目前登陆的管理员admin的操作

        employeeService.updateById(employee);//这才是更新操作
        return R.success("员工信息更新成功");
    }//到34

/*扩展问题{"code":1,"msg":null,"data":{"records":[{"id":1524554224029974500,"username":"lili","name":"李员工","password":"e10adc3949ba59abbe56e057f20f883e","phone":"15555555555","sex":"1","idNumber":"510902200004170589","status":1,"createTime":[2022,5,12,8,57,6],"updateTime":[2022,5,18,9,44,12],"createUser":1,"updateUser":1},{"id":15245539153250000,"username":"zhang","name":"张三","password":"e10adc3949ba59abbe56e057f20f883e","phone":"17777777666","sex":"1","idNumber":"510902200004170583","status":1,"createTime":[2022,5,12,8,55,52],"updateTime":[2022,5,18,9,42,23],"createUser":1,"updateUser":1},{"id":15242353408400000,"username":"wujing","name":"吴员工","password":"e10adc3949ba59abbe56e057f20f883e","phone":"13882515939","sex":"1","idNumber":"510902200004170583","status":0,"createTime":[2022,5,11,11,49,58],"updateTime":[2022,5,17,11,51,7],"createUser":1,"updateUser":1},{"id":15245540355379500,"username":"zhou","name":"周员工","password":"e10adc3949ba59abbe56e057f20f883e","phone":"15555555555","sex":"1","idNumber":"510902200004170583","status":1,"createTime":[2022,5,12,8,56,21],"updateTime":[2022,5,12,8,56,21],"createUser":1,"updateUser":1},{"id":15245537924067222,"username":"cai","name":"蔡员工","password":"e10adc3949ba59abbe56e057f20f883e","phone":"16666666666","sex":"1","idNumber":"510902200004170586","status":1,"createTime":[2022,5,12,8,55,23],"updateTime":[2022,5,12,8,55,23],"createUser":1,"updateUser":1}],"total":8,"size":5


   /* --------------------------修改编辑员工-------------------------------*/

/*
step1:回显数据：点击‘编辑’（编辑会携带id到add.html中），add.html中async init () 方法通过访问后台`/employee/${id}`
后台将当前需要编辑的id的员工信息(根据id查询员工信息)，回显到前端（这样一点击‘编辑’之后的页面就有此id员工的信息）
 */

    /*
    step2:点击保存后 向发送ajax(以json形式)请求....   保存员工信息
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        //log.info("根据id查询员工信息");
        Employee employee=employeeService.getById(id);
        System.out.println("*********77777777777777777777"+R.success(employee));
        return R.success(employee);//返回的是对象为什么前台收到的是json？

    }//36-38

}

//page 0-11