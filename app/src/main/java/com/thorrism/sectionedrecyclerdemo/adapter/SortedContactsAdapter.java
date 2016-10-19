package com.thorrism.sectionedrecyclerdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;


import com.thorrism.sectionedrecyclerdemo.data.model.Contact;

import java.util.List;

/**
 * Abstract {@link RecyclerView.android.support.v7.widget.RecyclerView.Adapter} that is intended specifically
 * for containing and sorting a collection of {@link Contact} objects.
 *
 * <p>
 *     The subclass to this particular abstract class just needs to take care of the business logic of how to
 *     construct the view for the adpater (providing a ViewHolder, populating the views in the ViewHolder, etc..).
 * </p>
 *
 * Created by Hercules on 7/24/2016.
 */
public abstract class SortedContactsAdapter<H extends RecyclerView.ViewHolder> extends SortedRecyclerAdapter<Contact, H>  {

    @Override
    public int compare(Contact o1, Contact o2) {
        return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
    }

    public SortedContactsAdapter(Context context, List<Contact> data) {
        super(context, data, Contact.class);
    }
}
