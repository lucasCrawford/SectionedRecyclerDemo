package com.thorrism.sectionedrecyclerdemo.adapter;

import android.content.Context;
import android.util.AttributeSet;


import xyz.danoz.recyclerviewfastscroller.sectionindicator.title.SectionTitleIndicator;

/**
 * Created by lcrawford on 10/16/16.
 */
public class SimpleSectionTitleIndicator extends SectionTitleIndicator<SimpleSectionRecyclerAdapter.Section> {

    public SimpleSectionTitleIndicator(Context context) {
        super(context);
    }

    public SimpleSectionTitleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleSectionTitleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setSection(SimpleSectionRecyclerAdapter.Section section){
        setTitleText(section.getTitle().toString());
    }
}
