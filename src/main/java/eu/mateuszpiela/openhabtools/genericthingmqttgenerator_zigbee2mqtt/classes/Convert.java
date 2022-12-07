package eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.classes.mapper.ChannelTypeMapper;
import eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.entities.mqtt_devices.Device;
import eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.entities.mqtt_devices.Expose;
import eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.entities.ohyaml.Channel;
import eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.entities.ohyaml.ChannelConfiguration;
import eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.entities.ohyaml.Thing;
import eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.entities.ohyaml.ThingConfiguration;

public class Convert {
    private Device[] devices;
    private Config config;

    /**
     * Constructor Convert Class
     * 
     * @param devices
     * @param config
     */
    public Convert(Device[] devices, Config config) {
        this.devices = devices;
        this.config = config;
    }

    /**
     * Get OpenHAB Thing Entity from Device entity (Zigbee2Mqtt)
     * 
     * @see Device
     * @return List<Thing>
     */
    public List<Thing> getOpenHabThings() {
        List<Thing> things = new ArrayList<>();

        for (Device device : devices) {
            if(!device.getType().equals("EndDevice")) {
                continue;
            }

            if(!device.getInterviewCompleted()) {
                continue;
            }
           
            String generatedUidCode = generateString(7).toLowerCase();

            Thing thing = new Thing();

            String uid[] = config.getBridgeUid().split(":");
            String code = uid[uid.length - 1];
            
            thing.setUid("mqtt:topic:" + code + ":" + generatedUidCode);
            thing.setLabel(device.getFriendlyName());
            thing.setThingTypeUID("mqtt:topic");
            thing.setBridgeUID(config.getBridgeUid());
            thing.setConfiguration(generateThingConfig(device, config));
            thing.setChannels(getChannels(device));

            things.add(thing);
        }

        return things;
    }

    /**
     * Convert from expose (zigbee2mqtt) to OpenHAB Channels
     * 
     * @param device
     * @return
     */
    private List<Channel> getChannels(Device device) {
        List<Channel> channels = new ArrayList<>();
        ChannelTypeMapper channelTypeMapper = new ChannelTypeMapper();

        for(Expose expose: device.getDefinition().getExposes()) {
            Channel channel = new Channel();
            channel.setLabel(expose.getDescription());
            String channelType = channelTypeMapper.channelType.get(expose.getType());

            channelType = (channelType != null) ? channelType : "switch";

            if(expose.getProperty().equals("contact"))
            {
                channelType = "contact";
            }

            channel.setChannelTypeUID("mqtt:" + channelType);
            channel.setId(expose.getProperty());


            channel.setConfiguration(getChannelConfiguration(device, expose, config));

            channels.add(channel);
        }

        return channels;
    }

    /**
     * Generate OpenHAB channel configuration from expose (zigbee2mqtt)
     * 
     * @param device
     * @param expose
     * @param config
     * @return
     */
    private ChannelConfiguration getChannelConfiguration(Device device, Expose expose,Config config) {
        ChannelConfiguration channelConfiguration = new ChannelConfiguration();
        channelConfiguration.setStateTopic(config.getBaseName() + "/" + device.getFriendlyName() + "/" + expose.getProperty());
        
        if(expose.getValueMax() > 0){
            channelConfiguration.setMax(expose.getValueMax());
            channelConfiguration.setMin(expose.getValueMin());
        }

        if(expose.getValueStep() > 0){
            channelConfiguration.setStep(expose.getValueStep());
        }

        if(expose.getValueOn() != null) {
            channelConfiguration.setOn(expose.getValueOn());
        }

        if(expose.getValueOff() != null) {
            channelConfiguration.setOff(expose.getValueOff());
        }

        if(expose.getUnit() != null) {
            channelConfiguration.setUnit(expose.getUnit());
        }

        return channelConfiguration;
    }

    /**
     * Generate thing configuration
     * 
     * @param device
     * @param config
     * @return
     */
    private ThingConfiguration generateThingConfig(Device device, Config config) {
        ThingConfiguration thingConfiguration = new ThingConfiguration();
        
        if(config.getIsLegacyAvailabilityEnabled()) {
            thingConfiguration.setAvailabilityTopic(config.getBaseName() + "/" + device.getFriendlyName() + "/availability");
            thingConfiguration.setPayloadAvailable("online");
            thingConfiguration.setPayloadNotAvailable("offline");
        }

        return thingConfiguration;
    }



    private String generateString(int length) {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = length;
        Random random = new Random();
    
        String generatedString = random.ints(leftLimit, rightLimit + 1)
          .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
          .limit(targetStringLength)
          .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
          .toString();

        return generatedString;
    }
    
}
