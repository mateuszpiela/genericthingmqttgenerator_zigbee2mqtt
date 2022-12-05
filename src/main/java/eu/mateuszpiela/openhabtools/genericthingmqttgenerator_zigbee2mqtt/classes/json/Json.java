package eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.classes.json;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.entities.mqtt_devices.Device;

public class Json {
    public Device[] parseZ2MDevices(String devicesJsonString) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        
        Device[] device = mapper.readValue(devicesJsonString, Device[].class);

        return device;
    }
    
}
