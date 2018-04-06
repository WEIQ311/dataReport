package com.report.controller;

import com.alibaba.fastjson.JSON;
import com.report.domain.AddressBook;
import com.report.service.AddressBookService;
import com.report.vo.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 通讯录
 *
 * @author weiQiang
 */
@Controller
@RequestMapping(value = "addressBook")
public class AddressBookController extends BaseController {


    @Autowired
    private AddressBookService addressBookService;

    /**
     * 首页
     *
     * @param request
     * @param response
     * @param addressBook
     * @return
     */
    @RequestMapping(value = {"", "/", "/index", "/list"}, method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response, AddressBook addressBook) {
        ResultEntity resultEntity = addressBookService.list(addressBook, Pageable.unpaged());
        request.setAttribute("result", resultEntity);
        request.setAttribute("address", addressBook);
        return ADDRESS_INDEX;
    }

    /**
     * 打开编辑或新增通讯录页面
     *
     * @param request
     * @param response
     * @param addressBook
     * @return
     */
    @RequestMapping(value = "addOrUpdate", method = RequestMethod.GET)
    public String addOrUpdate(HttpServletRequest request, HttpServletResponse response, AddressBook addressBook) {
        String bookSearchStr = StringUtils.delete(JSON.toJSONString(addressBook), "{");
        bookSearchStr = StringUtils.delete(bookSearchStr, "}");
        request.setAttribute("status", 200);
        if (StringUtils.isEmpty(bookSearchStr)) {
            request.setAttribute("title", "新增通讯录");
        } else {
            request.setAttribute("title", "编辑通讯录");
            List<AddressBook> addressBooks = addressBookService.list(addressBook);
            if (null != addressBooks && addressBooks.size() > 0) {
                addressBook = addressBooks.get(0);
            } else {
                addressBook.setBookId("");
                request.setAttribute("message", "未查到该通讯录信息");
                request.setAttribute("status", 500);
            }
        }
        request.setAttribute("bookDetail", addressBook);
        return ADDRESS_ADD_UPDATE;
    }

    /**
     * 编辑或新增通讯录
     *
     * @param request
     * @param response
     * @param addressBook
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "insertOrUpdate", method = RequestMethod.POST)
    public String insert(HttpServletRequest request, HttpServletResponse response, @Valid AddressBook addressBook, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            request.setAttribute("message", bindingResult.getFieldError().getDefaultMessage());
            request.setAttribute("status", 200);
            request.setAttribute("bookDetail", addressBook);
            return ADDRESS_ADD_UPDATE;
        }
        ResultEntity resultEntity = new ResultEntity();
        if (StringUtils.isEmpty(addressBook.getBookId())) {
            resultEntity = addressBookService.insert(addressBook);
        } else {
            resultEntity = addressBookService.update(addressBook);
        }
        request.setAttribute("message", resultEntity.getMessage());
        request.setAttribute("success", resultEntity.isSuccess());
        return "redirect:/addressBook";
    }
}
