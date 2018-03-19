package org.ekstep.genie.ui.collection;

import android.os.Bundle;

import org.ekstep.genie.R;
import org.ekstep.genie.activity.BaseActivity;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;

/**
 * Created on 8/2/17.
 *
 * @author shriharsh
 */
public class CollectionsActivity extends BaseActivity {
    private CollectionFragment collectionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_collections);

        collectionFragment = new CollectionFragment();
        collectionFragment.setArguments(getIntent().getExtras());
        setFragment(collectionFragment, false);
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

//    // @Override
//    public void startContentDetailsActivity(Content content, String path, List<HierarchyInfo> contentInfoList, boolean isFromDownloads, boolean isSpineAvailable, boolean isFromCollection, boolean isFromTextbook) {
//        Intent intent = new Intent(CollectionsActivity.this, ContentDetailActivity.class);
//        intent.putExtra(Constant.EXTRA_GAME_DATA, content);
//        intent.putExtra(Constant.BUNDLE_KEY_IS_SPINE_AVAILABLE, isSpineAvailable);
//        intent.putExtra(Constant.BUNDLE_KEY_LOCAL_PATH, path);
//        intent.putExtra(Constant.BUNDLE_KEY_IS_FROM_DOWNLOADS, isFromDownloads);
//        intent.putExtra(Constant.EXTRAS_CONTENT_INFO, (Serializable) contentInfoList);
//        intent.putExtra(Constant.BUNDLE_KEY_FROM_INSIDE_COLLECTION, isFromCollection);
//        intent.putExtra(Constant.BUNDLE_KEY_IS_FROM_COLLECTION_OR_TEXTBOOK, isFromTextbook);
//
//        startActivity(intent);
//    }

    @Override
    public void onBackPressed() {
        if (collectionFragment != null && collectionFragment.isVisible()) {
            collectionFragment.handleTelemetryEndEvent();
        }
        super.onBackPressed();
    }

}
