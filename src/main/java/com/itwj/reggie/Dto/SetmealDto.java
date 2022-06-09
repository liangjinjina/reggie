package com.itwj.reggie.Dto;

import com.itwj.reggie.entity.Setmeal;
import com.itwj.reggie.entity.SetmealDish;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
