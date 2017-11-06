package org.ekstep.genie.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.ui.settings.about.AboutFragment;
import org.ekstep.genie.util.DeviceUtility;
import org.ekstep.genie.util.FontUtil;
import org.ekstep.genie.util.preference.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by swayangjit_gwl on 3/29/2016.
 */
public class DummyLanguageTestFragment extends Fragment implements View.OnClickListener{

    List<String> language = new ArrayList<>();
    private PopupWindow mPopupWindow;
    private TextView txt_language;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dummy_fragment, container, false);

        view.findViewById(R.id.back_btn).setOnClickListener(this);
        view.findViewById(R.id.more_btn).setOnClickListener(this);

        txt_language = (TextView) view.findViewById(R.id.txt_selected_language);
        // spinner items
        language.add("English");
        language.add("Hindi");
        language.add("Kannada");
        language.add("Telugu");
        language.add("Marathi");
//        language.add("Tamil");
//        language.add("Malayalam");
//        language.add("Bengali");
//        language.add("Punjabi");
//        language.add("Gujarati");
//        language.add("Assamese");
//        language.add("Oriya");

        txt_language.setText(language.get(FontUtil.getInstance().getPositionForSelectedLocale()));

        return view;
    }

    public void showLanguageDialog(Context context, int x, int y){
        final Dialog dialog  = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_dummy_language);
        dialog.setCanceledOnTouchOutside(true);

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP | Gravity.LEFT;
        wmlp.x = x;
        wmlp.y = y;

        dialog.findViewById(R.id.layout_english).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                setLanguage(0);

            }
        });

        dialog.findViewById(R.id.layout_hindi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                setLanguage(1);

            }
        });

        dialog.findViewById(R.id.layout_kannada).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                setLanguage(2);

            }
        });

        dialog.findViewById(R.id.layout_telugu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                setLanguage(3);

            }
        });

        dialog.findViewById(R.id.layout_marathi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                setLanguage(4);

            }
        });

        DeviceUtility.displayFullScreenDialog(dialog,getActivity());
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back_btn) {
            ((LandingActivity) this.getActivity()).setFragment(new AboutFragment(), false);

        } else if (i == R.id.more_btn) {
            ImageView view = (ImageView) getActivity().findViewById(R.id.more_btn);
            showLanguageDialog(getActivity(), view.getLeft() + ((int) (view.getWidth() * 1.4)),
                    view.getTop() + (view.getHeight()));

        } else {
        }
    }

    private void setLanguage(int position){
        String localeString = FontUtil.getInstance().getLocaleAtPosition(position);

        PreferenceUtil.setLanguageSelected(true);
        PreferenceUtil.setLanguage(localeString);
        FontUtil.getInstance().changeLocale();
        PreferenceUtil.setShowDummyFragment("true");

        getActivity().startActivity(new Intent(getActivity(), LandingActivity.class));
        getActivity().finish();
    }
}
