package com.hwq.wudi;

import lombok.Data;

/**
 * @Auther: haowenqiang
 * @Date: 2019/8/14
 * @Description:
 */
@Data
public class Student {
    private String name;
    private Integer score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }
}
