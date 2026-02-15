package com.fs.datamodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Product(
        String title, double price, String description, String category, Rating rating) {}

