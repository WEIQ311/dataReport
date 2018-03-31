package com.report.repository;

import com.report.domain.MailConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 邮件orm
 *
 * @author weiQiang
 */
public interface MailConfigRepository extends JpaRepository<MailConfig, String> {
}
