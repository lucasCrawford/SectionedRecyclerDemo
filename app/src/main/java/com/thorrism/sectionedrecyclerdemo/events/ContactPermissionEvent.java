package com.thorrism.sectionedrecyclerdemo.events;

/**
 * Created by lcrawford on 12/26/16.
 */
public class ContactPermissionEvent extends BaseEvent {

    private final boolean contactsAccess;

    public ContactPermissionEvent(boolean contactsAccess){
        this.contactsAccess = contactsAccess;
    }

    public boolean isContactsAccess(){
        return this.contactsAccess;
    }
}
