/**
 *  Copyright 2015 SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
metadata {
	definition (name: "WA00Z", namespace: "smartthings", author: "SmartThings") {
		capability "Actuator"
		capability "Switch"
		capability "Switch Level"
		capability "Refresh"
		capability "Sensor"
        capability "Button"
        capability "Holdable Button"

		fingerprint deviceId: "0x"
		fingerprint deviceId: "0x3101"  // for z-wave certification, can remove these when sub-meters/window-coverings are supported
		fingerprint deviceId: "0x3101", inClusters: "0x86,0x32"
		fingerprint deviceId: "0x09", inClusters: "0x86,0x72,0x26,0x5b"
		fingerprint deviceId: "0x0805", inClusters: "0x47,0x86,0x72,0x5b"
	}

/*	simulator {
		status "on":  "command: 2003, payload: FF"
		status "off": "command: 2003, payload: 00"
	}
*/

	tiles {
		standardTile("switch", "device.switch", width: 2, height: 2, canChangeIcon: true) {
			state "on", label: '${name}', action: "switch.off", icon: "st.unknown.zwave.device", backgroundColor: "#00A0DC"
			state "off", label: '${name}', action: "switch.on", icon: "st.unknown.zwave.device", backgroundColor: "#ffffff"
		}
		standardTile("switchOn", "device.switch", inactiveLabel: false, decoration: "flat") {
			state "on", label:'on', action:"switch.on", icon:"st.switches.switch.on"
		}
		standardTile("switchOff", "device.switch", inactiveLabel: false, decoration: "flat") {
			state "off", label:'off', action:"switch.off", icon:"st.switches.switch.off"
		}

		main "switch"
		details (["switch", "switchOn", "switchOff"])
	}
}

def parse(String description) {
	def result = []
	if (description.startsWith("Err")) {
	    result = createEvent(descriptionText:description, isStateChange:true)
	} else {
		def cmd = zwave.parse(description, [0x20: 1, 0x84: 1, 0x98: 1, 0x56: 1, 0x60: 3])
		if (cmd) {
			result += zwaveEvent(cmd)
            //result = createEvent (descriptionText: description, isStateChange:true)
		}
	}
	return result
}

def buttonEvent(button, pushed) {
	button = button as Integer
	//String childDni = "${device.deviceNetworkId}/${button}"
	//def child = childDevices.find{it.deviceNetworkId == childDni}
	//if (!child) {
	//	log.error "Child device $childDni not found"
	//}
	if (pushed) {
		//child?.sendEvent(name: "button", value: "held", data: [buttonNumber: 1], descriptionText: "$child.displayName was held", isStateChange: true)
		createEvent(name: "button", value: "pushed", data: [buttonNumber: button], descriptionText:description, isStateChange: true)
	} /*else {
		//child?.sendEvent(name: "button", value: "pushed", data: [buttonNumber: 1], descriptionText: "$child.displayName was pushed", isStateChange: true)
		createEvent(name: "button", value: "pushed", data: [buttonNumber: button], descriptionText: "$device.displayName button $button was pushed", isStateChange: true)
	}*/
    return button
}

/*def buttonEvent(pushed) {
	if ($result == "case1") {
		result = sendEvent(descriptionText1:"case1 was held", isStateChange: true)
		result = createEvent(descriptionText: "This is a test", isStateChange: true)
	}
}*/

def zwaveEvent(physicalgraph.zwave.commands.centralscenev1.CentralSceneNotification cmd) {
	pushed = (cmd.sceneNumber == 1) as Integer
	buttonEvent(pushed)
}

/*def eventHandler(physicalgraph.zwave.commands.centralscenev1.CentralSceneNotification cmd) {
	def buttonPush = []
    if (cmd.sceneNumber == 2) {
		if (cmd.keyAttributes == 0) {
		createEvent(value: "case 1", descriptionText: "case 1", isStateChange = true)
        } else if (cmd.keyAttributes == 2) {
          createEvent(value: 5)
          } else (cmd.keyAttributes == 3) {
          createEvent(value: 6)
        }
	} else if (cmd.sceneNumber == 1) {
    	if (cmd.keyAttributes == 0) {
		createEvent(value: 1)
        } else if (cmd.keyAttributes == 2) {
          createEvent(value: 2)
          } else (cmd.keyAttributes == 3) {
          createEvent(value: 3)
        }
	}
}*/

def installed() {
	if (zwaveInfo.zw && zwaveInfo.zw.cc?.contains("84")) {
		response(zwave.wakeUpV1.wakeUpNoMoreInformation())
	}
}

def zwaveEvent(physicalgraph.zwave.commands.wakeupv1.WakeUpNotification cmd) {
	[ createEvent(descriptionText: "${device.displayName} woke up"),
	  response(zwave.wakeUpV1.wakeUpNoMoreInformation()) ]
}

def zwaveEvent(physicalgraph.zwave.Command cmd) {
	createEvent(descriptionText: "$device.displayName: $cmd", isStateChange: true)
}

/*def initialize() {
	sendEvent(name: "numberOfButtons", value: 1)
	sendEvent(name: "DeviceWatch-Enroll", value: JsonOutput.toJson([protocol: "zwave", scheme:"untracked"]), displayed: true)
}*/

/*def installed() {
	initialize()
	createChildDevices()
}*/

def updated() {
	initialize()
	if (!childDevices) {
		createChildDevices()
	}
	else if (device.label != state.oldLabel) {
		childDevices.each {
			def segs = it.deviceNetworkId.split("/")
			def newLabel = "${device.displayName} button ${segs[-1]}"
			it.setLabel(newLabel)
		}
		state.oldLabel = device.label
	}
}

def initialize() {
	sendEvent(name: "numberOfButtons", value: 6)
	//sendEvent(name: "DeviceWatch-Enroll", value: JsonOutput.toJson([protocol: "zwave", scheme:"untracked"]), displayed: false)
}

/*private void createChildDevices() {
	state.oldLabel = device.label
	for (i in 1..4) {
		addChildDevice("Child Button", "${device.deviceNetworkId}/${i}", null,
				[completedSetup: true, label: "${device.displayName} button ${i}",
				 isComponent: true, componentName: "button$i", componentLabel: "Button $i"])
	}
}*/