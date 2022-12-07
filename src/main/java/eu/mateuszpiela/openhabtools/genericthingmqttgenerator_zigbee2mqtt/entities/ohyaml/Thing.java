package eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.entities.ohyaml;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * OpenHAB UI Thing YAML
 */
@Getter
@Setter
public class Thing {
    @JsonProperty("UID")
    private String uid;

    private String label;

    private String thingTypeUID;

    private ThingConfiguration configuration;

    private String bridgeUID;

    private List<Channel> channels;

}
