package org.ekstep.genie.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makeramen.roundedimageview.RoundedImageView;

import org.ekstep.genie.R;
import org.ekstep.genie.customview.FancyCoverFlow;
import org.ekstep.genie.customview.FancyCoverFlowAdapter;
import org.ekstep.genie.util.FontUtil;
import org.ekstep.genie.util.Util;

public class LanguageCoverflowAdapter extends FancyCoverFlowAdapter {
    int mSelected;
    private Context mContext = null;
    private int[] mImages;

    public LanguageCoverflowAdapter(Context context) {
        mContext = context;
        mImages = FontUtil.getInstance().getLanguageResources();
        mSelected = FontUtil.getInstance().getPositionForSelectedLocale();
    }

    @Override
    public int getCount() {
        return mImages.length;
    }

    @Override
    public Integer getItem(int i) {
        return mImages[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setSelectedPosition(int position) {
        mSelected = position;
    }
    @Override
    public View getCoverFlowItem(final int i, View reuseableView, ViewGroup viewGroup) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_language_item, null);

        final RoundedImageView coverImage = (RoundedImageView) view.findViewById(R.id.cover_image);

        int dimension = Util.convertDp(mContext, R.dimen.language_card_height);
        view.setLayoutParams(new FancyCoverFlow.LayoutParams(dimension, dimension));
        if (mSelected == i) {
            coverImage.setBorderColor(mContext.getResources().getColor(R.color.white_transparent1));
        } else {
            coverImage.setBorderColor(Color.TRANSPARENT);
        }
        coverImage.setImageResource(mImages[i]);
        view.setTag(i);

        return view;
    }

}
