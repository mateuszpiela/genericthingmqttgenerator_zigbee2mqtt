package eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.entities.ohyaml;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Channel {
    private String id;
    private String channelTypeUID;
    private String label;
    private String description;
    private ChannelConfiguration configuration;
}
