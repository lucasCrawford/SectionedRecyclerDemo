package com.thorrism.sectionedrecyclerdemo.data.component;

import com.thorrism.sectionedrecyclerdemo.data.module.ContactManagerModule;
import com.thorrism.sectionedrecyclerdemo.service.ContactManager;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by lcrawford on 10/16/16.
 */
@Singleton
@Component(modules = {ContactManagerModule.class})
public interface ContactManagerComponent {
    ContactManager provideContactManager();
}
