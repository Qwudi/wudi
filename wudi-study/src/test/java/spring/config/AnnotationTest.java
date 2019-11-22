package spring.config;

import com.hwq.StudyApplication;
import com.hwq.rabbitmq.RabbitMqProviderTest;
import com.hwq.redis.SimpleLimiter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.regex.Pattern;

/**
 * @Auther: haowenqiang
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

    @Test
    public void test2(){
        for (int i = 0; i<10 ;i++) {
            System.out.println(SimpleLimiter.isAllowOperation("ccc", 60, 5));
        }
    }

    public static void main(String[] args) {
        String str = "123456";
        Pattern pattern = Pattern.compile("^[0-9]*$");
        System.out.println(pattern.matcher(str).matches());
    }

}
