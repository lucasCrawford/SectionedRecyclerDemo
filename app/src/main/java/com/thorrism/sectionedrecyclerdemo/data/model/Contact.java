package com.thorrism.sectionedrecyclerdemo.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by lcrawford on 10/16/16.
 */

public class Contact {
    private String name;
    private String number;
    private String type;
    private String id;

    public Contact(){}

    public Contact(Parcel in){
        name = in.readString();
        number = in.readString();
        type = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Contact{name='" + name + "', number='"+ number + "', type='" + type + "'}";
    }
}
