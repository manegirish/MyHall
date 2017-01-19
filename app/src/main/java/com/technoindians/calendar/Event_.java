/*
 * Copyright (c) 2016.
 * All Right Reserved  Girish D. Mane (girishmane8692@gmail.com)
 *
 */

package com.technoindians.calendar;

/**
 * @author Girish D M(girishmane8692@gmail.com)
 *         Created on 28/10/16.
 *         Last Modified on 28/10/16.
 */

public class Event_ {

    String id;
    String name;
    String title;
    String description;
    String time;
    String date;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getId() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getTime() {
        return this.time;
    }

    public String getDate() {
        return this.date;
    }

}
