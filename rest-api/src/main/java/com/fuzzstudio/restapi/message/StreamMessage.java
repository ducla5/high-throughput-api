package com.fuzzstudio.restapi.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fuzzstudio.restapi.enums.Command;

public class StreamMessage {

    @JsonProperty("command")
    public Command command;

    @JsonProperty("id")
    public String id;

    public StreamMessage(@JsonProperty("command") Command command,  @JsonProperty("id") String id) {
        this.command = command;
        this.id = id;
    }
}
