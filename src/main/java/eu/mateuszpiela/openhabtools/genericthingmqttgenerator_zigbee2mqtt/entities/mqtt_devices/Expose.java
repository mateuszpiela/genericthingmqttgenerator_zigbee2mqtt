package eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.entities.mqtt_devices;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
@Getter
@Setter
public class Expose {
    private Boolean access;
    private String description;
    private String name;
    private String property;
    private String type;
    private String unit;

    @JsonProperty("value_max")
    private int valueMax;

    @JsonProperty("value_min")
    private int valueMin;

    @JsonProperty("value_step")
    private int valueStep;

    @JsonProperty("value_off")
    private String valueOff;

    @JsonProperty("value_on")
    private String valueOn;
    
}
