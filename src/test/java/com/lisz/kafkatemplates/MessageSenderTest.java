package com.lisz.kafkatemplates;

import com.lisz.SpringbootKafkaApplication;
import com.lisz.service.IMessageSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = SpringbootKafkaApplication.class)
@RunWith(SpringRunner.class)
public class MessageSenderTest {
    @Autowired
    private IMessageSender iMessageSender;

    @Test
    public void testSend() {
        iMessageSender.send("topic02", "003", "This is kafka transaction template with @Transactional annotation");
    }
}
