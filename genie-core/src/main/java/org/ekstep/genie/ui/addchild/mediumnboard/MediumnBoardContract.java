package org.ekstep.genie.ui.addchild.mediumnboard;

import android.os.Bundle;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genieservices.commons.bean.Profile;

import java.util.List;

/**
 * Created by swayangjit on 22/12/16.
 */

public interface MediumnBoardContract {
    interface View extends BaseView {

        void showMediumListPopup(List<String> mediumList, android.view.View view);

        void showBoardListPopup(List<String> boardList, android.view.View view);

        void showMedium(String medium);

        void showBoard(String board);

        void showEmptyMediumAnimation();

        void showEmptyBoardAnimation();

        void animateNextButton();
    }

    interface Presenter extends BasePresenter {

        void fetchBundleExtras(Bundle bundle);

        void openMediumnBoard();

        void onMediumClick(android.view.View view);

        void onMediumItemClick(int position);

        void onBoardClick(android.view.View view);

        void onBoardItemClick(int position);

        void handleEmptyMedium();

        void setMediumMarked();

        String getMediumofInstruction();

        void setBoardMarked();

        void handleEmptyBoard();

        Profile getMediumnBoard();

        void populateMediumnBoard();

    }
}
