package eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.classes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Config {
    private String host = "";
    private int port = 1883;
    private String username = "";
    private String password = "";
    private String baseName = "zigbee2mqtt";
    private String bridgeUid = "";
    private Boolean isLegacyAvailabilityEnabled = false;
}