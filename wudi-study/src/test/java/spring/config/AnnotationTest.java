package spring.config;

import com.hwq.StudyApplication;
import com.hwq.rabbitmq.RabbitMqProviderTest;
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
    private RabbitMqProviderTest rabbitMqProviderTest;

    @Test
    public void test1(){
        for (int i = 0; i<4 ;i++) {
            rabbitMqProviderTest.send("支付完成", "pay", "wudi.direct.exchange");
        }
    }



}
