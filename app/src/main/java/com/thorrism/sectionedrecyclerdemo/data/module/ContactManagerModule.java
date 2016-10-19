package com.thorrism.sectionedrecyclerdemo.data.module;

import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.LocaleData;
import com.ibm.icu.util.ULocale;
import com.thorrism.sectionedrecyclerdemo.service.ContactManager;
import com.thorrism.sectionedrecyclerdemo.service.ContactManagerImpl;

import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module that provides the {@link ContactManager} implementation.
 *
 * <p>
 *     This module is designed to be a singleton across the application
 *     scope.
 * </p>
 *
 * Created by lcrawford on 10/16/16.
 */
@Module
public class ContactManagerModule {

    @Provides
    @Singleton
    public ContactManager provideContactManager(){
        UnicodeSet unicodeSet = LocaleData.getExemplarSet(new ULocale(Locale.getDefault().getLanguage()), LocaleData.ES_STANDARD);
        char[] alphabet = new char[unicodeSet.size()];
        for(int i=0; i<alphabet.length; ++i){
            alphabet[i] = (char) unicodeSet.charAt(i);
        }
        return new ContactManagerImpl(alphabet);
    }
}
