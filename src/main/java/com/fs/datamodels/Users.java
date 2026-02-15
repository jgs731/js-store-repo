package com.fs.datamodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Users(String email, String username, String password,
                    Name name, Address address) {
}
