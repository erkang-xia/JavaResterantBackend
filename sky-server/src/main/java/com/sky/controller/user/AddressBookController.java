package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Api(tags = "用户地址管理")
@Slf4j
public class AddressBookController {

    @Autowired
    AddressBookService addressBookService;

    @PostMapping
    @ApiOperation("add 新增地址")
    public Result addAddress(@RequestBody AddressBook addressBook) {
        addressBookService.save(addressBook);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("list 查询地址")
    public Result<List<AddressBook>> listAddressBook() {
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        List<AddressBook> addresses = addressBookService.list(addressBook);
        return Result.success(addresses);
    }

    @GetMapping("/default")
    @ApiOperation("default 查询默认地址")
    public Result<AddressBook> defaultAddressBook() {
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(1);
        List<AddressBook> addresses = addressBookService.list(addressBook);
        if (addresses != null && addresses.size() > 0) {
            return Result.success(addresses.get(0));
        }
        return Result.error("没有设置默认地址");
    }

    @PutMapping
    @ApiOperation("edit 修改客户地址")
    public Result editAddressBook(@RequestBody AddressBook addressBook) {
        addressBookService.update(addressBook);
        return Result.success();
    }
    @DeleteMapping
    @ApiOperation("delete 删除客户地址")
    public Result deleteAddressBook(Long id) {
        addressBookService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询地址")
    public Result<AddressBook> getById(@PathVariable Long id) {
        AddressBook addressBook = addressBookService.getById(id);
        return Result.success(addressBook);
    }

    @PutMapping("/default")
    @ApiOperation("设置默认地址")
    public Result setDefault(@RequestBody AddressBook addressBook) {
        addressBookService.setDefault(addressBook);
        return Result.success();
    }

}
