package com.thorrism.sectionedrecyclerdemo.data.component;

import com.thorrism.sectionedrecyclerdemo.data.module.ContactsViewModule;
import com.thorrism.sectionedrecyclerdemo.view.activity.MainActivity;

import dagger.Component;

/**
 * Created by lcrawford on 10/16/16.
 */
@ActivityScope
@Component(dependencies = {ContactManagerComponent.class}, modules = {ContactsViewModule.class})
public interface ContactComponent {
    void inject(MainActivity mainActivity);
}
