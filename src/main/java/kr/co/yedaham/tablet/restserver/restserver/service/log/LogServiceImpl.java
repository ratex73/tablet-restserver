package kr.co.yedaham.tablet.restserver.restserver.service.log;

import kr.co.yedaham.tablet.restserver.restserver.config.restslack.SlackSenderManager;
import kr.co.yedaham.tablet.restserver.restserver.model.slack.SlackTarget;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;


@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService{
    private final RestTemplate restTemplate;
    private final SlackSenderManager slackSenderManager;

    private static final Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);

    @Override
    public String sendLogServer(String log) {
        String result = "success";
        try {
            String url = "http://10.10.200.174:7000";
            ResponseEntity<String> response = restTemplate.postForEntity(URI.create(url), log,
                    String.class);
            String data = response.getBody().trim();
        } catch (Exception ex) {
            result = "failed";
        }

        return result;
    }

    @Override
    public String sendSlackServer(String log) {

        String result = "success";
        try {
            slackSenderManager.send(SlackTarget.CH_BOT, log);
        } catch (Exception ex) {
            result = "failed";
        }

        return result;
    }
}
