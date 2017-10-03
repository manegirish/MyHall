/*
 * Copyright (c) 2016.
 * All Right Reserved  Girish D. Mane (girishmane8692@gmail.com)
 *
 */

package com.technoindians.phonebook;

import com.google.gson.annotations.SerializedName;
import com.technoindians.variales.Constants;

/**
 * @author Girish D M(girishmane8692@gmail.com)
 *         Created on 28/10/16.
 *         Last Modified on 28/10/16.
 */

public class Member_ {

    @SerializedName(Constants.ID)
    private long id;

    @SerializedName(Constants.NAME)
    private String name;

    @SerializedName(Constants.EMAIL)
    private String email;

    @SerializedName("primary_phone")
    private String primary_phone;

    @SerializedName("secondary_phone")
    private String secondary_phone;

    @SerializedName(Constants.STATUS)
    private int status;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPrimary_phone(String primary_phone) {
        this.primary_phone = primary_phone;
    }

    public void setSecondary_phone(String secondary_phone) {
        this.secondary_phone = secondary_phone;
    }

    public int getStatus() {
        return status;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPrimary_phone() {
        return primary_phone;
    }

    public String getSecondary_phone() {
        return secondary_phone;
    }
}
