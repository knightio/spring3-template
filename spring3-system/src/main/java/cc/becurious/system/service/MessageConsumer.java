package cc.becurious.system.service;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;


@Service
@RocketMQMessageListener(consumerGroup = "cg_test", topic = "topic_test")
public class MessageConsumer implements RocketMQListener<String> {

    public static final Log log = LogFactory.getLog(MessageConsumer.class);

    @Override
    public void onMessage(String message) {
        log.debug(message);
    }
}
