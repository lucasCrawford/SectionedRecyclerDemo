package com.thorrism.sectionedrecyclerdemo.service.reactive;

import android.support.v7.app.AppCompatActivity;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by lcrawford on 10/17/16.
 */
public abstract class SubscriberActivity extends AppCompatActivity implements SubscriberComponent {

    protected CompositeSubscription mSubscriptions = new CompositeSubscription();

    protected CompositeSubscription getSubscriptions(){
        return mSubscriptions;
    }

    protected void setSubscriptions(CompositeSubscription compositeSubscription){
        mSubscriptions = compositeSubscription;
    }

    /**
     * Unsubscribe the current {@link AppCompatActivity}'s {@link CompositeSubscription}
     * if necessary.
     */
    @Override
    public void removeSubscriptions(){
        if(mSubscriptions != null && !mSubscriptions.isUnsubscribed()){
            mSubscriptions.unsubscribe();
        }
    }

    /**
     * Any subclass shall implement the adding of proper subscriptions needed
     * for the {@link AppCompatActivity}.
     */
    public abstract void addSubscriptions();


    @Override
    public void initializeSubscriptions(){
        if(mSubscriptions == null || mSubscriptions.isUnsubscribed()){
            mSubscriptions = new CompositeSubscription();
        }
        addSubscriptions();
    }

    /**
     * Whenever the Activity is stopped, unsubscribe from all current {@link rx.Subscription}s.
     */
    @Override
    protected void onStop() {
        removeSubscriptions();
        super.onStop();
    }

    @Override
    protected void onResume(){
        initializeSubscriptions();
        super.onResume();
    }
}
