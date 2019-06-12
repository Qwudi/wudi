package com.hwq.wudi.members.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 会员主信息表
 * </p>
 *
 * @author haowenqiang
 * @since 2019-06-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sdb_ssg_members")
public class Members implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 自增主键
     */
    @TableId(value = "member_code", type = IdType.AUTO)
    private Integer memberCode;

    /**
     * 用户ID
     */
    @TableField("member_id")
    private String memberId;

    /**
     * IV用户ID
     */
    @TableField("member_iv_id")
    private String memberIvId;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 手机
     */
    @TableField("mobile")
    private String mobile;


    /**
     * 生年
     */
    @TableField("b_year")
    private Integer bYear;

    /**
     * 生月
     */
    @TableField("b_month")
    private Integer bMonth;

    /**
     * 生日
     */
    @TableField("b_day")
    private Integer bDay;

    /**
     * 性别
     */
    @TableField("sex")
    private String sex;

    /**
     * 注册IP地址
     */
    @TableField("reg_ip")
    private String regIp;

    /**
     * 注册时间
     */
    @TableField("regtime")
    private Integer regtime;

    /**
     * 是否失效
     */
    @TableField("disabled")
    private String disabled;

    /**
     * 登录次数
     */
    @TableField("login_count")
    private Integer loginCount;

    /**
     * 头像类型
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 来源
     */
    @TableField("source")
    private String source;

    /**
     * 头像url
     */
    @TableField("avatar_rel_path")
    private String avatarRelPath;

    /**
     * 真实姓名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 身份证号
     */
    @TableField("ident_code")
    private String identCode;

    /**
     * 业态
     */
    @TableField("bizuntcd")
    private String bizuntcd;

    /**
     * 微信用户与公众号唯一标识
     */
    @TableField("openid")
    private String openid;

    /**
     * 联合唯一标识
     */
    @TableField("unionid")
    private String unionid;

    /**
     * 绑定状态
     */
    @TableField("bundle_type")
    private String bundleType;

    @TableField("wx_nick_name")
    private String wxNickName;

    @TableField("wx_avatar_rel_path")
    private String wxAvatarRelPath;


}
