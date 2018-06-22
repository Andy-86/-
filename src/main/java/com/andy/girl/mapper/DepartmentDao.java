package com.andy.girl.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DepartmentDao {
    int insert(@Param("pojo") Department pojo);

    int insertSelective(@Param("pojo") Department pojo);

    int insertList(@Param("pojos") List<Department> pojo);

    int update(@Param("pojo") Department pojo);

    List<Department> findall();
}
