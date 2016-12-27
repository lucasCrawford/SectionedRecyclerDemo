package com.thorrism.sectionedrecyclerdemo.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.thorrism.sectionedrecyclerdemo.R;
import com.thorrism.sectionedrecyclerdemo.common.PermissionUtils;
import com.thorrism.sectionedrecyclerdemo.service.reactive.SubscriberFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.thorrism.sectionedrecyclerdemo.view.activity.MainActivity.READ_CONTACTS_REQUEST;

/**
 * Created by lcrawford on 12/26/16.
 */
public class ContactAccessFragment extends SubscriberFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_need_access, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @OnClick(R.id.text_turn_on)
    public void onEnablePermissionClick(){
        Activity activity = getActivity();

        if (PermissionUtils.hasAskedPermission(activity, Manifest.permission.READ_CONTACTS)) {
            Toast.makeText(activity, "Please enable permission under settings to read contacts", Toast.LENGTH_LONG)
                    .show();
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{android.Manifest.permission.READ_CONTACTS},
                    READ_CONTACTS_REQUEST);
            PermissionUtils.updateAskedPermission(activity, Manifest.permission.READ_CONTACTS, true);
        }
    }

    @Override
    public void addSubscriptions() {

    }
}
