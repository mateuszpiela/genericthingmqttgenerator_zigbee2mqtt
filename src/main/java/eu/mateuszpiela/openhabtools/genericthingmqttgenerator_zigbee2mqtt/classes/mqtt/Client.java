package eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.classes.mqtt;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;


import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.classes.Config;

public class Client{
    private Config config;
    private IMqttClient iMqttClient;

    public Client(Config config) throws MqttException {
        iMqttClient = new MqttClient(
            "tcp://" + config.getHost() + ":" + String.valueOf(config.getPort()),
            UUID.randomUUID().toString()
        );

        this.config = config;
    }

    public void Connect() throws MqttSecurityException, MqttException {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();

        if(this.config.getUsername().length() > 0)
        {
            mqttConnectOptions.setUserName(this.config.getUsername());
            mqttConnectOptions.setPassword(this.config.getPassword().toCharArray());
        }

        iMqttClient.connect(mqttConnectOptions);
    }

    public boolean isConnected() {
        return iMqttClient.isConnected();
    }

    public void Disconnect() throws MqttException {
        iMqttClient.disconnect();
    }

    public String getZ2MDevices() throws MqttSecurityException, MqttException, InterruptedException, UnsupportedEncodingException {
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicReference<MqttMessage> msg = new AtomicReference<MqttMessage>();

        iMqttClient.subscribe(config.getBaseName() + "/bridge/devices", new IMqttMessageListener() {

            @Override
            public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
                msg.set(arg1);

                latch.countDown();
            }
        });
        
        latch.await();

        return new String(msg.get().getPayload(), "UTF-8");
    }

    public void finalize() throws MqttException {
        Disconnect();
    }
}
