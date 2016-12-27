package com.thorrism.sectionedrecyclerdemo.service.reactive;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by lcrawford on 12/26/16.
 */
public abstract class SubscriberFragment extends Fragment implements SubscriberComponent {

    protected CompositeSubscription mSubscriptions = new CompositeSubscription();

    protected CompositeSubscription getSubscriptions() {
        return mSubscriptions;
    }

    protected void setSubscriptions(CompositeSubscription compositeSubscription) {
        mSubscriptions = compositeSubscription;
    }

    /**
     * Unsubscribe the current {@link Fragment}'s {@link CompositeSubscription}
     * if necessary.
     */
    @Override
    public void removeSubscriptions() {
        if (mSubscriptions != null && !mSubscriptions.isUnsubscribed()) {
            mSubscriptions.unsubscribe();
        }
    }

    /**
     * Any subclass shall implement the adding of proper subscriptions needed
     * for the {@link Fragment}.
     */
    public abstract void addSubscriptions();

    /**
     * Ensure we properly create the {@link CompositeSubscription} or recreate it
     * if it was previously unsubscribed.
     */
    @Override
    public void initializeSubscriptions() {
        if (mSubscriptions == null || mSubscriptions.isUnsubscribed()) {
            mSubscriptions = new CompositeSubscription();
        }
        addSubscriptions();
    }

    /**
     * Whenever the Fragment is destroyed, unsubscribe from all current {@link rx.Subscription}s.
     * <p>
     * Instead of stop, we choose to remove when the fragment is destroyed. This is because a fragment
     * might have many different moments of start/stop as part of UI flow.
     * <p>
     * It is up to the subclass to handle removing subscriptions manually if they need it removed in a
     * specific way.
     */
    @Override
    public void onDestroy() {
        removeSubscriptions();
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeSubscriptions();
    }
}