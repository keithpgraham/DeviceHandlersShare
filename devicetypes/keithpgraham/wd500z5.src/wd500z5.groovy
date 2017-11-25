/**
 *  WD500Z5
 *
 *  Copyright 2017 Keith
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
	definition (name: "WD500Z5", namespace: "keithpgraham", author: "Keith") {
		capability "Actuator"
		capability "Button"
		capability "Configuration"
		capability "Holdable Button"
		capability "Indicator"
		capability "Light"
		capability "Momentary"
		capability "Polling"
		capability "Refresh"
		capability "Switch"
		capability "Switch Level"
	}


	simulator {
		// TODO: define status and reply messages here
	}

	tiles {
		// TODO: define your main and details tiles here
	}
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
	// TODO: handle 'button' attribute
	// TODO: handle 'numberOfButtons' attribute
	// TODO: handle 'button' attribute
	// TODO: handle 'numberOfButtons' attribute
	// TODO: handle 'indicatorStatus' attribute
	// TODO: handle 'switch' attribute
	// TODO: handle 'switch' attribute
	// TODO: handle 'level' attribute

}

// handle commands
def configure() {
	log.debug "Executing 'configure'"
	// TODO: handle 'configure' command
}

def indicatorWhenOn() {
	log.debug "Executing 'indicatorWhenOn'"
	// TODO: handle 'indicatorWhenOn' command
}

def indicatorWhenOff() {
	log.debug "Executing 'indicatorWhenOff'"
	// TODO: handle 'indicatorWhenOff' command
}

def indicatorNever() {
	log.debug "Executing 'indicatorNever'"
	// TODO: handle 'indicatorNever' command
}

def off() {
	log.debug "Executing 'off'"
	// TODO: handle 'off' command
}

def on() {
	log.debug "Executing 'on'"
	// TODO: handle 'on' command
}

def push() {
	log.debug "Executing 'push'"
	// TODO: handle 'push' command
}

def poll() {
	log.debug "Executing 'poll'"
	// TODO: handle 'poll' command
}

def refresh() {
	log.debug "Executing 'refresh'"
	// TODO: handle 'refresh' command
}

def on() {
	log.debug "Executing 'on'"
	// TODO: handle 'on' command
}

def off() {
	log.debug "Executing 'off'"
	// TODO: handle 'off' command
}

def setLevel() {
	log.debug "Executing 'setLevel'"
	// TODO: handle 'setLevel' command
}