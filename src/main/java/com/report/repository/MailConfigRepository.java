package com.report.repository;

import com.report.domain.MailConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 邮件orm
 *
 * @author weiQiang
 */
public interface MailConfigRepository extends JpaRepository<MailConfig, String> {
    /**
     * 通过收件人或主题分页查询已发送邮件
     *
     * @param setTo
     * @param subject
     * @param pageable
     * @return
     */
    Page<MailConfig> findBySetToIsLikeAndSubjectIsLikeOrderByCreateTimeDesc(String setTo, String subject, Pageable pageable);
}
