package com.thorrism.sectionedrecyclerdemo;

import android.app.Application;
import android.content.Context;

import com.thorrism.sectionedrecyclerdemo.data.component.ContactManagerComponent;
import com.thorrism.sectionedrecyclerdemo.data.component.DaggerContactManagerComponent;
import com.thorrism.sectionedrecyclerdemo.data.module.ContactManagerModule;
import com.thorrism.sectionedrecyclerdemo.data.module.RxBusModule;
import com.thorrism.sectionedrecyclerdemo.service.reactive.RxBus;

import javax.inject.Inject;

/**
 * Created by lcrawford on 10/16/16.
 */
public class SectionedRecyclerApp extends Application{

    private ContactManagerComponent contactManagerComponent;

    @Inject
    RxBus rxBus;

    @Override
    public void onCreate(){
        super.onCreate();

        // Create the application scoped component for accessing contacts
        this.contactManagerComponent = DaggerContactManagerComponent.builder()
                .contactManagerModule(new ContactManagerModule())
                .rxBusModule(new RxBusModule())
                .build();
    }

    public ContactManagerComponent getContactManagerComponent(){
        return this.contactManagerComponent;
    }

    public RxBus getRxBus(){
        return this.rxBus;
    }

    public static SectionedRecyclerApp getApplication(Context context){
        return (SectionedRecyclerApp) context.getApplicationContext();
    }
}
