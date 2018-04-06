package com.report.service.impl;

import com.report.domain.AddressBook;
import com.report.enums.GlobalEnum;
import com.report.repository.AddressBookRepository;
import com.report.service.AddressBookService;
import com.report.util.ResultUtil;
import com.report.vo.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author weiQiang
 */
@Service(value = "addressBookService")
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookRepository addressBookRepository;

    /**
     * 增加通讯录
     *
     * @param addressBook
     * @return
     */
    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public ResultEntity insert(AddressBook addressBook) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        addressBook.setCreateTime(timestamp);
        addressBook.setUpdateTime(timestamp);
        addressBook.setUpdateCount(0);
        addressBook = addressBookRepository.save(addressBook);
        return ResultUtil.success(GlobalEnum.INSERT_SUCCESS, addressBook);
    }

    /**
     * 批量增加通讯录
     *
     * @param addressBooks
     * @return
     */
    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public ResultEntity insert(List<AddressBook> addressBooks) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        addressBooks.forEach(addressBook -> {
            addressBook.setCreateTime(timestamp);
            addressBook.setUpdateTime(timestamp);
            addressBook.setUpdateCount(0);
        });
        addressBooks = addressBookRepository.saveAll(addressBooks);
        return ResultUtil.success(GlobalEnum.INSERT_SUCCESS, addressBooks);
    }

    /**
     * 批量或单个删除通讯录
     *
     * @param bookIds
     * @return
     */
    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public ResultEntity delete(String... bookIds) {
        List<AddressBook> addressBooks = new ArrayList<AddressBook>(bookIds.length) {{
            for (String bookId : bookIds) {
                add(AddressBook.builder().bookId(bookId).build());
            }
        }};
        addressBookRepository.deleteAll(addressBooks);
        return ResultUtil.success(GlobalEnum.DELETE_SUCCESS, bookIds.length);
    }

    /**
     * 修改通讯录
     *
     * @param addressBook
     * @return
     */
    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public ResultEntity update(AddressBook addressBook) {
        Optional<AddressBook> optionalBook = addressBookRepository.findById(addressBook.getBookId());
        if (!optionalBook.isPresent()) {
            throw new RuntimeException("该通讯录不存在!");
        }
        AddressBook oldBook = optionalBook.get();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        addressBook.setUpdateCount(Integer.sum(oldBook.getUpdateCount(), 1));
        addressBook.setCreateTime(oldBook.getCreateTime());
        addressBook.setUpdateTime(timestamp);
        addressBook = addressBookRepository.save(addressBook);
        return ResultUtil.success(GlobalEnum.UPDATE_SUCCESS, addressBook);
    }

    /**
     * 批量修改通讯录
     *
     * @param addressBooks
     * @return
     */
    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public ResultEntity update(List<AddressBook> addressBooks) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        addressBooks.forEach(addressBook -> {
            addressBook.setUpdateCount(Integer.sum(addressBook.getUpdateCount(), 1));
            addressBook.setUpdateTime(timestamp);
        });
        addressBooks = addressBookRepository.saveAll(addressBooks);
        return ResultUtil.success(GlobalEnum.INSERT_SUCCESS, addressBooks);
    }

    /**
     * 查询通讯录
     *
     * @param addressBook
     * @param pageable
     * @return
     */
    @Override
    public ResultEntity list(AddressBook addressBook, Pageable pageable) {
        String name = addressBook.getName();
        String mail = addressBook.getMail();
        if (!StringUtils.isEmpty(name)) {
            name = "%".concat(name).concat("%");
        } else {
            name = "%%";
        }
        if (!StringUtils.isEmpty(mail)) {
            mail = "%".concat(mail).concat("%");
        } else {
            mail = "%%";
        }
        Page<AddressBook> addressBooks = addressBookRepository.findByNameLikeAndMailIsLikeOrderByUpdateTimeDesc(name, mail, pageable);
        return ResultUtil.success(GlobalEnum.QUERY_SUCCESS, addressBooks);
    }

    /**
     * 查询通讯录
     *
     * @param addressBook
     * @return
     */
    @Override
    public List<AddressBook> list(AddressBook addressBook) {
        Optional<AddressBook> optionalBook = addressBookRepository.findById(addressBook.getBookId());
        if (optionalBook.isPresent()) {
            return new ArrayList<AddressBook>() {{
                add(optionalBook.get());
            }};
        } else {
            return new ArrayList<>();
        }

    }
}
