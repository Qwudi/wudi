package com.hwq.wudi.members.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwq.wudi.members.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @Auther: haowenqiang
 * @Description:
 */
public interface UserMapper extends BaseMapper<User> {

    IPage<User> getUserListPage(Page<User> page,@Param("age") Integer age);

    @Update("update user set name='laok' where id = #{id}")
    void updateByIdXml(@Param("id") String id);
}
