package com.hwq.wudi.members.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwq.wudi.members.entity.Members;
import com.hwq.wudi.members.service.IMembersService;
import com.hwq.wudi.util.RespEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    IMembersService membersService;

    @GetMapping("/list")
    public RespEntity getMembersPageList(Page page, Members members) {
//        IPage<Members> membersPageList = membersService.getMembersPageList(page, members);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("member_id", "nick_name","user_name");
        UpdateWrapper updateWrapper = new UpdateWrapper();
//        IPage page1 = membersService.page(page, queryWrapper);
        IPage iPage = membersService.pageMaps(page, queryWrapper);
        return RespEntity.ok(iPage);
    }

}

