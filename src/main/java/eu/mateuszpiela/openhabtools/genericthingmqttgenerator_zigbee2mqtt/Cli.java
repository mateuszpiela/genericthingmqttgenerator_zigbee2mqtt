package eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.classes.Config;

public class Cli {
    private CommandLine cmd;

    /**
     * Command Line Options
     * @return Options
     */
    private Options CliOptions() {
        Options options = new Options();
        
        options.addOption("h", "host", true, "MQTT Host");
        options.addOption("p", "port", true, "MQTT Port");
        options.addOption("u", "username", true, "MQTT Username");
        options.addOption("pwd", "password", true, "MQTT Password");
        options.addOption("b", "bridgeuid", true, "Bridge UID from OpenHAB");
        options.addOption("bt", "z2mbasetopic", true, "Zigbee2Mqtt basetopic");
        options.addOption("lae", "legacyavailabilityenabled", false, "Is enabled legacy availability in Zigbee2Mqtt?");
        options.addOption("?", "help", false, "Display help");

        return options;
    }

    /**
     * Command Line Parser
     * @param args
     * @throws ParseException
     */
    public void CliParser(String args[]) throws ParseException {
        Options options = CliOptions();

        CommandLineParser parser = new DefaultParser();
        cmd = parser.parse(options, args);

        // Check if the parameter is help and if it is display Help
        if(cmd.hasOption("?")){
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("oh3-genericmqttgenerator", options);    
            System.exit(0);
        }

        // Don't allow empty arguments
        if(args.length == 0) {
           System.out.println("The args are empty");
           System.exit(0);
        }

    }


    /**
     * Create Config object and set properties from CLI Arguments
     *
     * @return Config
     */
    public Config getConfigFromArgs() {
        // Check if required options are setted
        if(!cmd.hasOption("h") || !cmd.hasOption("b")) {
            System.out.println("Missing required options host or bridgeuid");
            System.exit(0);
        }

        Config config = new Config();

        String host = cmd.getOptionValue("h");
        String port = cmd.getOptionValue("p", "1883");
        String user = cmd.getOptionValue("u", "");
        String password = cmd.getOptionValue("pwd", "");
        String bridgeuid = cmd.getOptionValue("b");
        String basetopic = cmd.getOptionValue("bt", "zigbee2mqtt");
        Boolean legacyAvailable = cmd.hasOption("lae");

        config.setHost(host);
        config.setPort(Integer.parseInt(port));
        config.setUsername(user);
        config.setPassword(password);
        config.setBridgeUid(bridgeuid);
        config.setIsLegacyAvailabilityEnabled(legacyAvailable);
        config.setBaseName(basetopic);

        return config;
    }
}
