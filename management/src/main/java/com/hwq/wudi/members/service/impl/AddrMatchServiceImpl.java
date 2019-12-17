package com.hwq.wudi.members.service.impl;

import com.hwq.wudi.members.entity.Addr;
import com.hwq.wudi.members.mapper.AddrMatchMapper;
import com.hwq.wudi.members.service.AddrMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @Auther: haowenqiang
 * @Description:
 */
@Service
public class AddrMatchServiceImpl implements AddrMatchService {

    @Autowired
    private AddrMatchMapper addrMatchMapper;

    @Transactional
    @Override
    public void addrMatch() {

        addrMatchMapper.updateAllProvince();

        List<Addr> allProvince = addrMatchMapper.getAllProvince();

        for (Addr addr : allProvince) {
            List<Addr> mkLv2 = addrMatchMapper.getMkChildren(addr.getCode());
            List<Addr> ivLv2 = addrMatchMapper.getIvChildren2(addr.getIvCode());
            if ("台湾省".equals(addr.getName())) {
                List<Addr> ivLv3 = addrMatchMapper.getIvChildren3(ivLv2.get(0).getIvCode());
                for (Addr mk2 : mkLv2) {
                    //台湾,在iv中二级市只有一个台湾市，在魔筷中分的比较细、
                    // 对于台湾，iv中的三级县区，对应魔筷中的二级市
                    mk2.setIvCode(ivLv2.get(0).getIvCode());
                    mk2.setIvName(ivLv2.get(0).getIvName());
                    addrMatchMapper.updateAddr(mk2);
                    List<Addr> mkLv3 = addrMatchMapper.getMkChildren(mk2.getCode());
                    Optional<Addr> iv3 = ivLv3.stream().filter(mk2::matchRuleB).findFirst();
                    if (iv3.isPresent()) {
                        Addr eqAddr = iv3.get();
                        for (Addr mk3 : mkLv3) {
                            mk3.setIvCode(eqAddr.getIvCode());
                            mk3.setIvName(eqAddr.getIvName());
                            addrMatchMapper.updateAddr(mk3);
                        }
                    }
                }
                continue;
            }
            //市级匹配
            for (Addr mk2 : mkLv2) {
                //全字匹配
                Optional<Addr> all = ivLv2.stream().filter(mk2::matchRuleA).findFirst();
                if (all.isPresent()) {
                    Addr eqAddr = all.get();
                    update(mk2, eqAddr, 1);
                    continue;
                }
                //去掉最后一个字全匹配
                Optional<Addr> first = ivLv2.stream().filter(mk2::matchRuleB).findFirst();
                if (first.isPresent()) {
                    Addr eqAddr = first.get();
                    update(mk2, eqAddr, 1);
                    continue;
                }
                //魔筷中 "省直辖县级行政区划"和“自治区直辖县级行政区划” 算作一个单独的二级市，具体的地名算作三级区县.
                //iv中 直辖县 算作二级市，还有具体的三级区县。
                //这里取iv的第一个三级区县作为直辖县的第三级
                if (mk2.getName().contains("直辖县")) {
                    //所有的直辖县
                    List<Addr> mkLv3 = addrMatchMapper.getMkChildren(mk2.getCode());
                    for (Addr mk3 : mkLv3) {
                        Optional<Addr> zxx = ivLv2.stream().filter(mk3::matchRuleC).findFirst();
                        if (zxx.isPresent()) {
                            Addr eqAddr = zxx.get();
                            List<Addr> ivLv3 = addrMatchMapper.getIvChildren3(eqAddr.getIvCode());
                            mk3.setIvCode(eqAddr.getIvCode()+"-"+ivLv3.get(0).getIvCode());
                            mk3.setIvName(eqAddr.getName()+"-"+ivLv3.get(0).getIvName());
                            addrMatchMapper.updateAddr(mk3);
                        }
                    }
                    continue;
                }

                //魔筷重庆二级市下面有 “重庆市”和 “县”
                //处理县的方法-->“县” 对应iv中的重庆市
                if ("重庆".equals(addr.getName())) {
                    update(mk2, ivLv2.get(0), 1);
                    continue;
                }
                //新疆--如果上面的规则皮配不上，可能存在例如 哈密市--哈密地区 的情况
                if ("新疆".equals(addr.getName())) {
                    Optional<Addr> xj = ivLv2.stream().filter(mk2::matchRuleXJ).findFirst();
                    if (xj.isPresent()) {
                        Addr eqAddr = xj.get();
                        update(mk2, eqAddr, 1);
                    }
                }

            }
        }

    }

    //区县级匹配
    private void dealWithLv3(String ids, String ivCode) {
        List<Addr> mkLv3 = addrMatchMapper.getMkChildren(ids);
        List<Addr> ivLv3 = addrMatchMapper.getIvChildren3(ivCode);
        for (Addr mk3 : mkLv3) {
            //去掉最后一个字全匹配，比如 万全区 和 万全县 是可以匹配的。
            Optional<Addr> first = ivLv3.stream().filter(mk3::matchRuleB).findFirst();
            if (first.isPresent()) {
                Addr eqAddr = first.get();
                update(mk3, eqAddr, 2);
                continue;
            }
            //如果全匹配匹配不上，取前两个字匹配，如：坪山区 -- 坪山新区 ，南城街道办事处 -- 南城区
            Optional<Addr> second = ivLv3.stream().filter(mk3::matchRuleC).findFirst();
            if (second.isPresent()) {
                Addr eqAddr = second.get();
                update(mk3, eqAddr, 2);
                continue;
            }
            //对于县升区的情况，比如 栾城区--滦县   蓟州区--蓟县
            Optional<Addr> third = ivLv3.stream().filter(mk3::matchRuleD).findFirst();
            if (third.isPresent()) {
                Addr eqAddr = third.get();
                update(mk3, eqAddr, 2);
            }
        }
    }

    private void update(Addr mk, Addr iv, int flag) {
        mk.setIvCode(iv.getIvCode());
        mk.setIvName(iv.getIvName());
        addrMatchMapper.updateAddr(mk);
        if (flag == 1) {
            dealWithLv3(mk.getCode(), iv.getIvCode());
        }
    }

    public static void main(String[] args) {
        String s = "省直辖县级行政区划";

        System.out.println(s.contains("直辖县"));

    }

}
