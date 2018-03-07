package com.example.administrator.sampleapplication.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.sampleapplication.R;
import com.example.administrator.sampleapplication.adpters.ReCyclerViewAdapters;
import com.example.administrator.sampleapplication.datamodel.Device;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 3/6/2018.
 * MVP Architecture Used here
 * 1. View for Rendering View and basic Validation
 * 2. Model for do Business Logic and Network Request
 * 3. ViewPresenter  used for communicate with "View from Model"
 * 4. ModelPresenter used for Communicate with "Model from View"
 */

public class DeviceListFragment extends Fragment implements IViewPresenter {
    public static String TAG = "DeviceListFragment";


    private IModelPresenter modelPresenter;

    // Initialize view Here
    @BindView(R.id.deviceListView)
    RecyclerView recyclerView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.emptyView)
    TextView emptyView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize model
        modelPresenter = new DeviceModel(getActivity(), DeviceListFragment.this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_devicelist, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar.setVisibility(View.VISIBLE);

        //start parser for parsing Json File
        modelPresenter.startParsing();
    }

    /**
     * @param reCyclerViewAdapters Choose the Layout manager and get the instance and set to RecyclerView.
     *                             set the adapter in RecyclerView
     *                             Make the ProgressBar InVisible after Set the adapter
     */
    public void updateUI(ReCyclerViewAdapters reCyclerViewAdapters) {
        emptyView.setVisibility(View.GONE);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(reCyclerViewAdapters);
        runLayoutAnimation(recyclerView);
        hideProgress();
    }


    /**
     * @param device : get the Device Class from Clicked Position
     *               change the Snackbar color based on app theme
     */
    @Override
    public void showClickedItem(Device device) {
        Snackbar snackbar = Snackbar
                .make(recyclerView, device.getName(), Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        snackbar.show();
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void updateEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        emptyView.setText(getActivity().getResources().getString(R.string.errorData));
    }
    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }
}
