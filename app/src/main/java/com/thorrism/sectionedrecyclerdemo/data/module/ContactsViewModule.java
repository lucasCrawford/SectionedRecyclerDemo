package com.thorrism.sectionedrecyclerdemo.data.module;

import com.thorrism.sectionedrecyclerdemo.data.component.ActivityScope;

import static com.thorrism.sectionedrecyclerdemo.view.contracts.ContactsContract.ContactsView;

import dagger.Module;
import dagger.Provides;

/**
 * Module that provides the {@link ContactsView}.
 *
 * Created by lcrawford on 10/16/16.
 */
@Module
public class ContactsViewModule {

    private ContactsView contactsView;

    public ContactsViewModule(ContactsView contactsView){
        this.contactsView = contactsView;
    }

    @Provides
    @ActivityScope
    public ContactsView provideContactsView(){
        return this.contactsView;
    }

}
