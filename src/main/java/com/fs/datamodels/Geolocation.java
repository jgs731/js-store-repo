package com.fs.datamodels;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Geolocation(
        String lat,
        @JsonProperty("long")
        String lng
) { }
