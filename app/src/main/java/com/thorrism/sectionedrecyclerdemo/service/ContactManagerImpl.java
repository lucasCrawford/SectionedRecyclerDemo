package com.thorrism.sectionedrecyclerdemo.service;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.thorrism.sectionedrecyclerdemo.data.model.Contact;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.thorrism.sectionedrecyclerdemo.common.ContactUtils.ENGLISH_ALPHABET;
import static com.thorrism.sectionedrecyclerdemo.common.ContactUtils.NUMERICAL_LETTER;
import static com.thorrism.sectionedrecyclerdemo.common.ContactUtils.PROJECTION;

/**
 * Created by lcrawford on 10/16/16.
 */
public class ContactManagerImpl implements ContactManager {

    private static final String TAG = "ContactManagerImpl";

    private char[] alphabet;

    public ContactManagerImpl(char[] alphabet){
        this.alphabet = alphabet;
    }

    @Override
    public char[] getAlphabet() {
        return alphabet == null? ENGLISH_ALPHABET : alphabet;
    }

    @Override
    public char getNumericalLetter() {
        return NUMERICAL_LETTER;
    }

    @Override
    public List<Contact> readContacts(Context context, Cursor cursor) {
        ArrayList<Contact> models = new ArrayList<>();
        Contact model;
        if(cursor != null){

            // indices for the cursor's columns for each projection we care about
            final int idIdx = cursor.getColumnIndex(PROJECTION[0]);
            final int nameIdx = cursor.getColumnIndex(PROJECTION[2]);
            final int numberIdx = cursor.getColumnIndex(PROJECTION[3]);
            final int typeIdx = cursor.getColumnIndexOrThrow(PROJECTION[1]);

            if(cursor.moveToFirst()){

                /* Look through all the contacts */
                while(cursor.moveToNext()){
                    String name = cursor.getString(nameIdx);
                    String number = cursor.getString(numberIdx);
                    String id = cursor.getString(idIdx);
                    model = new Contact();
                    model.setId(id);
                    model.setName(name);
                    model.setNumber(number);
                    model.setType(ContactsContract.CommonDataKinds.Phone.getTypeLabel(context.getResources(), cursor.getInt(typeIdx), StringUtils.EMPTY).toString());
                    models.add(model);
                }
            }else{
                Log.d(TAG, "Problem reading the cursor. There was no contents!");
                //TODO: Possibly handle the issue by throwing a custom exception. For now, we handle by treating empty
                // list returned.
            }
        }
        return models;
    }
}
