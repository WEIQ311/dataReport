package com.report.repository;

import com.report.domain.AddressBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author weiQiang
 */
public interface AddressBookRepository extends JpaRepository<AddressBook, String> {
    /**
     * 通过ReportTemplate信息查询
     *
     * @param name
     * @param mail
     * @param pageable
     * @return
     */
    Page<AddressBook> findByNameLikeAndMailIsLikeOrderByUpdateTimeDesc(String name, String mail, Pageable pageable);
}
