package com.liezh.dao;

import com.liezh.domain.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/2/14.
 */
@Repository
public interface RoleDao {

    /**
     * 列出区域列表
     *
     * @return areaList
     */
    List<Role> queryRole();

}
