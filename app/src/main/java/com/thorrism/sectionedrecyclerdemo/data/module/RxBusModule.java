package com.thorrism.sectionedrecyclerdemo.data.module;

import com.thorrism.sectionedrecyclerdemo.service.reactive.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lcrawford on 10/16/16.
 */
@Module
public class RxBusModule {

    @Provides
    @Singleton
    public RxBus provideRxBus(){
        return new RxBus();
    }
}
