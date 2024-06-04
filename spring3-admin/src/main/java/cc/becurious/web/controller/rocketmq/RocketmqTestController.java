package cc.becurious.web.controller.rocketmq;

import cc.becurious.system.service.RocketMQService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("rocketMQ")
public class RocketmqTestController {

    @Resource
    private RocketMQService rocketMQService;

    @GetMapping("test")
    public void test(){
        rocketMQService.sendDelay();
    }

}
