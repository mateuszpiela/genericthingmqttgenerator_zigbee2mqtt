package eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.classes.mapper;

import java.util.HashMap;
import java.util.Map;

/**
 * OpenHAB Channel type mapper from Expose (Zigbee2Mqtt)
 */
public class ChannelTypeMapper {
    public Map<String, String> channelType = new HashMap<>();

    public ChannelTypeMapper() {
        channelType.put("binary", "switch");
        channelType.put("numeric", "number");
    }
}
