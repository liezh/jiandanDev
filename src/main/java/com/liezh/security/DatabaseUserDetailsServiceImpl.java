package com.liezh.security;


import com.liezh.dao.UserDao;
import com.liezh.domain.dto.user.UserInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/4/28.
 *  从数据库中获取用角色信息
 */
@Service
public class DatabaseUserDetailsServiceImpl implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(DatabaseUserDetailsServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String accountOrMobile) throws UsernameNotFoundException {
        UserInfoDto userOrig = userDao.queryUserByAccountOrMobile(accountOrMobile);
        if(userOrig == null) {
            logger.error("登录用户不存在！ account or mobile: {}", accountOrMobile);
            throw new UsernameNotFoundException("该用户不存在");
        }

        AuthUser authUser = new AuthUser(userOrig.getId(), userOrig.getAccount(), userOrig.getPassword(), userOrig.getSalt(), userOrig.getRoles());
        authUser.setCreateTime(userOrig.getCreateTime());
        authUser.setUpdateTime(userOrig.getUpdateTime());
        return authUser;
//        Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        for(Role r : userOrig.getRoles()){
//            //角色的开头必须是 ROLE_ 开头的不然security是不承认的，就会出现
//            // Access is denied (user is not anonymous); delegating to AccessDeniedHandler。
//            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+r.getName()));
//        }
//        return new org.springframework.security.core.userdetails.User(userOrig.getAccount(), userOrig.getPassword(), grantedAuthorities);
    }
}
