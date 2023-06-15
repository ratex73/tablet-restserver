package kr.co.yedaham.tablet.restserver.restserver.model.slack;

import lombok.Getter;

@Getter
public enum SlackTarget {
    CH_BOT("https://hooks.slack.com/services/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");

    private final String webHookUrl;


    SlackTarget(String webHookUrl) {
        this.webHookUrl = webHookUrl;
    }
}
