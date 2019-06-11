package com.hwq.wudi.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwq.wudi.entity.User;
import com.hwq.wudi.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: haowenqiang
 * @Date: 2019/6/11 0011 23:38
 * @Description:
 */
@RestController()
public class UserControler {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/list")
    public List<User> getUserList(){
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
        return userList;
    }
    //自带的分页
    @GetMapping("/page1")
    public IPage page(Page page, boolean listMode) {
        if (listMode) {
            // size 小于 0 不在查询 total 及分页，自动调整为列表模式。
            // 注意！！这个地方自己控制好！！
            page.setSize(-1);
        }
        return userMapper.selectPage(page,null);
    }
    //xml的分页
    @GetMapping("/page2")
    public IPage page(Page page, Integer age) {

        return userMapper.getUserListPage(page,age);
    }
}
