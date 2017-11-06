package org.ekstep.ipa.ui.intro;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.ekstep.ipa.R;
import org.ekstep.ipa.model.IntroModel;

import java.util.List;


/**
 * @author vinayagasundar
 */

public class IntroAdapter extends PagerAdapter {

    private List<IntroModel> mIntroModelList;

    private Context mContext;

    public IntroAdapter(@NonNull Context context, List<IntroModel> introModelList) {
        mContext = context;
        mIntroModelList = introModelList;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        IntroModel introModel = mIntroModelList.get(position);

        View layout = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_intro_item, container, false);

        TextView titleText = (TextView) layout.findViewById(R.id.title_text);
        TextView descText = (TextView) layout.findViewById(R.id.desc_text);
        ImageView introImage = (ImageView) layout.findViewById(R.id.intro_image);

        titleText.setText(introModel.getTitleText());
        descText.setText(introModel.getDescText());
        introImage.setImageResource(introModel.getImageResId());

        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return (mIntroModelList != null ? mIntroModelList.size() : 0);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }
}
