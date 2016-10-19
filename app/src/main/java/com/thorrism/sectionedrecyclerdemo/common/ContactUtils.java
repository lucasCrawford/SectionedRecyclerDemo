package com.thorrism.sectionedrecyclerdemo.common;

import android.provider.ContactsContract;

/**
 * Utility class for parsing a user's native contacts
 * list.
 *
 * Created by lcrawford on 7/3/16.
 */
public class ContactUtils {

    private static final String TAG = "ContactUtils";

    /* Projection over only the things we care about*/
    public static final String[] PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.TYPE,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };

    // Section header for numerical contacts
    public static final char NUMERICAL_LETTER = '#';

    // Default alphabet used when another alphabet isn't present
    public static final char[] ENGLISH_ALPHABET = new char[]{NUMERICAL_LETTER,
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

}
