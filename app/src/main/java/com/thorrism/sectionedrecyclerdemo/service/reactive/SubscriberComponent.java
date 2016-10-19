package com.thorrism.sectionedrecyclerdemo.service.reactive;

/**
 * Interface to control an Android Component that needs to keep track
 * of subscriptions and manage the lifecycle of those subscriptions.
 *
 * Created by lcrawford on 10/17/16.
 */
public interface SubscriberComponent {

    /**
     * Add all subscriptions for the component when the component is ready
     * to subscribe.
     *
     */
    void addSubscriptions();

    /**
     * Initialize the subscriptions when the component implementation is ready.
     */
    void initializeSubscriptions();

    /**
     * Clear all references to the components collection of
     * subscriptions
     *
     * <p>
     *     It is important to call this when the component is ready to
     *     dereference all subscriptions (unsubscribe). Otherwise, memory
     *     leaks are created.
     * </p>
     */
    void removeSubscriptions();

}
