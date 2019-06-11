package com.hwq.wudi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwq.wudi.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @Auther: haowenqiang
 * @Date: 2019/6/11 0011 23:13
 * @Description:
 */
public interface UserMapper extends BaseMapper<User> {

    IPage<User> getUserListPage(Page<User> page,@Param("age") Integer age);
}
