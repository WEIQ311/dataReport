package com.report.service.impl;

import com.report.domain.User;
import com.report.service.UserService;
import com.report.vo.ResultEntity;
import org.springframework.stereotype.Service;

/**
 * @author weiQiang
 * @date 2018/5/21
 */
@Service(value = "UserService")
public class UserServiceImpl implements UserService {
    @Override
    public User findUserByName(String userName) {
        return null;
    }

    @Override
    public ResultEntity findById(Long userId) {
        return null;
    }

    /**
     * 根据电话号码寻找用户
     *
     * @param telephone
     * @return
     */
    @Override
    public User findUserByTelephone(String telephone) {
        return null;
    }

    /**
     * 通过手机号注册用户
     *
     * @param telehone
     * @return
     */
    @Override
    public User addUserByPhone(String telehone) {
        return null;
    }

    /**
     * 修改指定属性值
     *
     * @param profile
     * @param value
     * @return
     */
    @Override
    public ResultEntity modifyUserProfile(String profile, String value) {
        return null;
    }
}
