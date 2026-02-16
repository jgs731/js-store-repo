package com.fs.datamodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Cart(Integer userId, Date date, List<Products> products) {
}
