package org.ekstep.genie.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.ekstep.genie.R;

import java.util.List;
import java.util.Map;

/**
 * Created by swayangjit on 28/9/17.
 */

public class BreadCrumbHorizontalScrollView extends HorizontalScrollView implements View.OnClickListener {
    private BreadcrumbHeaderCallback breadcrumbHeaderCallback;
    private LayoutInflater mInflater;
    private List<Map<String, String>> mContentHeaders;

    public BreadCrumbHorizontalScrollView(Context context) {
        super(context);
        init();
    }

    public BreadCrumbHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BreadCrumbHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.txt_header_name) {
            if (breadcrumbHeaderCallback != null) {
                Map<String, String> contentHeader = (Map<String, String>) v.getTag();
                breadcrumbHeaderCallback.onHeaderClick(Integer.valueOf(contentHeader.get("position")), String.valueOf(contentHeader.get("identifier")));
            }
        }
    }

    public void setBreadcrumbHeaderCallback(BreadcrumbHeaderCallback callback) {
        this.breadcrumbHeaderCallback = callback;
    }


    public void init() {
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
    }

    public void addBreadcrumbs(List<Map<String, String>> contentHeaders) {
        mContentHeaders = contentHeaders;
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_breadcrumb_parent);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.removeAllViews();
        for (int i = 0; i < contentHeaders.size(); i++) {
            Map<String, String> contentHeader = contentHeaders.get(i);
            RelativeLayout item = (RelativeLayout) mInflater.inflate(R.layout.layout_breadcrumb_item, null);
            EkStepCustomTextView textView = (EkStepCustomTextView) item.findViewById(R.id.txt_header_name);
            textView.setText(contentHeader.get("name"));
            contentHeader.put("position", String.valueOf(i));
            textView.setTag(contentHeader);
            textView.setOnClickListener(this);
            if (i == contentHeaders.size() - 1) {
                item.findViewById(R.id.view_bottom).setVisibility(View.VISIBLE);
            }
            linearLayout.addView(item, layoutParams);
        }

    }

    public LinearLayout getItemView(String name, int stackSize) {
        LinearLayout linearLayout = (LinearLayout) mInflater.inflate(R.layout.layout_breadcrumb_item, null);
        EkStepCustomTextView textView = (EkStepCustomTextView) linearLayout.findViewById(R.id.txt_header_name);
        textView.setText(name);
        textView.setTransformationMethod(null);
//        textView.setOnClickListener(onClickItemListener);
        textView.setTag(stackSize);

        return linearLayout;
    }

    public void removeBreadcrumbItemFrom(int position) {
        mContentHeaders.subList(position, mContentHeaders.size()).clear();
        addBreadcrumbs(mContentHeaders);
    }

    public void popBreadcrumbItem() {
        mContentHeaders.remove(mContentHeaders.size() - 1);
        addBreadcrumbs(mContentHeaders);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        fullScroll(HorizontalScrollView.FOCUS_RIGHT);
    }

    public interface BreadcrumbHeaderCallback {
        void onHeaderClick(int position, String identifier);
    }
}
