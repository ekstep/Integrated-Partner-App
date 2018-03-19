package org.ekstep.genie.ui.landing.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.ContentData;
import org.ekstep.genieservices.commons.bean.ContentImportResponse;
import org.ekstep.genieservices.commons.bean.ContentListingSection;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.Profile;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created on 4/1/17.
 *
 * @author shriharsh
 */
public interface HomeContract {

    interface View extends BaseView {

        void openNavigationDrawer();

        void setSubject(String s);

        void changeToAddChildActivity();

        void showProgressDialog();

        void hideProgressDialog();

        void removeAllSections();

        void showSwitchProfileDialog(List<Profile> profileList, Profile currentProfile);

        void showChildOrGroupProfileAvatar(int resId);

        void showAddChildImage();

        void openSearchActivity(String query, boolean isExplicit, Profile currentProfile);

        void popUpSubjects(List<String> subjectsList);

        void showAddChildOnBoarding();

        void showChangeSubjectOnBoarding();

        void disableOnBoardingViews();

        void showSectionsLayout();

        void hideNoDataAvailableView();

        void showNoDataAvailableView(GenieResponse genieResponse);

        void hideSectionLayout();

        void inflateSectionWithContents(ContentListingSection section, int sectionCount, List<ContentData> contentList);

        void hideDownloadContent();

        void showMyLessonsVisibility();

        void showMyLessonsLocalContentAdapter(List<Content> gameList, Profile profile, boolean isKnownUserProfile);

        void showMyTextbooksLocalContentAdapter(List<Content> textbookList, Profile profile, boolean isKnownUserProfile);

        void hideMyLessons();

        void hideMyTextBooks();

        void showDownloadContent();

        void navigateToSearchWithSearchSortFilterCriteria(ContentListingSection section, Profile currentProfile);

        void navigateToSearchWithRecommendationCriteria(Profile currentProfile);

        void showFooter();

        void hideFooter();

        void setChosenSubjectName(String subjectName);

        void playContent(Content content);

        void showTransparentView();

        void showMyTextbooksVisibility();

        void generateAddChildSkippedTelemetry();

        void generateSubjectSkippedTelemetry();

        void generateOnboardingCompletedTelemetry();

        void generateSubjectChangedTelemetry(String subject);

        void generateViewMoreClickedTelemetry(String sectionName);

        void inflateEmptySection(ContentListingSection section, int sectionCount);

        void generateSectionViewdTelemetryEvent(List<Map<String, Object>> sectionViewMap);

        void generateGenieHomeTelemetry(String uid);

        void shakeLayoutMore(TextView layout_More);

        void lockDrawer();

        void unLockDrawer();

        void showGenieOfflineLayout();

        void hideGenieOfflineLayout();

        void scrollToBestofGenie();

        void hideKeyboard(EditText searchEditText);

        void setSearchBarVisibility(int visibility);

        void hideSoftKeyboard();

        void navigateToMyLessonsActivity(String lessonType, Profile profile, boolean isKnownUserProfile);

        void showLessonsViewAll();

        void showBooksViewAll();

        void hideLessonsViewAll();

        void hideBooksViewAll();

        void showDeviceMemoryLowView();

        void showAddMemoryCardDialog();

        void startDownloadAnimation();

        void stopDownloadAnimation();
    }

    interface Presenter extends BasePresenter {

        void openNavigationDrawer();

        void changeSubject(int position);

        void addOrSwitchChild();

        void openHome(Bundle arguments);

        void getAllProfiles();

        void setSwitchedKidDetails(Profile profile, int size, boolean shouldCallApi);

        void getLocalContents();

        void switchToChildActivity();

        void openSearchResultActivity(String query, boolean isExplicit);

        void getSubjectsList();

        void showOnBoarding();

        void skipCurrentOnBoarding();

        void showOnBoardChangeSubject();

        void incrementOnBoardingState();

        void navigateToSearchResultActivity(ContentListingSection section);

        void setSubjectName();

        void startGame(Content content);

        void checkViewVisibility(List<android.view.View> viewList, ScrollView scrollView);

        void onPause();

        void onScrollChanged(RecyclerView recyclerView, int newState, TextView layout_More);

        void manageDrawer();

        void manageNetworkConnectivity(Context context);

        void getCurrentUser();

        void getOnlineContents();

        void setLocalContentsCalledAlreadyToFalse();

        void checkIfUserHasOnBoardedChangeSubjectStep();

        void checkForOnBoarding();

        void searchContent(String searchQuery, EditText editText);

        void handleSectionItemClicked(ContentData contentData, int positionClicked, Map<String, String> sectionMap);

        void setLocalContentDetails(Content content, LocalContentAdapter.NormalContentViewHolder normalContentViewHolder, LocalContentAdapter localContentAdapter, long mProfileCreationTime);

        void setLocalCollectionDetails(Content content, LocalContentAdapter.CollectionContentViewHolder collectionContentViewHolder, LocalContentAdapter localContentAdapter, long mProfileCreationTime);

        void setLocalTextbookDetails(Content content, LocalContentAdapter.TextBookContentViewHolder textBookContentViewHolder, LocalContentAdapter localContentAdapter, long mProfileCreationTime);

        void setSectionContentDetails(Set<String> identifierList, ContentData contentData, SectionAdapter.NormalContentViewHolder normalContentViewHolder, SectionAdapter sectionAdapter);

        void setSectionCollectionDetails(Set<String> identifierList, ContentData contentData, SectionAdapter.CollectionContentViewHolder collectionContentViewHolder, SectionAdapter sectionAdapter);

        void setSectionTextbookDetails(Set<String> identifierList, ContentData contentData, SectionAdapter.TextBookContentViewHolder textBookContentViewHolder, SectionAdapter sectionAdapter);

        int getItemTypeInSectionAdapter(int position, List<ContentData> contentDataList);

        int getItemTypeInLocalAdapter(int position, List<Content> contentList);

        void manageLocalContentImportSuccess(String importSuccess);

        void manageImportnDeleteSuccess(Object response);

//        void editChildSuccess(String editChildSuccess);

        void manageRefreshProfile(String refreshProfile);

        void hideSoftKeyboard();

        void handleViewAllClick(String lessonType);

        void checkIfPartnerHasSkippedChangeSubject(boolean shouldShowSubjectChange);

        void checkForDeviceMemoryLow();

        void checkForLowInternalMemory();

        void manageImportResponse(ContentImportResponse importResponse);

        void handleDownloadingAnimation(String identifier);

        void manageSwitchSource(String switchSource);
    }
}
