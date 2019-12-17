package com.hwq.wudi.members.mapper;

import com.hwq.wudi.members.entity.Addr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: haowenqiang
 * @Description:
 */
public interface AddrMatchMapper {

    List<Addr> getAllProvince();

    List<Addr> getMkChildren(@Param("ids") String ids);

    List<Addr> getIvChildren2(@Param("ivCode") String ivCode);

    List<Addr> getIvChildren3(@Param("ivCode") String ivCode);

    void updateAddr(Addr addr);

    void updateAllProvince();

}
