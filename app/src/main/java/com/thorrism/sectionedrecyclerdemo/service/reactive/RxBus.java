package com.thorrism.sectionedrecyclerdemo.service.reactive;

import com.thorrism.sectionedrecyclerdemo.events.BaseEvent;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

/**
 * Simple event bus to allow a publisher/subscriber communication model
 * designed with a reactive approach using RxJava.
 *
 * Created by lcrawford on 10/16/16.
 */
public class RxBus {

    public RxBus(){}

    private final PublishSubject<BaseEvent> bus = PublishSubject.create();

    public void publish(BaseEvent o) {
        bus.onNext(o);
    }

    public <T extends BaseEvent> Subscription subscribe(final Class<T> clazz, Action1<T> func){
        return toObserverable()
                .filter(new Func1<BaseEvent, Boolean>() {
                    @Override
                    public Boolean call(BaseEvent o) {
                        return o.getClass().equals(clazz);
                    }
                })
                .map(new Func1<BaseEvent, T>() {
                    @Override
                    public T call(BaseEvent o) {
                        return (T) o;
                    }
                })
                .subscribe(func);
    }

    public Observable<BaseEvent> toObserverable() {
        return bus.observeOn(AndroidSchedulers.mainThread());
    }
}
