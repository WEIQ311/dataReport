package com.report.service;

import com.report.domain.AddressBook;
import com.report.vo.ResultEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 通讯录Service
 *
 * @author weiQiang
 */
public interface AddressBookService {

    /**
     * 增加通讯录
     *
     * @param addressBook
     * @return
     */
    ResultEntity insert(AddressBook addressBook);

    /**
     * 批量增加通讯录
     *
     * @param addressBooks
     * @return
     */
    ResultEntity insert(List<AddressBook> addressBooks);

    /**
     * 批量或单个删除通讯录
     *
     * @param bookIds
     * @return
     */
    ResultEntity delete(String... bookIds);

    /**
     * 修改通讯录
     *
     * @param addressBook
     * @return
     */
    ResultEntity update(AddressBook addressBook);

    /**
     * 批量修改通讯录
     *
     * @param addressBooks
     * @return
     */
    ResultEntity update(List<AddressBook> addressBooks);

    /**
     * 查询通讯录
     *
     * @param addressBook
     * @param pageable
     * @return
     */
    ResultEntity list(AddressBook addressBook, Pageable pageable);

    /**
     * 查询通讯录
     *
     * @param addressBook
     * @return
     */
    List<AddressBook> list(AddressBook addressBook);
}
