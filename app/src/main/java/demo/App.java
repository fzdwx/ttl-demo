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

/**
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/3/9 11:25
 */
@SpringBootApplication
@RestController
@Slf4j
public class App {

    public static void main(String[] args) {
        final ConfigurableApplicationContext ctx = SpringApplication.run(App.class, args);

        // test
        new Thread(TtlRunnable.get(() -> {
            for (; ; ) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("test thread ---------------------------- " + Context.traceId());
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

    @GetMapping("/test")
    public void test() throws InterruptedException {
        log.info("controller: " + Thread.currentThread().getName() + " --- " + Context.traceId());
    }
}