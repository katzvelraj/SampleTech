package com.example.administrator.sampleapplication.utils;

import android.content.Context;

import com.example.administrator.sampleapplication.R;
import com.example.administrator.sampleapplication.datamodel.Device;
import com.example.administrator.sampleapplication.datamodel.DeviceList;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 3/1/2018.
 * <p>
 * get the Json file from Asset Folder and do the Parsing
 */

public class Parser {
    private Context mContext;

    public Parser(Context context) {
        this.mContext = context;
    }


    //get json file from asset folder

    /**
     * @return Json String
     * get the Json file from Asset Folder
     * Convert the file to Json String
     * return Json String
     */
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = mContext.getAssets().open("devices.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    // doing manual parsing here and return result

    /**
     * @return : List<Device> form Json Parsing
     * Get Json String and convert to Json Object
     * Do the Manual Parsing
     * Create the Model Class and Assign the Value to model class from parsing
     * Send Back to Caller
     */
    public List<Device> doParsing() {

        List<Device> deviceList = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray jsonArray = obj.getJSONArray(mContext.getResources().getString(R.string.parser_devices));


            for (int i = 0; i < jsonArray.length(); i++) {
                Device device = new Device();
                JSONObject jo_inside = jsonArray.getJSONObject(i);

                String deviceType = jo_inside.getString(mContext.getResources().getString(R.string.parser_deviceType));
                String model = jo_inside.getString(mContext.getResources().getString(R.string.parser_model));
                String name = jo_inside.getString(mContext.getResources().getString(R.string.parser_name));

                device.setDeviceType(deviceType);
                device.setModel(model);
                device.setName(name);

                deviceList.add(device);

            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return deviceList;
    }


    /**
     * @return :List<Device>
     * get the Json String From Asset File Folder
     * Auto Parsing Using Gson send the result back to caller
     */
    public List<Device> doAutoParsing() {
        List<Device> deviceList = new ArrayList<>();
        try {
            String jsonString = loadJSONFromAsset();
            Gson gson = new Gson();
            DeviceList list = gson.fromJson(jsonString, DeviceList.class);
            deviceList = list.getDevices();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return deviceList;
    }


}
