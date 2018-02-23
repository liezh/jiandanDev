package com.liezh.dao;

import com.liezh.domain.entity.Role;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by Administrator on 2018/2/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按方法名大小升序执行
public class RoleDaoTest {

    @Autowired
    private RoleDao roleDao;

    @Test
    public void queryRole() throws Exception {
        List<Role> roles = roleDao.queryRole();
        System.out.println(roles);
    }

}