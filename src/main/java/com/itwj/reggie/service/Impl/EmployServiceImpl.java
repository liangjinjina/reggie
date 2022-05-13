package com.itwj.reggie.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itwj.reggie.entity.Employee;
import com.itwj.reggie.mapper.EmployeeMapper;
import com.itwj.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService {
}
