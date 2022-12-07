package eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.cli.ParseException;
import org.eclipse.paho.client.mqttv3.MqttException;

import eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.classes.Config;
import eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.classes.Convert;
import eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.classes.OHYamlCreator;
import eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.classes.json.Json;
import eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.classes.mqtt.Client;
import eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.entities.mqtt_devices.Device;
import eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.entities.ohyaml.Thing;

/**
 * Hello world!
 *
 */
public class App 
{
    /**
     * Main application entrypoint
     * @param args Application arguments
     * @throws ParseException
     */
    public static void main( String[] args ) throws ParseException
    {

        System.out.println("============================================================================");
        System.out.println("Tool for generating MQTT Generic Things for OpenHAB from Zigbee2Mqtt Devices");
        System.out.println("============================================================================");
        System.out.println("Licensed under MIT license!");

        try {
            Cli cli = new Cli();
            cli.CliParser(args);
    
            Config config = cli.getConfigFromArgs();
    
            Client client;
            
            client = new Client(config);
            client.Connect();

            if(client.isConnected()){
                System.out.println("Connected to MQTT server...");
            }

            System.out.println("Getting all devices registered in Zigbee2Mqtt topic");
            String z2mDevicesJsonString = client.getZ2MDevices();

            // Disconnect from MQTT we are having now all required datas from json
            System.out.println("Disconnecting from MQTT server");
            client.Disconnect();

            Json json = new Json();
            Device[] devices = json.parseZ2MDevices(z2mDevicesJsonString);

            Convert convert = new Convert(devices, config);
            List<Thing> things = convert.getOpenHabThings();
    
            Files.createDirectories(Paths.get("things"));
    
            System.out.println("Generating yaml files...");
            //Create yaml using converted things entity
            OHYamlCreator ohYamlCreator = new OHYamlCreator();
            ohYamlCreator.getYamlString(things);

        } catch (MqttException|IOException|InterruptedException e) {
            System.out.println("Oh you got the Black Screen Of Death :)");
            System.out.println("================================");
            System.out.println("Something went terribly wrong :(");
            System.out.println("================================");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


        


    }
}
