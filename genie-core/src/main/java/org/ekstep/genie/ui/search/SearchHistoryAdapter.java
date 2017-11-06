package org.ekstep.genie.ui.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.ui.landing.home.HomePresenter;

import java.util.List;

/**
 * Created by Indraja Machani on 7/13/2017.
 */

public class SearchHistoryAdapter extends ArrayAdapter<String> {
    private int viewResourceId;
    private BasePresenter mPresenter;

    public SearchHistoryAdapter(Context context, int resource, List<String> searchHistoryList, BasePresenter presenter) {
        super(context, resource, searchHistoryList);
        viewResourceId = resource;
        mPresenter = presenter;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        String searchedItem = (String) getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(viewResourceId, parent, false);
            viewHolder = new ViewHolder();
            // Lookup view for data population
            viewHolder.mTvSearchHistory = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mTvSearchHistory.setText(searchedItem);

        // OnTouchListener, Hide keyboard on ACTION_DOWN
        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mPresenter instanceof HomePresenter) {
                        HomePresenter homePresenter = (HomePresenter) mPresenter;
                        homePresenter.hideSoftKeyboard();
                    } else if (mPresenter instanceof SearchPresenter) {
                        SearchPresenter searchPresenter = (SearchPresenter) mPresenter;
                        searchPresenter.hideSoftKeyboard();
                    }
                }
                return false;
            }
        });

        return convertView;
    }


    private class ViewHolder {
        TextView mTvSearchHistory;
    }
}
