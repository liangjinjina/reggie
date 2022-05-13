package com.itwj.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itwj.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.stream.BaseStream;


@Mapper
@Repository
public interface EmployeeMapper extends BaseMapper<Employee> {

}
