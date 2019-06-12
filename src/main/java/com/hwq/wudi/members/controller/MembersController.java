package com.hwq.wudi.members.controller;


import com.hwq.wudi.members.entity.Members;
import com.hwq.wudi.members.mapper.MembersMapper;
import com.hwq.wudi.util.RespEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 会员主信息表 前端控制器
 * </p>
 *
 * @author haowenqiang
 * @since 2019-06-12
 */
@RestController
@RequestMapping("/members")
public class MembersController {
    @Autowired
    MembersMapper membersMapper;
    @GetMapping("/list")
    public RespEntity getUserList(){
        List<Members> userList = membersMapper.selectList(null);
        userList.forEach(System.out::println);
        return RespEntity.ok(userList);
    }

}

