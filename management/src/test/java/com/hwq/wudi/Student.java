package com.hwq.wudi;

import lombok.Data;

/**
 * @Auther: haowenqiang
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
