package com.thorrism.sectionedrecyclerdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thorrism.sectionedrecyclerdemo.R;
import com.thorrism.sectionedrecyclerdemo.data.model.Contact;
import com.thorrism.sectionedrecyclerdemo.service.reactive.RxBus;
import com.thorrism.sectionedrecyclerdemo.events.ContactSelectedEvent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lcrawford on 10/16/16.
 */
public class ContactAdapter extends SortedContactsAdapter<ContactAdapter.ContactViewHolder> {

    private RxBus rxBus;

    public ContactAdapter(Context context, List<Contact> data, RxBus rxBus) {
        super(context, data);
        this.rxBus = rxBus;
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        Contact contact = getItem(position);
        holder.contactName.setText(contact.getName());
        holder.contactType.setText(contact.getType());

        // Bind the holder's contact to a click listener
        holder.bind(contact);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactViewHolder(this.layoutInflater.inflate(R.layout.item_contact, parent, false));
    }

    class ContactViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.text_contact_name)
        TextView contactName;

        @BindView(R.id.text_contact_type)
        TextView contactType;

        public ContactViewHolder(View v){
            super(v);
            ButterKnife.bind(this, v);
        }

        public void bind(final Contact contact){
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rxBus.publish(new ContactSelectedEvent(contact));
                }
            });
        }
    }
}
