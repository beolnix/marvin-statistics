package com.beolnix.marvin.statistics.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("User details DTO")
public class UserDTO {

    @ApiModelProperty(value = "Name of the user", required = true)
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
