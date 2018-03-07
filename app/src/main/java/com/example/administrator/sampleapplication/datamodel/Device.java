package com.example.administrator.sampleapplication.datamodel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by Administrator on 3/6/2018.
 */

public class Device {

    @SerializedName("deviceType")
    @Expose
    private String deviceType;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * No args constructor for use in serialization
     *
     */
    public Device() {
    }

    /**
     *
     * @param model
     * @param deviceType
     * @param name
     */
    public Device(String deviceType, String model, String name) {
        super();
        this.deviceType = deviceType;
        this.model = model;
        this.name = name;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
