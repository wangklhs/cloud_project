package org.example.cloud.task;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
//@Async
//@EnableScheduling
@EnableAsync
@Slf4j
public class TestTask {

    int i = 20;

    public TestTask(ApplicationContext applicationContext) {

        System.out.println("TestTask created..........");
    }

    @Scheduled(fixedDelay = 30 * 60 * 1000)
    public void testTaskA() {
        log.info(Thread.currentThread().getName() + " come in AAAA");
        try {
            Thread.sleep(i-- * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info(Thread.currentThread().getName() + " come out AAAA");
    }

    @Scheduled(fixedDelay = 1000)
    public void testTaskB() {
        System.out.println(Thread.currentThread().getName() + " come in BBBB");
        System.out.println(Thread.currentThread().getName() + " come out BBBB");
    }

}
