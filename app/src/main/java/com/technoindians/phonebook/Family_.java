package com.technoindians.phonebook;

import com.google.gson.annotations.SerializedName;
import com.technoindians.variales.Constants;

/**
 * Created by GirishM on 03-10-2017.
 */

public class Family_ {

    @SerializedName(Constants.STATUS)
    private int status;

    @SerializedName(Constants.ID)
    private long id;

    @SerializedName(Constants.NAME)
    private String name;

    @SerializedName("created_by")
    private String created_by;

    @SerializedName("created_by_id")
    private long created_by_id;

    @SerializedName("total_members")
    private long total_members;

    @SerializedName("created_date")
    private long created_date;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public void setCreated_by_id(long created_by_id) {
        this.created_by_id = created_by_id;
    }

    public void setTotal_members(long total_members) {
        this.total_members = total_members;
    }

    public void setCreated_date(long created_date) {
        this.created_date = created_date;
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

    public String getCreated_by() {
        return created_by;
    }

    public long getCreated_by_id() {
        return created_by_id;
    }

    public long getCreated_date() {
        return created_date;
    }

    public long getTotal_members() {
        return total_members;
    }
}
