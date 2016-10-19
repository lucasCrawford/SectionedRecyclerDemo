package com.thorrism.sectionedrecyclerdemo.events;

import com.thorrism.sectionedrecyclerdemo.data.model.Contact;

/**
 * Event in charge of being created when a contact has been selected
 * by the user.
 *
 * Created by lcrawford on 10/16/16.
 */
public class ContactSelectedEvent extends BaseEvent {

    private final Contact contact;

    public ContactSelectedEvent(Contact contact){
        this.contact = contact;
    }

    public Contact getSelectedContact(){
        return this.contact;
    }
}
