package demo;

import com.alibaba.ttl.TtlRunnable;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.annotation.WebFilter;


/**
 * @author <a href="mailto:likelovec@gmail.com">fzdwx</a>
 * @date 2022/3/7 20:12
 */
@WebFilter(urlPatterns = "/*")
@SpringBootApplication
@RestController
@Slf4j
public class Serv {

    public static void main(String[] args) {
        final ConfigurableApplicationContext ctx = SpringApplication.run(Serv.class, args);
        new Thread(TtlRunnable.get(() -> {
            for (; ; ) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("----------------------------" + Context.traceId());
            }
        })).start();
    }

    @Component
    @Slf4j
    @RocketMQMessageListener(topic = "cccccccccccccccccc", consumerGroup = "test-consumer-group")
    public static class Lins implements RocketMQListener<String> {

        @SneakyThrows
        @Override
        public void onMessage(final String s) {
            Thread.sleep(1000);
            log.info("收到消息：{}", s);
        }
    }

    @GetMapping
    public void test() throws InterruptedException {
        System.out.println("controller: " + Thread.currentThread().getName() + " --- " + Context.traceId());
    }
}