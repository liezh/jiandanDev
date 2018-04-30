package com.liezh.dao;

import com.liezh.domain.entity.Heat;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/4/30.
 */
@Repository
public interface HeatDao {

    List<Heat> queryHeat(@Param("name") String name);

}
