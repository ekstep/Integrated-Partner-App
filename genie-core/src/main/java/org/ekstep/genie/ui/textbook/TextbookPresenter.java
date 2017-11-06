package org.ekstep.genie.ui.textbook;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.customview.treeview.TreeItemHolder;
import org.ekstep.genie.customview.treeview.model.TreeNode;
import org.ekstep.genie.model.ContentDeleteResponse;
import org.ekstep.genie.model.TextbookSection;
import org.ekstep.genie.model.enums.ContentType;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryConstant;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.telemetry.enums.EntityType;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.DialogUtils;
import org.ekstep.genie.util.FileHandler;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.PlayerUtil;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.geniesdk.ContentUtil;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.IContentService;
import org.ekstep.genieservices.async.ContentService;
import org.ekstep.genieservices.async.UserService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.ChildContentRequest;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.ContentAccess;
import org.ekstep.genieservices.commons.bean.ContentAccessFilterCriteria;
import org.ekstep.genieservices.commons.bean.ContentData;
import org.ekstep.genieservices.commons.bean.ContentDelete;
import org.ekstep.genieservices.commons.bean.ContentDeleteRequest;
import org.ekstep.genieservices.commons.bean.ContentDetailsRequest;
import org.ekstep.genieservices.commons.bean.ContentImport;
import org.ekstep.genieservices.commons.bean.ContentImportRequest;
import org.ekstep.genieservices.commons.bean.ContentImportResponse;
import org.ekstep.genieservices.commons.bean.ContentLearnerState;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.HierarchyInfo;
import org.ekstep.genieservices.commons.bean.UserSession;
import org.ekstep.genieservices.commons.bean.enums.ContentImportStatus;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.commons.utils.DateUtil;
import org.ekstep.genieservices.commons.utils.StringUtil;
import org.ekstep.genieservices.eventbus.EventBus;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 8/2/17.
 *
 * @author shriharsh
 */
public class TextbookPresenter implements TextbookContract.Presenter {

    private static final String TAG = TextbookPresenter.class.getSimpleName();
    private static final String CONTENT_TYPE_TEXTBOOKUNIT = "textbookunit";
    private static final String SECTION_IDENTIFIER = "sectionIdentifier";
    private Context mContext;
    private TextbookContract.View mTextbookView;

    private Content mContent;
    private ContentData mContentData;
    private ContentAccess mContentAccess;
    private String mLocalPath;
    private boolean mIsFromDownloadsScreen;
    private int id = 0;
    private List<Map<String, Object>> mSectionMapList = new ArrayList<>();
    private Content mTextbookObjectGotFromApi;
    private boolean isNeededToGenerateHomeInteractEvent;
    private List<TextbookSection> mTextbookSectionsList = new ArrayList<>();
    private ContentService mContentService = null;
    private UserService mUserService = null;
    private UserSession mUserSession;
    private IContentService mSyncContentService = null;
    private Content mContentToBeDeleted;

    public TextbookPresenter() {
        mContentService = CoreApplication.getGenieAsyncService().getContentService();
        mUserService = CoreApplication.getGenieAsyncService().getUserService();
        mSyncContentService = CoreApplication.getGenieSdkInstance().getContentService();
    }

    @Override
    public void fetchTextbooks(Bundle arguments) {
        mContent = (Content) arguments.getSerializable(Constant.BundleKey.BUNDLE_KEY_CONTENT);
        mContentData = mContent.getContentData();

        ContentUtil.addContentAccess(mContent);

        List<ContentAccess> contentAccessList = mContent.getContentAccess();
        if (contentAccessList.size() > 0) {
            mContentAccess = contentAccessList.get(0);
        } else {
            mContentAccess = new ContentAccess();
        }
        mLocalPath = arguments.getString(Constant.BundleKey.BUNDLE_KEY_LOCAL_PATH, "");
        mIsFromDownloadsScreen = arguments.getBoolean(Constant.BundleKey.BUNDLE_KEY_IS_FROM_DOWNLOADS, false);


        mTextbookView.setIsFromDownload(mIsFromDownloadsScreen);

        setContentIcon();

        mTextbookView.setTextbookName(mContentData.getName());

        if (mContentData.getGradeLevel() != null && mContentData.getGradeLevel().size() > 0) {
            mTextbookView.showGrade(mContentData.getGradeLevel().get(0));
        } else {
            mTextbookView.hideGrade();
            mTextbookView.hideGradeDivider();
        }

        if (mContentData.getLanguage() != null && mContentData.getLanguage().size() > 0) {
            mTextbookView.showLanguage(mContentData.getLanguage().get(0));
        } else {
            mTextbookView.hideLanguage();
        }
    }

    private void setContentIcon() {
        String appIcon = mContentData.getAppIcon();
        if (appIcon != null) {
            if (Util.isValidURL(appIcon)) {
                mTextbookView.showIcon(appIcon);
            } else {
                mTextbookView.showLocalIcon(new File(mLocalPath + "/" + appIcon));
            }
        } else {
            mTextbookView.showDefaultIcon(R.drawable.ic_default_book);
        }
    }

    @Override
    public void fetchTableOfContentsAndLessons(final boolean refreshLayout) {
        if (refreshLayout) {
            isNeededToGenerateHomeInteractEvent = true;
            ChildContentRequest.Builder childContentRequestBuilder = new ChildContentRequest.Builder();
            childContentRequestBuilder.forContent(mContentData.getIdentifier()).hierarchyInfo(mContent.getHierarchyInfo());
            mContentService.getChildContents(childContentRequestBuilder.build(), new IResponseHandler<Content>() {
                @Override
                public void onSuccess(GenieResponse<Content> genieResponse) {
                    Content mainContent = genieResponse.getResult();
                    if (mainContent != null) {
                        mTextbookObjectGotFromApi = mainContent;

                        getSectionNamesAndLessons(mainContent);
                        generateTOC(mainContent, refreshLayout);
                        mTextbookView.showTextBookShelf(mTextbookSectionsList, mIsFromDownloadsScreen);
                    }
                }

                @Override
                public void onError(GenieResponse<Content> genieResponse) {

                }
            });
        } else {
            isNeededToGenerateHomeInteractEvent = false;
            generateTOC(mTextbookObjectGotFromApi, refreshLayout);
            getSectionNamesAndLessons(mTextbookObjectGotFromApi);
            mTextbookView.showTextBookShelf(mTextbookSectionsList, mIsFromDownloadsScreen);
        }
    }

    /**
     * This method gives Section wise, the name and the list of contents beloging
     * to that first level/unit of section
     *
     * @param content
     */
    private void getSectionNamesAndLessons(Content content) {

        //make a list of TextbookSections
        List<TextbookSection> textbookSectionsList = new ArrayList<>();

        //check if the TU list is not null and empty
        List<Content> textbookUnitList = content.getChildren();
        if (textbookUnitList != null && textbookUnitList.size() > 0) {
            for (Content eachTextbookUnitInTuList : textbookUnitList) {
                TextbookSection textbookSection = new TextbookSection();

                //this list is for maintaining content info for each lessons
                List<HierarchyInfo> lessonAndTextbookUnitRelationList = new ArrayList<>();

                //add the content info for the first level and later add it to lessonParentRelationList
                HierarchyInfo hierarchyInfo = new HierarchyInfo(eachTextbookUnitInTuList.getIdentifier(), eachTextbookUnitInTuList.getContentData().getContentType());
                lessonAndTextbookUnitRelationList.add(hierarchyInfo);

                String sectionName = eachTextbookUnitInTuList.getContentData().getName();

                if (!TextUtils.isEmpty(sectionName)) {
                    textbookSection.setSectionName(sectionName);
                }

                //store the complete content details of this current section
                textbookSection.setCurrentSectionContentDetails(eachTextbookUnitInTuList);

                //add the games to the section if they have lesson units
                List<Content> lessonsOfTheEachTextbookUnit = new ArrayList<>();

                if (eachTextbookUnitInTuList.getChildren() != null) {
                    lessonsOfTheEachTextbookUnit.addAll(eachTextbookUnitInTuList.getChildren());
                }

                if (lessonsOfTheEachTextbookUnit.size() > 0) {
                    for (Content lesson : lessonsOfTheEachTextbookUnit) {
                        lesson.setHierarchyInfo(lesson.getHierarchyInfo());
                    }
                }

                //check if there are any textbook unit list under current textbook unit
                if (eachTextbookUnitInTuList.getChildren() != null && eachTextbookUnitInTuList.getChildren().size() > 0) {
                    List<HierarchyInfo> lessonAndTextbookUnitRelationListForUnderlyingLessons = new ArrayList<>();
                    lessonAndTextbookUnitRelationListForUnderlyingLessons.addAll(lessonAndTextbookUnitRelationList);

                    for (Content textbookUnit : eachTextbookUnitInTuList.getChildren()) {
                        if (textbookUnit.getContentType().equalsIgnoreCase(ContentType.TEXTBOOKUNIT)) {
                            List<Content> underlyingLessonsListFromEachTU = new ArrayList<>();

                            underlyingLessonsListFromEachTU = getAllLessonsFromUnderlyingTextbookUnit(underlyingLessonsListFromEachTU, textbookUnit, lessonAndTextbookUnitRelationListForUnderlyingLessons);

                            //add all these games list to main games list
                            lessonsOfTheEachTextbookUnit.addAll(underlyingLessonsListFromEachTU);
                        }
                    }
                }

                List<Content> sortedlessonsOfTheEachTextbookUnit = new ArrayList<>();
                List<String> contentIdentifiersOfTheEachTextbookUnit = new ArrayList<>();
                for (Content lessonOfTheEachTextbookUnit : lessonsOfTheEachTextbookUnit) {
                    if (!lessonOfTheEachTextbookUnit.getContentType().equalsIgnoreCase(ContentType.TEXTBOOKUNIT)) {
                        sortedlessonsOfTheEachTextbookUnit.add(lessonOfTheEachTextbookUnit);
                        contentIdentifiersOfTheEachTextbookUnit.add(lessonOfTheEachTextbookUnit.getIdentifier());
                    }
                }
                textbookSection.setSectionContents(sortedlessonsOfTheEachTextbookUnit);
                textbookSection.setSectionContentIdentifiers(contentIdentifiersOfTheEachTextbookUnit);

                textbookSectionsList.add(textbookSection);
            }
        }

        //add this in activity and get from activity in DownloadTextbookFragment
        ((TextbookActivity) mContext).setTextbookSectionList(textbookSectionsList);

        if (textbookSectionsList.size() > 0) {
            mTextbookSectionsList.clear();
            mTextbookSectionsList.addAll(textbookSectionsList);

            if (checkIfLocalContentIsAvailableorNot(textbookSectionsList)) {
                mTextbookView.showDownloadedLessonText();
                mTextbookView.showTickImage();
            } else {
                mTextbookView.hideDownloadedLessonText();
                mTextbookView.hideTickImage();
            }
            mTextbookView.hideNoTextBooksView();
            mTextbookView.showTextBooksRecyclerView();

        } else {
            mTextbookView.hideTextBooksRecyclerView();
            mTextbookView.showNoTextBooksView();
        }
    }

    private boolean checkIfLocalContentIsAvailableorNot(List<TextbookSection> textbookSectionsList) {
        for (TextbookSection textbookSection : textbookSectionsList) {
            List<Content> eachSectionLessons = textbookSection.getSectionContents();
            if (eachSectionLessons != null && eachSectionLessons.size() > 0) {
                for (Content lesson : eachSectionLessons) {
                    if (lesson.isAvailableLocally()) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    /**
     * This method recursively adds the underlying games present for a particular textbookunit
     * to one single list of games belonging to first level/textbookunit of that section
     *
     * @param underlyingGamesList
     * @param eachGameInTuList
     * @param underlyingLessonAndTextbookUnitRelationList
     * @return
     */
    private List<Content> getAllLessonsFromUnderlyingTextbookUnit(List<Content> underlyingGamesList, Content eachGameInTuList, List<HierarchyInfo> underlyingLessonAndTextbookUnitRelationList) {
        //create new underlyingLessonAndTextbookUnitRelationList to avoid deep copy
        List<HierarchyInfo> newUnderlyingLessonAndTextbookUnitRelationList = new ArrayList<>();
        newUnderlyingLessonAndTextbookUnitRelationList.addAll(underlyingLessonAndTextbookUnitRelationList);

        HierarchyInfo parentContentInfo = new HierarchyInfo(eachGameInTuList.getIdentifier(), eachGameInTuList.getContentData().getContentType());
        newUnderlyingLessonAndTextbookUnitRelationList.add(parentContentInfo);

        //create new underlyingGamesList to avoid deep copy
        List<Content> newUnderLyingGamesList = new ArrayList<>();
        newUnderLyingGamesList.addAll(underlyingGamesList);

        //get the textbook unit list from current text book unit
        List<Content> underlyingTuList = eachGameInTuList.getChildren();

        //first check if this TU has more TUs inside it
        if (underlyingTuList != null && underlyingTuList.size() > 0) {
            for (Content eachUnderlyingTextbookUnit : underlyingTuList) {
                getAllLessonsFromUnderlyingTextbookUnit(newUnderLyingGamesList, eachUnderlyingTextbookUnit, newUnderlyingLessonAndTextbookUnitRelationList);
            }
        }

        //get the lesson for the current text book unit
        List<Content> underlyingLessonsForThisTu = eachGameInTuList.getChildren();

        //add all those lessons for this TU
        if (underlyingLessonsForThisTu != null && underlyingLessonsForThisTu.size() > 0) {
            for (Content perLesson : underlyingLessonsForThisTu) {
                perLesson.setHierarchyInfo(newUnderlyingLessonAndTextbookUnitRelationList);
            }
            newUnderLyingGamesList.addAll(underlyingLessonsForThisTu);
        }

        return newUnderLyingGamesList;
    }

    /**
     * This method helps in generating TOC view from the root level
     *
     * @param content
     * @param refreshLayout
     */
    private void generateTOC(Content content, boolean refreshLayout) {
        int level = 1;
        TreeNode tree = TreeNode.root();

        //0 represents the root node for the tree
        TreeNode root = new TreeNode(new TreeItemHolder.IconTreeItem(mContext.getString(R.string.label_textbook_table_of_contents), id, 0, -1));

        tree.addChildren(constructTOC(root, content, level, -1));
        mTextbookView.showTableOfContents(tree);

        if (refreshLayout) {
            getPreviousLearnerState();
        }
    }

    /**
     * This method adds the respective intermediate nodes in the TOC view, to the root node
     *
     * @param root
     * @param parentContent
     * @param level
     * @param tocFirstLevelItemPosition
     * @return
     */
    private TreeNode constructTOC(TreeNode root, Content parentContent, int level, int tocFirstLevelItemPosition) {
        //check if the parent has any children
        List<Content> childContentsList = parentContent.getChildren();
        if (childContentsList != null && childContentsList.size() > 0) {
            for (Content perChildContent : childContentsList) {
                String unitName = perChildContent.getContentData().getName();

                //this counter is used to maintain the positions of only the first level items as they appear in TOC tree view
                //so that when any leaf item in the tree is clicked, it will be easy to get from which first level node it came from
                if (level == 1) {
                    tocFirstLevelItemPosition++;
                }

                if (perChildContent.getContentType().equalsIgnoreCase(CONTENT_TYPE_TEXTBOOKUNIT)) {
                    TreeNode unit = new TreeNode(new TreeItemHolder.IconTreeItem(unitName, id, level, tocFirstLevelItemPosition));

                    //add this unit to tableOfContents
                    root.addChild(unit);

                    if (perChildContent.getChildren() != null && perChildContent.getChildren().size() > 0) {
                        level++;
                        constructTOC(unit, perChildContent, level, tocFirstLevelItemPosition);
                        level--;
                    }
                }
            }

        }

        return root;
    }

    private void getPreviousLearnerState() {
        mUserService.getCurrentUserSession(new IResponseHandler<UserSession>() {
            @Override
            public void onSuccess(GenieResponse<UserSession> genieResponse) {
                mUserSession = genieResponse.getResult();
                fetchLearnerState(mUserSession);
            }

            @Override
            public void onError(GenieResponse<UserSession> genieResponse) {

            }
        });
    }

    private void fetchLearnerState(UserSession userSession) {
        if (userSession != null) {
            ContentAccessFilterCriteria.Builder builder = new ContentAccessFilterCriteria.Builder().byUser(userSession.getUid()).forContent(mContent.getIdentifier());

            mUserService.getAllContentAccess(builder.build(), new IResponseHandler<List<ContentAccess>>() {
                @Override
                public void onSuccess(GenieResponse<List<ContentAccess>> genieResponse) {
                    List<ContentAccess> contentAccesses = genieResponse.getResult();

                    if (contentAccesses != null && contentAccesses.size() > 0) {
                        if (contentAccesses.get(0).getContentLearnerState().getLearnerState() != null) {
                            Map<String, Object> learnerState = contentAccesses.get(0).getContentLearnerState().getLearnerState();
                            String sectionIdentifier = (String) learnerState.get(SECTION_IDENTIFIER);
                            LogUtil.e(TAG + "@getPreviousLearnerState", "Previous learner state - " + sectionIdentifier);
                            getPositionOfTextbookIdentifier(sectionIdentifier);
                        } else {
                            getPositionOfTextbookIdentifier(null);
                        }
                    }
                }

                @Override
                public void onError(GenieResponse<List<ContentAccess>> genieResponse) {

                }
            });
        }
    }

    private void getPositionOfTextbookIdentifier(String tuIdentifier) {

        if (mTextbookSectionsList != null && mTextbookSectionsList.size() > 0) {
            int positionOfItemInToc = 0;

            if (tuIdentifier != null) {
                for (TextbookSection currentSection : mTextbookSectionsList) {
                    if (currentSection.getCurrentSectionContentDetails() != null && currentSection.getCurrentSectionContentDetails().getIdentifier().equalsIgnoreCase(tuIdentifier)) {
                        break;
                    }
                    positionOfItemInToc++;
                }
                generateLocalCount(mTextbookSectionsList, positionOfItemInToc);

                if (mTextbookView.getLinearLayoutManager() != null && positionOfItemInToc != -1) {
                    mTextbookView.scrollTo(positionOfItemInToc);
                }
            } else {
                generateLocalCount(mTextbookSectionsList, positionOfItemInToc);
            }


        }
    }

    @Override
    public void handleBackButtonClick() {

        if (mSectionMapList.size() > 0) {
            mTextbookView.generateSectionViewedEvent(mContentData.getIdentifier(), mSectionMapList);
        }


        mTextbookView.finish();
    }

    @Override
    public void downloadedLessons() {
        showDownloadedLessons(mLocalPath, mIsFromDownloadsScreen);
    }

    public void showDownloadedLessons(String mLocalPath, boolean mIsFromDownloadsScreen) {
        int count = 0;
        if (mTextbookSectionsList != null && mTextbookSectionsList.size() > 0) {
            for (TextbookSection textbookSection : mTextbookSectionsList) {
                List<Content> eachSectionLessons = textbookSection.getSectionContents();
                if (eachSectionLessons != null && eachSectionLessons.size() > 0) {
                    for (Content lesson : eachSectionLessons) {
                        if (lesson.isAvailableLocally()) {
                            count++;
                        }
                    }
                }
            }

            if (count > 0) {
                DownloadedTextbooksFragment downloadedTextbooksFragment =
                        DownloadedTextbooksFragment.newInstance(mContent,
                                mLocalPath, mIsFromDownloadsScreen);
                ((TextbookActivity) mContext).setFragment(downloadedTextbooksFragment,
                        true, R.id.fragment_container, true);
            } else {
                Util.showCustomToast(mContext.getString(R.string.error_textbook_no_downloaded_lessons));
            }
        } else {
            Util.showCustomToast(mContext.getString(R.string.error_textbook_no_downloaded_lessons));
        }
    }

    @Override
    public void handleDetailsClick() {
//        mTextbookView.showTextBookDetails(mContent, mLocalPath, true);
        mTextbookView.showDetails(mContent, null, mIsFromDownloadsScreen, true, true, true);
    }

    @Override
    public void handleProgressClick(Content content) {
        mTextbookView.showProgressReportActivity(content);
    }


    @Override
    public void handleMoreClick(final Content content) {
        ContentDetailsRequest.Builder detailsRequest = new ContentDetailsRequest.Builder().forContent(content.getIdentifier()).withContentAccess().withFeedback();
        mContentService.getContentDetails(detailsRequest.build(), new IResponseHandler<Content>() {
            @Override
            public void onSuccess(GenieResponse<Content> genieResponse) {
                Content selectedContent = genieResponse.getResult();
                selectedContent.setHierarchyInfo(content.getHierarchyInfo());
                DialogUtils.showMoreDialog(mContext, selectedContent, TextbookPresenter.this);
            }

            @Override
            public void onError(GenieResponse<Content> genieResponse) {
                Log.e(TAG, "error " + genieResponse.getErrorMessages().get(0));
            }
        });
    }

    @Override
    public void handleContentClick(Content content) {
        if (content.isAvailableLocally()) {
            PlayerUtil.startContent(mContext, content, TelemetryStageId.CONTENT_DETAIL, mIsFromDownloadsScreen);
        } else {
            mTextbookView.showDetails(content, (ArrayList) content.getHierarchyInfo(), mIsFromDownloadsScreen, true, true, false);
        }
    }

    @Override
    public void handleDeleteClick(Content content) {
        mContentToBeDeleted = content;
        ContentUtil.showDeleteContentConfirmationDialog(mContext, this, content.getReferenceCount(), content.getIdentifier());
    }

    @Override
    public void handleTreeNodeClick(TreeNode node, Object value) {
        TreeItemHolder.IconTreeItem item = (TreeItemHolder.IconTreeItem) value;
        if (item.text.equalsIgnoreCase(mContext.getString(R.string.label_textbook_table_of_contents))) {
            if (!node.isExpanded()) {
                mTextbookView.generateTOCInteractEvent(mContentData.getIdentifier());
            }
        } else {
            if (mTextbookView.getAndroidTreeView() != null) {
                mTextbookView.collapseAllLeaf();
            }

            if (mTextbookView.getLinearLayoutManager() != null && item.tocFirstLevelItemPosition != -1) {
                mTextbookView.scrollTo(item.tocFirstLevelItemPosition);
            }
        }
    }

    @Override
    public void setLearnerState(LinearLayoutManager linearLayoutManager) {
        if (linearLayoutManager != null && mTextbookSectionsList != null && mTextbookSectionsList.size() > 0) {
            String lastVisibleSectionIdentifier = mTextbookSectionsList.get(linearLayoutManager.findLastVisibleItemPosition()).getCurrentSectionContentDetails().getIdentifier();

            LogUtil.e(TAG, "Textbook Identifier - " + mContentData.getIdentifier() + " Last Visible Section Identifier - " + lastVisibleSectionIdentifier);

            Map<String, Object> learnerState = new HashMap<>();
            learnerState.put(SECTION_IDENTIFIER, lastVisibleSectionIdentifier);

            ContentLearnerState contentLearnerState = new ContentLearnerState();
            contentLearnerState.setLearnerState(learnerState);

            ContentAccess contentAccess = new ContentAccess();
            contentAccess.setStatus(1);
            contentAccess.setContentId(mContentData.getIdentifier());
            contentAccess.setContentLearnerState(contentLearnerState);

            mUserService.addContentAccess(contentAccess, new IResponseHandler<Void>() {
                @Override
                public void onSuccess(GenieResponse<Void> genieResponse) {
                    LogUtil.e(TAG + "@setLearnerState", "saved learner state -" + genieResponse.getStatus());
                }

                @Override
                public void onError(GenieResponse<Void> genieResponse) {

                }
            });

        }
    }

    @Override
    public void checkAndGetTableOfContentsAndLessonsFromApi(boolean isRequiredToCallApi) {
        if (isRequiredToCallApi) {
            mTextbookView.setIsRequiredToCallApi(false);
            fetchTableOfContentsAndLessons(true);
        } else {
            fetchTableOfContentsAndLessons(false);
        }
    }

    @Override
    public void deleteContent(final String identifier) {
        if (!TextUtils.isEmpty(identifier) && mContentToBeDeleted != null) {
            ContentDeleteRequest.Builder builder = new ContentDeleteRequest.Builder();
            builder.add(new ContentDelete(identifier, true));

            mContentService.deleteContent(builder.build(), new IResponseHandler<List<org.ekstep.genieservices.commons.bean.ContentDeleteResponse>>() {
                @Override
                public void onSuccess(GenieResponse<List<org.ekstep.genieservices.commons.bean.ContentDeleteResponse>> genieResponse) {
                    //manage delete content success
                    manageContentDeleteSuccess(identifier);
                }

                @Override
                public void onError(GenieResponse<List<org.ekstep.genieservices.commons.bean.ContentDeleteResponse>> genieResponse) {
                    LogUtil.e(TAG, "error deleting content");
                }
            });

            //recurse as long as the Content object has textbook units in it
            if (mContentToBeDeleted.getChildren() != null && mContentToBeDeleted.getChildren().size() > 0) {
//                for (Content eachContent : content.getChildren()) {
                for (int i = 0; i < mContentToBeDeleted.getChildren().size(); i++) {
                    Content eachContent = mContentToBeDeleted.getChildren().get(i);
                    if (mContentToBeDeleted.getChildren().get(i).getIdentifier().equalsIgnoreCase(identifier)) {
                        mContentToBeDeleted = eachContent;
                        deleteContent(identifier);
                    }
                }
            }
        }
    }

    @Override
    public void generateLocalCount(List<TextbookSection> textbookSectionList, int offset) {
        if (isNeededToGenerateHomeInteractEvent) {
            int localCount = 0;
            int totalCount = 0;
            if (textbookSectionList != null) {
                for (TextbookSection textbookSection : textbookSectionList) {
                    List<Content> sectionList = textbookSection.getSectionContents();
                    if (sectionList != null) {
                        totalCount += sectionList.size();
                        for (Content section : sectionList) {
                            if (section.isAvailableLocally()) {
                                localCount++;
                            }
                        }
                    }
                }
            }

            generateHomeInteractEvent(mContentData.getIdentifier(), localCount, totalCount, offset);
        }
    }

    @Override
    public void handleScrollChanged(RecyclerView recyclerView, int newState) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            TextbookAdapter textbookAdapter = (TextbookAdapter) recyclerView.getAdapter();
            LogUtil.i(TAG, "Scrolling Stopped");
            LogUtil.i(TAG, layoutManager.findLastVisibleItemPosition() + "");
            if (textbookAdapter != null) {
                String sectionName = textbookAdapter.getItem(layoutManager.findLastVisibleItemPosition()).getSectionName();
                Map<String, Object> sectionMap = new HashMap<>();
                sectionMap.put("sectionName", sectionName);
                sectionMap.put("time", DateUtil.getCurrentTimestamp());
                mSectionMapList.add(sectionMap);
            }
        }
    }

    @Override
    public void manageImportSuccess(String identifier) {
        if (!StringUtil.isNullOrEmpty(identifier)) {
            updateTextbookSectionList(mTextbookSectionsList, identifier, true);
            mTextbookView.refreshAdapter(mTextbookSectionsList);

            //show downloaded lessons button, because for the first time, it will be hidden
            mTextbookView.showDownloadedLessonText();
            mTextbookView.showTickImage();
        }
    }

    @Override
    public void updateTextbookSectionList(List<TextbookSection> textbookSectionList, String identifier, boolean isAvailableLocally) {
        if (textbookSectionList != null && textbookSectionList.size() > 0 && !StringUtil.isNullOrEmpty(identifier)) {
            for (TextbookSection eachTextbookSection : textbookSectionList) {
                if (eachTextbookSection.getSectionContentIdentifiers().contains(identifier)) {
                    for (Content eachContentWithinTextbookSection : eachTextbookSection.getSectionContents()) {
                        if (eachContentWithinTextbookSection.getIdentifier().equalsIgnoreCase(identifier)) {
                            eachContentWithinTextbookSection.setAvailableLocally(isAvailableLocally);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void handleFeedbackClick(Content content) {
        ContentDetailsRequest.Builder detailsRequest = new ContentDetailsRequest.Builder().forContent(content.getIdentifier()).withFeedback();
        mContentService.getContentDetails(detailsRequest.build(), new IResponseHandler<Content>() {
            @Override
            public void onSuccess(GenieResponse<Content> genieResponse) {
                Content selectedContent = genieResponse.getResult();
                mTextbookView.showFeedbackDialog(selectedContent.getIdentifier(), ContentUtil.getPreviousRating(selectedContent.getContentFeedback()));
            }

            @Override
            public void onError(GenieResponse<Content> genieResponse) {
            }
        });
    }

    @Override
    public void handleViewDetailsClick(Content content) {
        mTextbookView.showDetails(content, content.getHierarchyInfo(), mIsFromDownloadsScreen, true, true, false);
    }

    @Override
    public void manageContentDeleteSuccess(String identifier) {
        if (mTextbookView == null) {
            return;
        }

        if (mContentToBeDeleted != null) {
            mTextbookView.showCustomToast(mContext.getResources().getString(R.string.msg_content_deletion_sucessfull));

            if (mContentToBeDeleted.getContentType().equalsIgnoreCase(ContentType.COLLECTION)
                    || mContentToBeDeleted.getContentType().equalsIgnoreCase(ContentType.TEXTBOOK)) {
                return;
            }
        }

        updateTextbookSectionList(mTextbookSectionsList, identifier, false);

        // refresh adapter
        mTextbookView.refreshAdapter(mTextbookSectionsList);
    }

    @Override
    public void manageImportAndDeleteSuccess(Object response) {
        if (response instanceof ContentImportResponse) {
            manageImportSuccess(((ContentImportResponse) response).getIdentifier());
        } else {
            manageContentDeleteSuccess(((ContentDeleteResponse) response).getIdentifier());
        }
        EventBus.removeStickyEvent(response);
    }

    @Override
    public void removeContentToBeDeletedValue() {
        mContentToBeDeleted = null;
    }

    /**
     * This method checks if to show the progress bar or the download chapter button
     *
     * @param contentList
     * @param downloadChapterButton
     * @param progressBarChapterDownload
     */
    public void checkToShowProgressOrDownloadChapterButton(List<Content> contentList, View downloadChapterButton, ProgressBar progressBarChapterDownload) {
        int numberOfNotDownloadedContents = 0;

        for (Content content : contentList) {
            if (!content.isAvailableLocally()) {
                ContentData contentData = content.getContentData();
                if (contentData != null) {
                    numberOfNotDownloadedContents++;
                }
            }
        }

        if (numberOfNotDownloadedContents > 0) {
            downloadChapterButton.setVisibility(View.VISIBLE);
            progressBarChapterDownload.setVisibility(View.INVISIBLE);
        } else {
            downloadChapterButton.setVisibility(View.INVISIBLE);
            progressBarChapterDownload.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Calculate the size of remaining contents to be downloaded
     *
     * @param gameList
     * @param sectionName
     * @param chapterDownloadButton
     * @param progressBarChapterDownload
     */
    public void calculateSize(List<Content> gameList, String sectionName, ImageButton chapterDownloadButton, ProgressBar progressBarChapterDownload) {
        long availableSizeLeft = 0;
        int numberOfNotDownloadedContents = 0;
        List<Content> contentsToBeDownloaded = new ArrayList<>();
        for (Content content : gameList) {
            if (!content.isAvailableLocally()) {
                ContentData contentData = content.getContentData();
                String contentSize = ContentUtil.getContentSize(contentData);
                if (contentData != null && !StringUtil.isNullOrEmpty(contentSize)) {
                    long size = new BigDecimal(contentSize).longValue();
                    availableSizeLeft += size;
                }
                contentsToBeDownloaded.add(content);
                numberOfNotDownloadedContents++;
            }
        }
        if (numberOfNotDownloadedContents > 0) {
            mTextbookView.showDownloadDialog(contentsToBeDownloaded, numberOfNotDownloadedContents, sectionName, String.valueOf(Util.humanReadableByteCount(availableSizeLeft, true)), chapterDownloadButton, progressBarChapterDownload);
        }
    }

    /**
     * Downloads all the Contents passed to it, by using the content identifiers.
     *
     * @param contentList
     */
    @Override
    public void downloadAll(List<Content> contentList) {
        ContentImportRequest.Builder contentImportRequest = new ContentImportRequest.Builder();
        if (contentList != null && contentList.size() > 0) {
            for (int i = 0; i < contentList.size(); i++) {
                Content content = contentList.get(i);
                ContentData contentData = content.getContentData();
                ContentImport contentImport = new ContentImport(contentData.getIdentifier(), true, FileHandler.getDefaultStoragePath(mContext));
                contentImport.setCorrelationData(PreferenceUtil.getCoRelationList());
                contentImportRequest.add(contentImport);
                addDownloadQueueItem(contentData);
            }

            mContentService.importContent(contentImportRequest.build(), new IResponseHandler<List<ContentImportResponse>>() {
                @Override
                public void onSuccess(GenieResponse<List<ContentImportResponse>> genieResponse) {

                }

                @Override
                public void onError(GenieResponse<List<ContentImportResponse>> genieResponse) {

                }
            });
        }
    }

    private void addDownloadQueueItem(ContentData contentData) {
        String contentSize = null;
        if (contentData != null && !StringUtil.isNullOrEmpty(contentData.getSize())) {
            long size = new BigDecimal(contentData.getSize()).longValue();
            contentSize = String.valueOf(Util.humanReadableByteCount(size, true));
        }
        ContentUtil.addDownloadQueueItem(contentData.getIdentifier(), contentData.getName(),
                contentSize, mContentData.getIdentifier(), mContentData.getName(), 0);
    }

    /**
     * This method checks if any of the contents of the section are being downloaded currently
     *
     * @param textbookSection
     * @return
     */
    public boolean isDownloadingAnyChapter(TextbookSection textbookSection) {
        for (Content content : textbookSection.getSectionContents()) {
            ContentImportResponse response = mSyncContentService.getImportStatus(content.getIdentifier()).getResult();
            if (response.getStatus().getValue() == ContentImportStatus.DOWNLOAD_STARTED.getValue()) {
                LogUtil.e(TAG + "@isDownloadingAnyChapter", "Something is getting downloaded in - " + textbookSection.getSectionName());
                return true;
            }
        }

        return false;
    }

    @Override
    public void generateHomeInteractEvent(String identifier, int localCount, int totalCount, int position) {
        Map<String, Object> eks = new HashMap<>();
        eks.put(TelemetryConstant.CONTENT_LOCAL_COUNT, "" + localCount);
        eks.put(TelemetryConstant.CONTENT_TOTAL_COUNT, "" + totalCount);
        eks.put(TelemetryConstant.POSITION, "" + position);
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.SHOW, TelemetryStageId.TEXTBOOK_HOME, EntityType.CONTENT_ID, identifier, eks));
    }

    @Override
    public void bindView(BaseView view, Context context) {
        mTextbookView = (TextbookContract.View) view;
        mContext = context;
    }

    @Override
    public void unbindView() {
        mTextbookView = null;
        mContext = null;
    }
}