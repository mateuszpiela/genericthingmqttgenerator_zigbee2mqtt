package eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.entities.ohyaml;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelConfiguration {
    private String commandTopic;
    private String stateTopic;
    private int min;
    private int max;
    private int step;
    private String unit;

    @JsonProperty("on")
    private String on;

    @JsonProperty("off")
    private String off;
}
