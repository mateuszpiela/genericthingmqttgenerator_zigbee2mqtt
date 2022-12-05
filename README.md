# OpenHAB3 - Generic MQTT Topic Thing Generator From zigbee2mqtt
# Warning: This is alpha version it contain's some bugs

# Known issues
1. Produced YAML have extra properties that OpenHAB doesn't like
2. Produced YAML is quoted some keys like ON,OFF

# Compile
1. mvn clean
2. mvn install

# Required args
bridgeuid - Bridge MQTT UID from OpenHAB
host - MQTT Server Host

# Used libraries
Eclipse PAHO for MQTT Client 
Jackson for JSON Processing
SnakeYAML for YAML Generating
Apache Commons CLI for CLI Arguments Parsing