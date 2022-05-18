package com.itwj.reggie.Dto;

import com.reggie.reggie.entity.Setmeal;
import com.reggie.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
