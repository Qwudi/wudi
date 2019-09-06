package com.hwq.wudi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WudiApplicationTests {

    Student[] students;

    @Before
    public void init() {
        students = new Student[100];
        for (int i = 0; i < 30; i++) {
            Student student = new Student("user", i);
            students[i] = student;
        }
        for (int i = 30; i < 60; i++) {
            Student student = new Student("user" + i, i);
            students[i] = student;
        }
        for (int i = 60; i < 100; i++) {
            Student student = new Student("user" + i, i);
            students[i] = student;
        }
    }

    @Test
    public void testCollect1() {
//        /**
//         * 生成List
//         */
//        List<Student> list = Arrays.stream(students).collect(Collectors.toList());
//        list.forEach((x)-> System.out.println(x));
//        /**
//         * 生成Set
//         */
//        Set<Student> set = Arrays.stream(students).collect(Collectors.toSet());
//        set.forEach((x)-> System.out.println(x));
        /**
         * 如果包含相同的key，则需要提供第三个参数，否则报错
         */
//        Map<String,Integer> map = Arrays.stream(students).collect(Collectors.toMap(Student::getName,Student::getScore,(s, a)->s+a));
//        map.forEach((x,y)-> System.out.println(x+"->"+y));
        Arrays.stream(students).filter(student -> student.getScore() > 90).collect(Collectors.toList()).forEach(System.out::println);
    }
}
