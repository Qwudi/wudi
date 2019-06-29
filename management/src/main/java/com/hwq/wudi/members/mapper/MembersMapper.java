package com.hwq.wudi.members.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwq.wudi.members.entity.Members;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员主信息表 Mapper 接口
 * </p>
 *
 * @author haowenqiang
 * @since 2019-06-12
 */
public interface MembersMapper extends BaseMapper<Members> {

    IPage<Members> getMembersPageList(Page page, @Param("members") Members members);

}
