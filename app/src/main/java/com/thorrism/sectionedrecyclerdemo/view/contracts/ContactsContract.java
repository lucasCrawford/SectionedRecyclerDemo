package com.thorrism.sectionedrecyclerdemo.view.contracts;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.util.SortedList;

import com.thorrism.sectionedrecyclerdemo.adapter.SimpleSectionRecyclerAdapter;
import com.thorrism.sectionedrecyclerdemo.data.model.Contact;

import java.util.List;

/**
 * Contract in charge of defining the view and presenter for dealing with
 * user's contacts.
 *
 * Created by lcrawford on 10/16/16.
 */
public class ContactsContract {

    public interface ContactsView {

        /**
         * Notify the {@link ContactsView} when the contacts are ready to be used
         * for view actions.
         *
         * @param contacts  List of {@link Contact}s ready to be displayed
         */
        void onContactsReady(List<Contact> contacts);
    }

    public interface ContactsPresenter {

        /**
         * Load the user's contacts from the database
         *
         * @param cursor  {@link Cursor} with access to filtered user's contacts
         * @param context  Activity or Application context associated with the view
         */
        void loadContacts(Cursor cursor, Context context);
        List<SimpleSectionRecyclerAdapter.Section> createSections(SortedList<Contact> contacts);
    }
}
