package com.example.administrator.sampleapplication.ui;

import android.content.Context;
import android.os.Handler;
import android.view.View;

import com.example.administrator.sampleapplication.adpters.ReCyclerViewAdapters;
import com.example.administrator.sampleapplication.adpters.ReCyclerViewItemClickListener;
import com.example.administrator.sampleapplication.datamodel.Device;
import com.example.administrator.sampleapplication.utils.Parser;

import java.util.List;

/**
 * Created by Administrator on 3/6/2018.
 * <p>
 * Model class in MVP Architecture
 * Do the Json Parsing Using Separate Thread
 * get The Response from Parser
 * Create The Adapter based on the Result
 * Communicate the View To update The Adapter
 */

public class DeviceModel implements IModelPresenter, ReCyclerViewItemClickListener {

    //Initialize model,context, data, etc..
    private Context mContext;

    private IViewPresenter iViewPresenter;

    private List<Device> mDeviceList = null;

    private final Handler myHandler = new Handler();

    public DeviceModel(Context context, IViewPresenter viewPresenter) {
        this.mContext = context;
        this.iViewPresenter = viewPresenter;
    }

    /**
     * Start Parsing Json File Using  Worker Thread
     * get the Response From Parsing and Pass to Handler to Update Ui From Background Thread
     */
    public void startParsing() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Parser parser = new Parser(mContext);
                mDeviceList = parser.doParsing();
                // mDeviceList = parser.doAutoParsing();
                if (mDeviceList != null)
                    myHandler.post(updateRunnable);
                else {
                    iViewPresenter.updateEmptyView();
                    iViewPresenter.hideProgress();
                }

            }
        }).start();

    }


    /**
     * Create Runnable Object to Update Ui Using Handler
     */
    final Runnable updateRunnable = new Runnable() {
        public void run() {
            updateUI();
        }
    };


    /**
     * create Recycler Adapter and send back to UI to update.
     * Pass Context and Data and callBack of Click Listener
     */
    private void updateUI() {
        if (mDeviceList != null && mDeviceList.size() > 0) {
            ReCyclerViewAdapters cyclerViewAdapters = new ReCyclerViewAdapters(mContext, DeviceModel.this, mDeviceList);
            iViewPresenter.updateUI(cyclerViewAdapters);
        }
    }


    @Override
    public void onItemClickListener(View view, int adapterPosition) {
        Device device = mDeviceList.get(adapterPosition);
        iViewPresenter.showClickedItem(device);
    }

}
