package com.lisz.kafkatemplates;

import com.lisz.SpringbootKafkaApplication;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = SpringbootKafkaApplication.class)
@RunWith(SpringRunner.class)
public class KafkaTemplateTest {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void testSend1() {//非事务下执行，也就是没有spring.kafka.producer.transaction-id-prefix=transaction-id-配置的情况
        kafkaTemplate.send(new ProducerRecord<String, String>("topic02", "002", "This is kafka template"));
    }

    @Test
    public void testSend2() {
        // 一般KafkaTemplate要在事务中，这就需要配置：spring.kafka.producer.transaction-id-prefix=transaction-id-
        // 还有另外一种方式，见：com.lisz.service.MessageSender
        kafkaTemplate.executeInTransaction(new KafkaOperations.OperationsCallback<String, String, Object>() {
            @Override
            public Object doInOperations(KafkaOperations<String, String> operations) {
                operations.send(new ProducerRecord<String, String>("topic02", "002", "This is kafka transaction template"));
                return null;
            }
        });
    }
}
