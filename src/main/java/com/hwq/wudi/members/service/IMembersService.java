package com.hwq.wudi.members.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hwq.wudi.members.entity.Members;

/**
 * <p>
 * 会员主信息表 服务类
 * </p>
 *
 * @author haowenqiang
 * @since 2019-06-12
 */
public interface IMembersService extends IService<Members> {

    IPage<Members> getMembersPageList(Page page, Members members);
}

