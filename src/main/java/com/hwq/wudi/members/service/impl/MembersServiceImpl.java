package com.hwq.wudi.members.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwq.wudi.members.entity.Members;
import com.hwq.wudi.members.mapper.MembersMapper;
import com.hwq.wudi.members.service.IMembersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员主信息表 服务实现类
 * </p>
 *
 * @author haowenqiang
 * @since 2019-06-12
 */
@Service
public class MembersServiceImpl extends ServiceImpl<MembersMapper, Members> implements IMembersService {

    @Autowired
    MembersMapper membersMapper;

    @Override
    public IPage<Members> getMembersPageList(Page page, Members members) {
        IPage<Members> result = membersMapper.getMembersPageList(page,members);
        membersMapper.update(members, new UpdateWrapper<>());
        return result;
    }
}
