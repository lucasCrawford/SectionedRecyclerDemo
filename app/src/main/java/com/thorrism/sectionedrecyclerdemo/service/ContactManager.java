package com.thorrism.sectionedrecyclerdemo.service;

import android.content.Context;
import android.database.Cursor;

import com.thorrism.sectionedrecyclerdemo.data.model.Contact;

import java.util.List;

/**
 * Created by lcrawford on 10/16/16.
 */

public interface ContactManager {

    /**
     * Return current working alphabet.
     *
     * <p>
     *     The intention here is that multiple languages can be supported, and the
     *     appropriate implementation returns the proper values.
     * </p>
     *
     * @return
     */
    char[] getAlphabet();

    /**
     * Return the working symbol for a numerical section letter.
     *
     * <p>
     *     This character will be used to categorize the section of contacts whose first
     *     letter is either the '#' character or a number.
     * </p>
     * @return
     */
    char getNumericalLetter();

    /**
     * Read the results from a {@link Cursor} containing the user's currently saved
     * contacts.
     *
     * <p>
     *     This cursor can be assumed unordered, and the result can also be assumed unordered.
     * </p>
     *
     * @param context  Activity or Application {@link Context} needed for parsing label types of a contact
     * @param cursor  {@link Cursor} containing a pointer to the user's contacts in their phones database
     *                              given to us from a {@link android.content.ContentProvider}
     * @return
     */
    List<Contact> readContacts(Context context, Cursor cursor);
}
