package com.thorrism.sectionedrecyclerdemo.view.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.thorrism.sectionedrecyclerdemo.R;
import com.thorrism.sectionedrecyclerdemo.SectionedRecyclerApp;
import com.thorrism.sectionedrecyclerdemo.common.PermissionUtils;
import com.thorrism.sectionedrecyclerdemo.events.ContactPermissionEvent;
import com.thorrism.sectionedrecyclerdemo.service.reactive.RxBus;
import com.thorrism.sectionedrecyclerdemo.service.reactive.SubscriberActivity;
import com.thorrism.sectionedrecyclerdemo.view.fragment.ContactAccessFragment;
import com.thorrism.sectionedrecyclerdemo.view.fragment.ContactsFragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

import static com.thorrism.sectionedrecyclerdemo.view.activity.MainActivity.FragmentState.CONTACTS;
import static com.thorrism.sectionedrecyclerdemo.view.activity.MainActivity.FragmentState.NEED_ACCESS;

public class MainActivity extends SubscriberActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private @FragmentState int currentState;

    public static final int READ_CONTACTS_REQUEST = 1993;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Initialize the activity
        initActivity();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case READ_CONTACTS_REQUEST:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showFragment(FragmentState.CONTACTS);
                    PermissionUtils.updateAskedPermission(this, Manifest.permission.READ_CONTACTS, false);
                }else{
                    showFragment(FragmentState.NEED_ACCESS);
                }
        }
    }

    @Override
    public void addSubscriptions() {
        RxBus rxBus = SectionedRecyclerApp.getApplication(this)
                .getRxBus();

        this.mSubscriptions.add(rxBus.subscribe(ContactPermissionEvent.class, new Action1<ContactPermissionEvent>() {
            @Override
            public void call(ContactPermissionEvent contactPermissionEvent) {
                if (!contactPermissionEvent.isContactsAccess()) {
                    showFragment(FragmentState.NEED_ACCESS);
                } else {
                    showFragment(FragmentState.CONTACTS);
                }
            }
        }));
    }

    @Override
    public void onResume(){
        super.onResume();

        boolean hasContactsPermission = PermissionUtils.checkPermission(this, Manifest.permission.READ_CONTACTS);
        if (currentState == NEED_ACCESS && hasContactsPermission) {
            showFragment(FragmentState.CONTACTS);
        }
    }

    /**
     * Initialize the activity by injecting the proper components and checking the proper privileges.
     */
    private void initActivity(){
        setSupportActionBar(toolbar);

        // Check permissions for reading contacts
        if(!PermissionUtils.checkPermission(this, Manifest.permission.READ_CONTACTS)){
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_CONTACTS},
                    READ_CONTACTS_REQUEST);
        }else {
            showFragment(FragmentState.CONTACTS);
        }
    }

    /**
     * Handle showing fragment based on the provided {@link FragmentState}.
     *
     * @param fragmentState  state for the activity which dictates which fragment
     *                       is shown.
     */
    private void showFragment(@FragmentState int fragmentState){
        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction();

        // Handle the requested fragment state
        switch (fragmentState) {
            case CONTACTS:
                ft.replace(R.id.fragment_layout, new ContactsFragment());
                break;
            case NEED_ACCESS:
                ft.replace(R.id.fragment_layout, new ContactAccessFragment());
                break;
        }

        // Use commitAllowingStateLoss() since we perform this after UI has
        // loaded, and permissions are requested.
        currentState = fragmentState;
        ft.commitAllowingStateLoss();
    }

    /**
     * Enumeration for handling fragment state of the Activity.
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({CONTACTS, NEED_ACCESS})
    public @interface FragmentState {
        int CONTACTS = 0;
        int NEED_ACCESS = 1;
    }

}
