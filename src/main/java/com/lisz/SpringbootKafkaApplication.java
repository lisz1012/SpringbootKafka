package com.lisz;

import com.lisz.service.IMessageSender;
import com.lisz.service.MessageSender;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.messaging.handler.annotation.SendTo;

import java.io.IOException;
// 踩坑：用Unit test发消息可能收不到，直接用Application 这个class发
@SpringBootApplication
public class SpringbootKafkaApplication {

    public static void main( String[] args ) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(SpringbootKafkaApplication.class, args);
        Thread.sleep(5000);
        IMessageSender sender = context.getBean(IMessageSender.class);
        for (int i = 0; i < 9; i++) {
            sender.send("topic01", "lisz - " + i, "lisz1012 - " + i);
            Thread.sleep(1000);
        }

        System.in.read();
    }

    @KafkaListeners(@KafkaListener(topics = {"topic01"}))
    public void receive01(ConsumerRecord<String, String> record) {
        System.out.println("record: " + record);
    }

    @KafkaListeners(@KafkaListener(topics = {"topic02"}))
    @SendTo("topic03")  // 将String返回值转发给topic03
    public String receive02(ConsumerRecord<String, String> record) {
        return record.value() + " --- lisz";
    }

    public void send() {

    }
}
