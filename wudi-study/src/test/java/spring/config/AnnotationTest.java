package spring.config;

import com.hwq.StudyApplication;
import com.hwq.spring.config.BenzCar;
import com.hwq.spring.config.Car;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther: haowenqiang
 * @Date: 2019/7/3
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudyApplication.class)
public class AnnotationTest {
    @Autowired
    private Car car;
    @Autowired
    private BenzCar benzCar;

    @Test
    public void test1(){
        System.out.println(car.getBenzCar() == benzCar);
    }
}
