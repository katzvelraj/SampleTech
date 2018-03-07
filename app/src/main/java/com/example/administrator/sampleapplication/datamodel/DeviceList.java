package com.example.administrator.sampleapplication.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 3/6/2018.
 */

public class DeviceList {

    @SerializedName("devices")
    @Expose
    private List<Device> devices = null;

    /**
     * No args constructor for use in serialization
     */
    public DeviceList() {
    }

    /**
     * @param devices
     */
    public DeviceList(List<Device> devices) {
        super();
        this.devices = devices;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}
