package org.ekstep.genie.ui.settings.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.fragment.BaseFragment;

public class AboutUsFragment extends BaseFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about_us, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WebView webview = (WebView) view.findViewById(R.id.webview_about);
        WebSettings setting = webview.getSettings();
        webview.setInitialScale(20);
        setting.setJavaScriptEnabled(true);
        webview.getSettings().setBuiltInZoomControls(true);
        setting.setSupportZoom(true);
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webview.setWebChromeClient(new WebChromeClient());
        webview.loadUrl("file:///android_asset/about_us.html");

        TextView txt_header = (TextView) getActivity().findViewById(R.id.txt_header);
        txt_header.setText(getResources().getText(R.string.label_about_us));
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return null;
    }

    @Override
    protected String getTAG() {
        return null;
    }

    @Override
    protected BaseView getBaseView() {
        return null;
    }

}
