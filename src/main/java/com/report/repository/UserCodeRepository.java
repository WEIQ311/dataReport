package com.report.repository;

import com.report.domain.UserCode;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author weiQiang
 * @date 2018/5/21
 */
public interface UserCodeRepository extends JpaRepository<UserCode, String> {
    /**
     * 通过电话号码查询验证码
     *
     * @param telephone
     * @return
     */
    UserCode findByTelephone(String telephone);
}
