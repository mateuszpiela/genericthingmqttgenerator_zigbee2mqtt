package eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.entities.mqtt_devices;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

/**
 * Device entity from Zigbee2Mqtt
 * 
 * @see https://www.zigbee2mqtt.io/guide/usage/mqtt_topics_and_messages.html#zigbee2mqtt-bridge-devices
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Device {
    @JsonProperty("friendly_name")
    private String friendlyName;

    @JsonProperty("ieee_address")
    private String ieeeAddress;

    @JsonProperty("interview_completed")
    private Boolean interviewCompleted;

    private String type;
    
    @JsonInclude(Include.NON_NULL)
    private Definition definition;
}
