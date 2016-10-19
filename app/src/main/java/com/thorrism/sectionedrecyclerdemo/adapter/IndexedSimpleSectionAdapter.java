package com.thorrism.sectionedrecyclerdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.SectionIndexer;

/**
 * Implementation of {@link SimpleSectionRecyclerAdapter} that supports an implementation of
 * {@link SectionIndexer} to use in conjunction with fast scrolling.
 *
 * Created by lcrawford on 10/16/16.
 */
public class IndexedSimpleSectionAdapter extends SimpleSectionRecyclerAdapter implements SectionIndexer {

    private Section[] originalData;

    public IndexedSimpleSectionAdapter(Context context, int sectionResourceId, int textResourceId, RecyclerView.Adapter baseAdapter) {
        super(context, sectionResourceId, textResourceId, baseAdapter);
    }

    @Override
    public void setSections(Section[] sections, boolean sortSections) {
        super.setSections(sections, sortSections);
        originalData = sections;
    }

    @Override
    public Object[] getSections() {
        return originalData;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        // Find the nearest section
        int key = 0;
        int result = mSections.keyAt(key);
        for(int i=0, size = mSections.size(); i<size; ++i){
            key = mSections.keyAt(i);
            if(position >= key){
                result = mSections.keyAt(i);
            }else if(position < key){
                break;
            }
        }

        // Return the index from the sparse array for the nearest section
        return mSections.indexOfKey(result);
    }
}
