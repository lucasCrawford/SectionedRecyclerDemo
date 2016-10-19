package com.thorrism.sectionedrecyclerdemo.adapter;

import android.content.Context;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract {@link RecyclerView.android.support.v7.widget.RecyclerView.Adapter} that supports
 * a sorted collection based on the concrete implementation's definition of {@link #compare(Object, Object)}.
 *
 * Created by Hercules on 7/24/2016.
 */
public abstract class SortedRecyclerAdapter<T, H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<H> {
    protected SortedList<T> mSortedList;
    protected Context mContext;
    protected LayoutInflater layoutInflater;

    public SortedRecyclerAdapter(Context context, List<T> data, Class<T> clazz){
        this.mContext = context;
        initAdapter(clazz);
        mSortedList.addAll(data);
        layoutInflater = LayoutInflater.from(context);
    }

    public abstract int compare(T o1, T o2);

    public void initAdapter(Class<T> clazz){
        mSortedList = new SortedList<>(clazz, new SortedList.Callback<T>() {

            @Override
            public int compare(T o1, T o2) {
                return SortedRecyclerAdapter.this.compare(o1, o2);
            }

            @Override
            public void onInserted(int position, int count) {
               notifyItemInserted(position);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyDataSetChanged();
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(T oldItem, T newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(T item1, T item2) {
                return item1.hashCode() == item2.hashCode();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSortedList.size();
    }

    /**
     * Get the sorted items of the adapter in a {@link List}.
     *
     * <p>
     *     Useful if a developer needs to construct a list out of the
     *     internal data, and to abstract away the use of {@link SortedList}.
     * </p>
     *
     * @return  List constructed from the type T objects in our {@link SortedList}
     */
    public List<T> getItems(){
        int size = mSortedList.size();
        List<T> items = new ArrayList<>(size);
        for(int i=0; i<size; ++i){
            items.add(mSortedList.get(i));
        }
        return items;
    }

    public SortedList<T> getSortedList(){
        return this.mSortedList;
    }

    public T getItem(int pos){
        return this.mSortedList.get(pos);
    }

    public T removeItem(int pos){
        return mSortedList.removeItemAt(pos);
    }

    public boolean removeItem(T item){
        return mSortedList.remove(item);
    }

    public void updateItem(int pos, T newItem){
        this.mSortedList.updateItemAt(pos, newItem);
    }

    public void addItem(T item){
        this.mSortedList.add(item);
    }

    public void replaceData(List<T> data){
        mSortedList.clear();
        mSortedList.addAll(data);
    }
}
