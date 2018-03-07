package com.example.administrator.sampleapplication.ui;

import com.example.administrator.sampleapplication.adpters.ReCyclerViewAdapters;
import com.example.administrator.sampleapplication.datamodel.Device;

/**
 * Created by Administrator on 3/6/2018.
 * <p>
 * View Presenter communicate with View from Model
 * Update adapter, hideProgress etc...
 */

public interface IViewPresenter {
    void updateUI(ReCyclerViewAdapters cyclerViewAdapters);

    void showClickedItem(Device device);

    void hideProgress();

    void updateEmptyView();


}
