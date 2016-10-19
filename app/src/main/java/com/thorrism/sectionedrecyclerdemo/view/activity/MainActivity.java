package com.thorrism.sectionedrecyclerdemo.view.activity;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.thorrism.sectionedrecyclerdemo.R;
import com.thorrism.sectionedrecyclerdemo.SectionedRecyclerApp;
import com.thorrism.sectionedrecyclerdemo.adapter.ContactAdapter;
import com.thorrism.sectionedrecyclerdemo.adapter.IndexedSimpleSectionAdapter;
import com.thorrism.sectionedrecyclerdemo.adapter.SimpleSectionRecyclerAdapter;
import com.thorrism.sectionedrecyclerdemo.common.ContactUtils;
import com.thorrism.sectionedrecyclerdemo.common.PermissionUtils;
import com.thorrism.sectionedrecyclerdemo.data.component.DaggerContactComponent;
import com.thorrism.sectionedrecyclerdemo.data.model.Contact;
import com.thorrism.sectionedrecyclerdemo.data.module.ContactsViewModule;
import com.thorrism.sectionedrecyclerdemo.data.presenter.ContactsPresenterImpl;
import com.thorrism.sectionedrecyclerdemo.service.reactive.RxBus;
import com.thorrism.sectionedrecyclerdemo.view.DividerItemDecoration;
import com.thorrism.sectionedrecyclerdemo.view.contracts.ContactsContract.ContactsView;
import com.thorrism.sectionedrecyclerdemo.events.*;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;
import xyz.danoz.recyclerviewfastscroller.sectionindicator.title.SectionTitleIndicator;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

public class MainActivity extends SubscriberActivity implements LoaderManager.LoaderCallbacks<Cursor>, ContactsView {

    @BindView(R.id.recycler_contacts)
    RecyclerView contactsRecycler;

    @BindView(R.id.fast_scroller)
    VerticalRecyclerViewFastScroller fastScroller;

    @BindView(R.id.fast_scroller_section_title_indicator)
    SectionTitleIndicator<SimpleSectionRecyclerAdapter.Section> contactSectionTitleIndicator;

    @Inject
    ContactsPresenterImpl contactsPresenter;

    @Inject
    RxBus rxBus;

    private ContactAdapter contactAdapter;

    private static final int READ_CONTACTS_REQUEST = 1993;
    private static final int LOADER_ID = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Initialize the activity
        initActivity();
    }

    private void initRecycler(){
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        contactsRecycler.setLayoutManager(lm); // Connect the scroller to the recycler (to let the recycler scroll the scroller's handle)
        fastScroller.setRecyclerView(contactsRecycler);
        contactsRecycler.addOnScrollListener(fastScroller.getOnScrollListener());
        fastScroller.setSectionIndicator(contactSectionTitleIndicator);
    }

    /**
     * Initialize the activity by injecting the proper components and checking the proper privileges.
     *
     */
    private void initActivity(){

        // Inject dependencies. Gives the contacts presenter the necessary dependencies
        DaggerContactComponent.builder()
                .contactManagerComponent(SectionedRecyclerApp.getApplication(this).getContactManagerComponent())
                .contactsViewModule(new ContactsViewModule(this))
                .build()
                .inject(this);

        // Initialize recycler
        initRecycler();

        // Check permissions for reading contacts
        if(!PermissionUtils.checkPermission(this, android.Manifest.permission.READ_CONTACTS)){
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_CONTACTS},
                    READ_CONTACTS_REQUEST);
        }else{

            // Initialize the loader if permissions granted already
            getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        }
    }

    @Override
    public void onContactsReady(List<Contact> contacts) {

        // Add fast scroller and section indicator
        contactAdapter = new ContactAdapter(this, contacts, rxBus);

        // Create the sections needed for the loaded contacts
        List<SimpleSectionRecyclerAdapter.Section> sections = contactsPresenter.createSections(contactAdapter.getSortedList());

        // Create the wrapping adapter
        SimpleSectionRecyclerAdapter wrappedContactsAdapter = new IndexedSimpleSectionAdapter(this,
                R.layout.item_section, R.id.section_text, contactAdapter);
        wrappedContactsAdapter.setSections(sections.toArray(new SimpleSectionRecyclerAdapter.Section[sections.size()]), false);

        // Set adapter and add custom item decoration
        contactsRecycler.setAdapter(wrappedContactsAdapter);
        contactsRecycler.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                ContactUtils.PROJECTION,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        contactsPresenter.loadContacts(data, this);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // NO-OP
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case READ_CONTACTS_REQUEST:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Initialize the loader
                    getSupportLoaderManager().initLoader(LOADER_ID, null, this);
                }else{
                    //TODO: Provide a dialog or something to notify the user to allow reading on contacts.
                }
        }
    }

    @Override
    public void addSubscriptions() {
        mSubscriptions.add(rxBus.subscribe(ContactSelectedEvent.class, new Action1<ContactSelectedEvent>() {
            @Override
            public void call(ContactSelectedEvent contactSelectedEvent) {
                Toast.makeText(MainActivity.this, contactSelectedEvent.getSelectedContact().getName(), Toast.LENGTH_LONG)
                        .show();
            }
        }));
    }
}
