package com.fs.datamodels;

public record Address(String city, String street, String number, String zipcode,
                      Geolocation geolocation, String phone) {
}
