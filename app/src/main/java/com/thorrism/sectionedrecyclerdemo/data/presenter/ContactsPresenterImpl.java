package com.thorrism.sectionedrecyclerdemo.data.presenter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.util.SortedList;

import com.thorrism.sectionedrecyclerdemo.adapter.SimpleSectionRecyclerAdapter;
import com.thorrism.sectionedrecyclerdemo.service.ContactManager;
import com.thorrism.sectionedrecyclerdemo.data.model.Contact;
import com.thorrism.sectionedrecyclerdemo.view.contracts.ContactsContract.ContactsPresenter;
import com.thorrism.sectionedrecyclerdemo.view.contracts.ContactsContract.ContactsView;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import static com.thorrism.sectionedrecyclerdemo.common.ContactUtils.NUMERICAL_LETTER;

/**
 * Presenter in charge of the business logic around loading contacts and constructing
 * the appropriate objects needed by the {@link ContactsView}.
 *
 * Created by lcrawford on 10/16/16.
 */
public class ContactsPresenterImpl implements ContactsPresenter {
    private ContactsView view;
    private ContactManager contactManager;

    @Inject
    public ContactsPresenterImpl(ContactsView view, ContactManager contactManager){
        this.view = view;
        this.contactManager = contactManager;
    }

    @Override
    public void loadContacts(Cursor cursor, Context context) {
        List<Contact> contacts = this.contactManager.readContacts(context, cursor);
        this.view.onContactsReady(contacts);
    }


    /**
     * Helper method to create a list of {@link SimpleSectionRecyclerAdapter.Section}
     * objects for each letter found from the provided contacts.
     *
     * @param contacts  {@link SortedList <Contact>} containing current contacts
     * @return  sorted list of {@link SimpleSectionRecyclerAdapter.Section}
     *          objects.
     */
    @Override
    public List<SimpleSectionRecyclerAdapter.Section> createSections(SortedList<Contact> contacts){
        char[] currentAlphabet = contactManager.getAlphabet();
        List<SimpleSectionRecyclerAdapter.Section> sections = new ArrayList<>(currentAlphabet.length);

        // Populate the letter map with false for each letter, marking true when found
        Map<Character, Boolean> LETTER_MAP = new HashMap<>(currentAlphabet.length);
        for(char c : currentAlphabet){
            LETTER_MAP.put(c, false);
        }
        LETTER_MAP.put(contactManager.getNumericalLetter(), false);

        Contact contact;
        for(int i=0, size=contacts.size(); i<size; ++i){
            contact = contacts.get(i);
            char firstLetter = Character.toLowerCase(contact.getName().charAt(0));
            String firstLetterStr = Character.toString(firstLetter).toUpperCase();

            // If the letter is numerical, make it our numerical letter
            if(NumberUtils.isNumber(firstLetterStr)){
                firstLetter = NUMERICAL_LETTER;
            }

            // If the first letter hasn't been found yet, add a new section for it.
            if(!LETTER_MAP.get(firstLetter)){
                sections.add(new SimpleSectionRecyclerAdapter.Section(i, firstLetterStr));
                LETTER_MAP.put(firstLetter, true);
            }
        }
        return sections;
    }
}
