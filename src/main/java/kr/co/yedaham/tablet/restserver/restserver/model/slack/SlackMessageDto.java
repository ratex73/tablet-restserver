package kr.co.yedaham.tablet.restserver.restserver.model.slack;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class SlackMessageDto {
    @Getter
    @NoArgsConstructor
    public static class Basic {

        private String channel;
        private String username;
        @JsonProperty("icon_emoji")
        private String iconEmoji;
        private String text;

        @Builder
        public Basic(String text) {
            this.channel = "tablet";
            this.username = "webhookbot";
            this.iconEmoji = "ghost";
            this.text = text;
        }
    }
}
