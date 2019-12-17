package com.hwq.wudi.members.entity;

/**
 * @Auther: haowenqiang
 * @Description:
 */
public class Addr {
    private String code;
    private String name;
    private String ivCode;
    private String ivName;

    public String getIvName() {
        return ivName;
    }

    public void setIvName(String ivName) {
        this.ivName = ivName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIvCode() {
        return ivCode;
    }

    public void setIvCode(String ivCode) {
        this.ivCode = ivCode;
    }

    private String getShortName(String name, int l) {
        return name.substring(0, l);
    }

    /**
     * 全匹配
     *
     * @param o
     * @return
     */
    public boolean matchRuleA(Addr o) {
        return name.equals(o.getName());
    }

    /**
     * 去掉最后一个字匹配
     *
     * @param o
     * @return
     */
    public boolean matchRuleB(Addr o) {
        return name.substring(0, name.length() - 1).equals(o.getName().substring(0, o.getName().length() - 1));
    }

    /**
     * 匹配前两个字
     *
     * @param o
     * @return
     */
    public boolean matchRuleC(Addr o) {
        return getShortName(name, 2).equals(getShortName(o.getName(), 2));
    }

    /**
     * 匹配第一个字
     *
     * @param args
     */
    public boolean matchRuleD(Addr o) {
        if (o.getName().length() == 2) {
            return getShortName(name, 1).equals(getShortName(o.getName(), 1));
        }
        return false;
    }
    /**
     * 新疆特殊匹配
     *
     * @param o
     * @return
     */
    public boolean matchRuleXJ(Addr o) {
        return getShortName(name, name.length()-1).equals(getShortName(o.getName(), o.getName().length()-2));
    }

}
