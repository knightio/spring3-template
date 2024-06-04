package cc.becurious.system.service;

import jakarta.annotation.Resource;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class RocketMQService {

    public static final Log log = LogFactory.getLog(RocketMQService.class);

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void sendDelay(){
        SendResult sendResult = rocketMQTemplate.syncSendDeliverTimeMills("topic_test", MessageBuilder.withPayload("testDelay").build(), System.currentTimeMillis() + 5000);
        log.debug(sendResult.getSendStatus().name());
        rocketMQTemplate.syncSend("topic_test",MessageBuilder.withPayload("test").build());
    }

}
