package eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.classes;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import eu.mateuszpiela.openhabtools.genericthingmqttgenerator_zigbee2mqtt.entities.ohyaml.Thing;

public class OHYamlCreator {
    public void getYamlString(List<Thing> things) throws IOException {        
        Integer totalThings = things.size();
        Integer curIndex = 1;

        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        dumperOptions.setPrettyFlow(true);
        dumperOptions.setAllowReadOnlyProperties(true);

        Representer representer = getRepresenter();

        

        for (Thing thing: things) {
            Float progressPercent = (curIndex.floatValue() / totalThings.floatValue()) * 100;
            System.out.println("Progress: " + curIndex.toString() + "/" + totalThings.toString() + " " + progressPercent.toString() + "%");
            
            PrintWriter writer = new PrintWriter(new File("things/" + thing.getLabel() + ".yaml"));
            Yaml yaml = new Yaml(representer, dumperOptions);
            yaml.dump(thing, writer);
            
            System.out.println("Now writing to file: " + "things/" + thing.getLabel() + ".yaml");
            curIndex++;
        }
    }

    private Representer getRepresenter() {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        dumperOptions.setPrettyFlow(true);
        dumperOptions.setAllowReadOnlyProperties(true);

        Representer representer = new Representer(dumperOptions);
        representer.getPropertyUtils().setSkipMissingProperties(true);

        return representer;
    }
}