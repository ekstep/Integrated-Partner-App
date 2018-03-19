package org.ekstep.genie.ui.textbook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.ekstep.genie.R;
import org.ekstep.genie.customview.EkStepCustomTextView;
import org.ekstep.genie.customview.EkStepNotoTextView;
import org.ekstep.genie.model.TextbookSection;
import org.ekstep.genie.model.enums.ContentType;
import org.ekstep.genie.util.GlideImageUtil;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.Util;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shriharsh on 28/2/17.
 */

public class TextbookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = TextbookAdapter.class.getSimpleName();
    private final Context mContext;
    private List<TextbookSection> mTextbookSectionList = new ArrayList();
    private boolean isFromDownloadsScreen;
    private int numberOfDownloadedLessonsAvailable = 0;
    private TextbookContract.Presenter mPresenter;
    private int mClickedTextbookSectionPosition = 0;

    public TextbookAdapter(Context context, List<TextbookSection> textbookSectionList, boolean isFromDownloadsScreen, TextbookContract.Presenter presenter) {
        mContext = context;
        mTextbookSectionList.addAll(textbookSectionList);
        this.isFromDownloadsScreen = isFromDownloadsScreen;
        mPresenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View textbookSectionViewHolder = inflater.inflate(R.layout.layout_textbook_item, parent, false);
        viewHolder = new TextBookSectionViewHolder(textbookSectionViewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final TextbookSection textbookSection = mTextbookSectionList.get(position);
        final TextBookSectionViewHolder textBookSectionViewHolder = (TextBookSectionViewHolder) holder;
        textBookSectionViewHolder.vhLinearLayoutLessonsSection.removeAllViews();

        textBookSectionViewHolder.vhTvChapter.setText(textbookSection.getSectionName());
        if (mPresenter.isDownloadingAnyChapter(textbookSection)) {
            textBookSectionViewHolder.vhProgressBarChapterDownload.setVisibility(View.GONE);
        } else if (textbookSection.getSectionContents() != null && textbookSection.getSectionContents().size() > 0) {
            mPresenter.checkToShowProgressOrDownloadChapterButton(textbookSection.getSectionContents(), textBookSectionViewHolder.vhImageButtonChapterDownload, textBookSectionViewHolder.vhProgressBarChapterDownload);
        } else {
//            textBookSectionViewHolder.vhImageButtonChapterDownload.setVisibility(View.VISIBLE);
            textBookSectionViewHolder.vhProgressBarChapterDownload.setVisibility(View.GONE);
        }

        textBookSectionViewHolder.vhImageButtonChapterDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textbookSection.getSectionContents() != null && textbookSection.getSectionContents().size() > 0) {
//                    mPresenter.calculateSize(textbookSection.getSectionContents(), textbookSection.getSectionName(), textBookSectionViewHolder.vhImageButtonChapterDownload, textBookSectionViewHolder.vhProgressBarChapterDownload);
                } else {
                    Util.showCustomToast(mContext.getString(R.string.error_textbook_no_contents_to_download));
                }

            }
        });

        List<Content> lessonsInThisSection = textbookSection.getSectionContents();

        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams((int) mContext.getResources().getDimension(R.dimen.section_each_item_width)
                , (int) mContext.getResources().getDimension(R.dimen.section_each_item_height));

        int dividerDimen = (int) mContext.getResources().getDimension(R.dimen.section_divider);
        parms.setMargins(0, 0, dividerDimen, 0);
        if (lessonsInThisSection != null && lessonsInThisSection.size() > 0) {
            for (Content eachLesson : lessonsInThisSection) {
                View view;
                if (eachLesson.getContentType().equalsIgnoreCase(ContentType.COLLECTION)) {
                    view = LayoutInflater.from(mContext).inflate(R.layout.layout_textbook_adapter_collection_item, null);
                    view.setLayoutParams(parms);
                    setContentToViews(view, eachLesson, R.string.label_all_content_type_collection, position);
                } else if (eachLesson.getContentType().equalsIgnoreCase(ContentType.TEXTBOOK)) {
                    view = LayoutInflater.from(mContext).inflate(R.layout.layout_textbook_adapter_textbook_item, null);
                    view.setLayoutParams(parms);
                    setContentToViews(view, eachLesson, R.string.label_all_content_type_textbook, position);
                } else {
                    view = LayoutInflater.from(mContext).inflate(R.layout.layout_textbook_adapter_normal_item, null);
                    view.setLayoutParams(parms);
                    setContentToViews(view, eachLesson, R.string.label_all_content_type_activity, position);
                }

                textBookSectionViewHolder.vhLinearLayoutLessonsSection.addView(view);
            }
        }
    }

    /**
     * Set content to the views
     *
     * @param view
     * @param lesson
     */
    private void setContentToViews(View view, final Content lesson, int contentType, int position) {
        WrapperContent wrapperContent = new WrapperContent(position, lesson);
        RelativeLayout mCompletelayout = (RelativeLayout) view.findViewById(R.id.completeLayout);
        EkStepNotoTextView mContentName = (EkStepNotoTextView) view.findViewById(R.id.txt_content_name);
        ImageView mContentPoster = (ImageView) view.findViewById(R.id.img_content);
        EkStepNotoTextView mContentType = (EkStepNotoTextView) view.findViewById(R.id.tv_content_type);
        LinearLayout mTickMark = (LinearLayout) view.findViewById(R.id.ll_tick);
        final ImageButton mContentMore = (ImageButton) view.findViewById(R.id.ib_more);
        if (!isFromDownloadsScreen) {
            mContentMore.setVisibility(View.INVISIBLE);
            if (lesson.isAvailableLocally()) {
                numberOfDownloadedLessonsAvailable++;
                mTickMark.setVisibility(View.VISIBLE);
            } else {
                mTickMark.setVisibility(View.GONE);
            }
        } else {
            mTickMark.setVisibility(View.GONE);
            if (lesson.isAvailableLocally()) {
                numberOfDownloadedLessonsAvailable++;
                mContentMore.setTag(wrapperContent);
                mContentMore.setVisibility(View.VISIBLE);
            } else {
                mContentMore.setVisibility(View.INVISIBLE);
            }
        }
        mCompletelayout.setTag(wrapperContent);
        mCompletelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WrapperContent wrapperContent1 = (WrapperContent) view.getTag();
                mPresenter.handleContentClick(wrapperContent1.getContent());
            }
        });

        mContentMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WrapperContent wrapperContent1 = (WrapperContent) view.getTag();
                mClickedTextbookSectionPosition = wrapperContent1.getPosition();
                mPresenter.handleMoreClick(wrapperContent1.getContent());
            }
        });

        mContentName.setText(lesson.getContentData().getName());

        mContentType.setText(contentType);

        if (!StringUtil.isNullOrEmpty(lesson.getContentData().getAppIcon())) {
            LogUtil.d("textbookadapter", "image path " + lesson.getBasePath() + "/" + lesson.getContentData().getAppIcon());
            GlideImageUtil.loadImageUrl(mContext, lesson.getBasePath() + "/" + lesson.getContentData().getAppIcon(), mContentPoster);
        } else {
            GlideImageUtil.loadImageUrl(mContext, mContentPoster);
        }
    }

    public void refresh(List<TextbookSection> lessonsList) {
        numberOfDownloadedLessonsAvailable = 0;
        mClickedTextbookSectionPosition = 0;
        mTextbookSectionList.clear();
        if (lessonsList != null && lessonsList.size() > 0) {
            mTextbookSectionList.addAll(lessonsList);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mTextbookSectionList.size();
    }

    public TextbookSection getItem(int position) {
        return mTextbookSectionList.get(position);
    }

    /**
     * View Holder for each section
     */
    public class TextBookSectionViewHolder extends RecyclerView.ViewHolder {
        public View view;

        private EkStepCustomTextView vhTvChapter;
        private LinearLayout vhLinearLayoutLessonsSection;
        private ImageButton vhImageButtonChapterDownload;
        private ProgressBar vhProgressBarChapterDownload;

        public TextBookSectionViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemView;
            vhTvChapter = (EkStepCustomTextView) itemLayoutView.findViewById(R.id.tv_chapter_name);
            vhLinearLayoutLessonsSection = (LinearLayout) itemLayoutView.findViewById(R.id.ll_chapter_lessons);
            vhImageButtonChapterDownload = (ImageButton) itemLayoutView.findViewById(R.id.ib_chapter_download);
            vhProgressBarChapterDownload = (ProgressBar) itemLayoutView.findViewById(R.id.pb_chapter_download);
        }
    }

    private class WrapperContent {
        private int position;
        private Content content;

        WrapperContent(int position, Content content) {
            this.position = position;
            this.content = content;
        }

        public Content getContent() {
            return content;
        }

        public int getPosition() {
            return position;
        }
    }

}
