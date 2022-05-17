package com.reggie.reggie.mapper;

import com.reggie.reggie.entity.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 菜品管理 Mapper 接口
 * </p>
 *
 * @author fanshuai
 * @since 2022-05-12
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {

}
