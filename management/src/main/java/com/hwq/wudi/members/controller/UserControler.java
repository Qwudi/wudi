package com.hwq.wudi.members.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwq.wudi.config.datasource.DataSource;
import com.hwq.wudi.members.entity.User;
import com.hwq.wudi.members.mapper.UserMapper;
import com.hwq.wudi.util.RespEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: haowenqiang
 * @Description:
 */
@RestController()
public class UserControler {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/list")
    @DataSource("master")
    public RespEntity getUserList(){
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
        return RespEntity.ok(userList);
    }
    //自带的分页
    @GetMapping("/page1")
    @DataSource("slave1")
    public RespEntity page(Page page, boolean listMode) {
        if (listMode) {
            // size 小于 0 不在查询 total 及分页，自动调整为列表模式。
            // 注意！！这个地方自己控制好！！
            page.setSize(-1);
        }

        return RespEntity.ok(userMapper.selectPage(page,null));
    }
    //xml的分页
    @GetMapping("/page2")
    public RespEntity page(Page page, Integer age) {

        return RespEntity.ok(userMapper.getUserListPage(page,age));
    }

    @PutMapping("/update/{id}")
    @DataSource("slave1")
    public RespEntity update(@PathVariable String id){
        userMapper.updateByIdXml(id);
        return RespEntity.ok(null);
    }
    @PutMapping("/insert")
    public RespEntity insert(@RequestBody User user){
        userMapper.insert(user);
        return RespEntity.ok(null);
    }
    @PutMapping("/update2")
    public RespEntity update2(User user){
        user.setName("92929");
        user.setEmail("");
        userMapper.updateById(user);
        return RespEntity.ok(null);
    }
}
