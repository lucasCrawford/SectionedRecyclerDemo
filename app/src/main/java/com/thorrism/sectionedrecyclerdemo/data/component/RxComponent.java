package com.thorrism.sectionedrecyclerdemo.data.component;

import com.thorrism.sectionedrecyclerdemo.SectionedRecyclerApp;
import com.thorrism.sectionedrecyclerdemo.data.module.RxBusModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by lcrawford on 12/26/16.
 */
@Singleton
@Component(modules = {RxBusModule.class})
public interface RxComponent {

    void inject(SectionedRecyclerApp application);
}
