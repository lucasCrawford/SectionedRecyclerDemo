package com.thorrism.sectionedrecyclerdemo.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.thorrism.sectionedrecyclerdemo.events.ContactPermissionEvent;
import com.thorrism.sectionedrecyclerdemo.events.ContactSelectedEvent;
import com.thorrism.sectionedrecyclerdemo.service.reactive.RxBus;
import com.thorrism.sectionedrecyclerdemo.service.reactive.SubscriberFragment;
import com.thorrism.sectionedrecyclerdemo.view.DividerItemDecoration;
import com.thorrism.sectionedrecyclerdemo.view.contracts.ContactsContract;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;
import xyz.danoz.recyclerviewfastscroller.sectionindicator.title.SectionTitleIndicator;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

/**
 * Fragment to contain the contacts data.
 *
 * Created by lcrawford on 12/26/16.
 */
public class ContactsFragment extends SubscriberFragment implements LoaderManager.LoaderCallbacks<Cursor>,
        ContactsContract.ContactsView {

    @BindView(R.id.recycler_contacts)
    RecyclerView contactsRecycler;

    @BindView(R.id.fast_scroller)
    VerticalRecyclerViewFastScroller fastScroller;

    @BindView(R.id.fast_scroller_section_title_indicator)
    SectionTitleIndicator<SimpleSectionRecyclerAdapter.Section> contactSectionTitleIndicator;

    @Inject
    ContactsPresenterImpl contactsPresenter;

    private ContactAdapter contactAdapter;
    private RxBus rxBus;
    private boolean canShowContacts = false;

    // Loaded id to load the contacts
    private static final int LOADER_ID = 1337;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = getActivity();
        canShowContacts = PermissionUtils.checkPermission(activity, Manifest.permission.READ_CONTACTS);

        if (!canShowContacts) {
            rxBus.publish(new ContactPermissionEvent(false));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.component_contact_recycler, container, false);
        ButterKnife.bind(this, v);

        // Initialize the fragments data
        initFragment();

        return v;
    }

    @Override
    public void addSubscriptions() {

        // Inject dependencies. Gives the contacts presenter the necessary dependencies
        DaggerContactComponent.builder()
                .contactManagerComponent(SectionedRecyclerApp.getApplication(getActivity()).getContactManagerComponent())
                .contactsViewModule(new ContactsViewModule(this))
                .build()
                .inject(this);

        rxBus = SectionedRecyclerApp.getApplication(getActivity())
                .getRxBus();

        mSubscriptions.add(rxBus.subscribe(ContactSelectedEvent.class, new Action1<ContactSelectedEvent>() {
            @Override
            public void call(ContactSelectedEvent contactSelectedEvent) {
                Toast.makeText(getActivity(), contactSelectedEvent.getSelectedContact().getName(), Toast.LENGTH_LONG)
                        .show();
            }
        }));
    }

    @Override
    public void onContactsReady(List<Contact> contacts) {
        Activity activity = getActivity();

        // Add fast scroller and section indicator
        contactAdapter = new ContactAdapter(activity, contacts, rxBus);

        // Create the sections needed for the loaded contacts
        List<SimpleSectionRecyclerAdapter.Section> sections = contactsPresenter.createSections(contactAdapter.getSortedList());

        // Create the wrapping adapter
        SimpleSectionRecyclerAdapter wrappedContactsAdapter = new IndexedSimpleSectionAdapter(activity,
                R.layout.item_section, R.id.section_text, contactAdapter);
        wrappedContactsAdapter.setSections(sections.toArray(new SimpleSectionRecyclerAdapter.Section[sections.size()]), false);

        // Set adapter and add custom item decoration
        contactsRecycler.setAdapter(wrappedContactsAdapter);
        contactsRecycler.addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                ContactUtils.PROJECTION,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        contactsPresenter.loadContacts(data, getActivity());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // NO-OP
    }

    private void initFragment(){

        // Initialize recycler
        initRecycler();

        // Initialize the loader manager
        Activity activity = getActivity();

        // Only load contacts if we have permission to load them
        if (canShowContacts) {
            ((AppCompatActivity)activity).getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        }

    }

    private void initRecycler(){
        LinearLayoutManager lm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        contactsRecycler.setLayoutManager(lm); // Connect the scroller to the recycler (to let the recycler scroll the scroller's handle)
        fastScroller.setRecyclerView(contactsRecycler);
        contactsRecycler.addOnScrollListener(fastScroller.getOnScrollListener());
        fastScroller.setSectionIndicator(contactSectionTitleIndicator);
    }
}
