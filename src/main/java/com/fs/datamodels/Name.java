package com.fs.datamodels;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Name(
        @JsonProperty("firstname")
        String firstName,
        @JsonProperty("lastname")
        String lastName
) { }
