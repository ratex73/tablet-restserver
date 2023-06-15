package kr.co.yedaham.tablet.restserver.restserver.config.restslack;

import kr.co.yedaham.tablet.restserver.restserver.model.slack.SlackMessageDto;
import kr.co.yedaham.tablet.restserver.restserver.model.slack.SlackTarget;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
@Slf4j
public class SlackSenderManager {
    private final RestTemplate restTemplate;

    public void send(SlackTarget target, String text) {
        restTemplate.postForEntity(target.getWebHookUrl(), new SlackMessageDto.Basic(text), String.class);
    }
}
