package com.thorrism.sectionedrecyclerdemo.data.component;

import com.thorrism.sectionedrecyclerdemo.data.module.RxBusModule;
import com.thorrism.sectionedrecyclerdemo.service.ContactManager;
import com.thorrism.sectionedrecyclerdemo.data.module.ContactManagerModule;
import com.thorrism.sectionedrecyclerdemo.service.reactive.RxBus;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by lcrawford on 10/16/16.
 */
@Singleton
@Component(modules = {ContactManagerModule.class, RxBusModule.class})
public interface ContactManagerComponent {
    ContactManager provideContactManager();
    RxBus provideRxBus();
}
