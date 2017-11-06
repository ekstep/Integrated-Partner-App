package org.ekstep.genie.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;

import org.ekstep.genie.BuildConfig;
import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.customview.EkStepCustomEditText;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.ui.collection.CollectionContract;
import org.ekstep.genie.ui.collection.CollectionPresenter;
import org.ekstep.genie.ui.contentdetail.ContentDetailPresenter;
import org.ekstep.genie.ui.mycontent.MyContentContract;
import org.ekstep.genie.ui.mycontent.MyContentPresenter;
import org.ekstep.genie.ui.textbook.DownloadedTextbooksContract;
import org.ekstep.genie.ui.textbook.DownloadedTextbooksPresenter;
import org.ekstep.genie.ui.textbook.TextbookContract;
import org.ekstep.genie.ui.textbook.TextbookPresenter;
import org.ekstep.genie.util.geniesdk.ContentUtil;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.ContentAccess;
import org.ekstep.genieservices.commons.bean.ContentFeedback;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.utils.CollectionUtil;
import org.ekstep.genieservices.commons.utils.StringUtil;

import java.util.List;

public class DialogUtils {


    /**
     * This utility method will be called from Downloads when clicked on More Icon in each item.
     * <p>
     * Since this only called from Downloads now, and so is the method name, if this will be called from anywhere else,
     * the method name should be changed to generic
     *
     * @param context
     * @param content
     * @param basePresenter
     */
    public static void showMoreDialog(final Context context, final Content content, final BasePresenter basePresenter) {
        final Dialog mMoreActionDialog = new Dialog(context);
        mMoreActionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mMoreActionDialog.setContentView(R.layout.dialog_content_action);

        ((TextView) mMoreActionDialog.findViewById(R.id.txt_content_title)).setText(content.getContentData().getName());


        View view = mMoreActionDialog.findViewById(R.id.layout_feedback);
        View view_feedback = mMoreActionDialog.findViewById(R.id.view_feedback);

        int status = 0;
        List<ContentAccess> contentAccessList = content.getContentAccess();
        if (!CollectionUtil.isNullOrEmpty(contentAccessList)) {
            ContentAccess contentAccess = contentAccessList.get(0);
            status = contentAccess.getStatus().getValue();
        }
        if (status == 0) {
            view.setVisibility(View.GONE);
            view_feedback.setVisibility(View.GONE);
        }

        if (!ContentUtil.isContentLive(content.getContentData().getStatus())) {
            mMoreActionDialog.findViewById(R.id.layout_share).setVisibility(View.GONE);
            mMoreActionDialog.findViewById(R.id.view_share).setVisibility(View.GONE);
        }

        if (basePresenter instanceof TextbookPresenter || basePresenter instanceof CollectionPresenter || basePresenter instanceof DownloadedTextbooksPresenter) {
            mMoreActionDialog.findViewById(R.id.layout_share).setVisibility(View.GONE);
            mMoreActionDialog.findViewById(R.id.view_share).setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);
            view_feedback.setVisibility(View.VISIBLE);
//            mMoreActionDialog.findViewById(R.id.layout_report_progress).setVisibility(View.GONE);
//            mMoreActionDialog.findViewById(R.id.view_progress).setVisibility(View.GONE);
        }


        //view details
        mMoreActionDialog.findViewById(R.id.layout_view_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMoreActionDialog.dismiss();
                if (basePresenter instanceof MyContentContract.Presenter) {
                    MyContentContract.Presenter myContentPresenter = (MyContentContract.Presenter) basePresenter;
                    myContentPresenter.handleContentDetailClick(content);
                } else if (basePresenter instanceof TextbookContract.Presenter) {
                    TextbookContract.Presenter textbookPresenter = (TextbookContract.Presenter) basePresenter;
                    textbookPresenter.handleViewDetailsClick(content);
                } else if (basePresenter instanceof CollectionContract.Presenter) {
                    CollectionContract.Presenter collectPresenter = (CollectionContract.Presenter) basePresenter;
                    collectPresenter.handleViewDetailsClick(content);
                } else if (basePresenter instanceof DownloadedTextbooksContract.Presenter) {
                    DownloadedTextbooksContract.Presenter downloadedTextbookPresenter = (DownloadedTextbooksContract.Presenter) basePresenter;
                    downloadedTextbookPresenter.handleDetailsClick(content);
                }
            }
        });

        //share
        mMoreActionDialog.findViewById(R.id.layout_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMoreActionDialog.dismiss();
                if (basePresenter instanceof MyContentContract.Presenter) {
                    MyContentContract.Presenter myContentPresenter = (MyContentContract.Presenter) basePresenter;
                    myContentPresenter.handleShareClick(content);
                }
            }
        });

        //progress
        mMoreActionDialog.findViewById(R.id.layout_report_progress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMoreActionDialog.dismiss();
                if (basePresenter instanceof MyContentContract.Presenter) {
                    MyContentContract.Presenter myContentPresenter = (MyContentContract.Presenter) basePresenter;
                    myContentPresenter.handleProgressClick(content);
                } else if (basePresenter instanceof CollectionPresenter) {
                    CollectionContract.Presenter collectPresenter = (CollectionContract.Presenter) basePresenter;
                    collectPresenter.handleProgressClick(content);
                } else if (basePresenter instanceof TextbookPresenter) {
                    TextbookContract.Presenter textPresenter = (TextbookContract.Presenter) basePresenter;
                    textPresenter.handleProgressClick(content);
                }
            }
        });


        //feedback
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMoreActionDialog.dismiss();
                if (basePresenter instanceof MyContentContract.Presenter) {
                    MyContentContract.Presenter myContentPresenter = (MyContentContract.Presenter) basePresenter;
                    myContentPresenter.handleFeedbackClick(content);
                } else if (basePresenter instanceof TextbookPresenter) {
                    TextbookContract.Presenter textbookPresenter = (TextbookContract.Presenter) basePresenter;
                    textbookPresenter.handleFeedbackClick(content);
                } else if (basePresenter instanceof CollectionContract.Presenter) {
                    CollectionContract.Presenter collectPresenter = (CollectionContract.Presenter) basePresenter;
                    collectPresenter.handleFeedbackClick(content);
                } else if (basePresenter instanceof DownloadedTextbooksContract.Presenter) {
                    DownloadedTextbooksContract.Presenter downloadedTextbookPresenter = (DownloadedTextbooksContract.Presenter) basePresenter;
                    downloadedTextbookPresenter.handleFeedbackClick(content);
                }
            }
        });

        //delete
        mMoreActionDialog.findViewById(R.id.layout_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMoreActionDialog.dismiss();
                if (basePresenter instanceof MyContentContract.Presenter) {
                    MyContentContract.Presenter myContentPresenter = (MyContentContract.Presenter) basePresenter;
                    myContentPresenter.handleDeleteClick(content);
                } else if (basePresenter instanceof TextbookPresenter) {
                    TextbookContract.Presenter textbookPresenter = (TextbookContract.Presenter) basePresenter;
                    textbookPresenter.handleDeleteClick(content);
                } else if (basePresenter instanceof CollectionContract.Presenter) {
                    CollectionContract.Presenter collectPresenter = (CollectionContract.Presenter) basePresenter;
                    collectPresenter.handleDeleteClick(content);
                } else if (basePresenter instanceof DownloadedTextbooksContract.Presenter) {
                    DownloadedTextbooksContract.Presenter downloadedTextbookPresenter = (DownloadedTextbooksContract.Presenter) basePresenter;
                    downloadedTextbookPresenter.handleContentDeleteClick(content);
                }
            }
        });

        mMoreActionDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        mMoreActionDialog.setCancelable(true);
        DeviceUtility.displayFullScreenDialog(mMoreActionDialog, (Activity) context);
    }

    /**
     * Dialog to upgrade genie.
     *
     * @param context
     */
    public static void showDialogUpgradeGenie(final Context context, boolean forceUpdate) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_upgrade_genie);
        dialog.setCancelable(false);

        // if button is clicked, close the custom dialog
        dialog.findViewById(R.id.txt_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)));
            }
        });

        if (forceUpdate) {
            dialog.findViewById(R.id.txt_no).setVisibility(View.INVISIBLE);
        } else {
            dialog.findViewById(R.id.txt_no).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        dialog.show();
    }

    public static void showFeedbackDialog(final Context context, final BasePresenter presenter, String identifier, float previousRating) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_feedback);

        final RatingBar ratingFeedback = (RatingBar) dialog.findViewById(R.id.ratingFeedback);
        ratingFeedback.setRating(previousRating);
        final EkStepCustomEditText edtComments = (EkStepCustomEditText) dialog.findViewById(R.id.edt_comments);
        final ContentFeedback feedBack = new ContentFeedback();
        feedBack.setContentId(identifier);

        if (presenter instanceof MyContentPresenter) {
            feedBack.setStageId(TelemetryStageId.MY_CONTENT);
        } else if (presenter instanceof ContentDetailPresenter || presenter instanceof CollectionPresenter || presenter instanceof TextbookPresenter
                || presenter instanceof DownloadedTextbooksPresenter) {
            ratingFeedback.setRating(previousRating);
            feedBack.setStageId(TelemetryStageId.CONTENT_DETAIL);
        }

        dialog.findViewById(R.id.txt_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ratingFeedback.getRating() == 0 && isRatingFeedbackTextEmpty(edtComments.getText().toString())) {
                    return;
                }

                dialog.dismiss();
                feedBack.setRating(ratingFeedback.getRating());
                feedBack.setComments(edtComments.getText().toString());
                CoreApplication.getGenieAsyncService().getContentService().sendFeedback(feedBack, new IResponseHandler<Void>() {
                    @Override
                    public void onSuccess(GenieResponse<Void> genieResponse) {
                        if (presenter instanceof ContentDetailPresenter) {
                            ContentDetailPresenter contentDetailPresenter = (ContentDetailPresenter) presenter;
                            contentDetailPresenter.handleFeedBackSuccess(feedBack.getRating(), true);
                        } else {
                            Util.showCustomToast(R.string.msg_feedback_successfull);
                        }

                    }

                    @Override
                    public void onError(GenieResponse<Void> genieResponse) {

                    }
                });
            }
        });

        DeviceUtility.displayFullScreenDialog(dialog, (Activity) context);
    }

    private static boolean isRatingFeedbackTextEmpty(String rating) {
        if (StringUtil.isNullOrEmpty(rating)) {
            return true;
        } else if (rating.trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * This utility method will be called from Downloads when clicked on More Icon in each item.
     * <p>
     * Since this only called from Downloads now, and so is the method name, if this will be called from anywhere else,
     * the method name should be changed to generic
     *
     * @param context
     * @param content
     * @param basePresenter
     */
    public static void showDownloadsMoreDialog(final Context context, final Content content, final BasePresenter basePresenter) {
        final Dialog mMoreActionDialog = new Dialog(context);
        mMoreActionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mMoreActionDialog.setContentView(R.layout.dialog_download_content_action);

        ((TextView) mMoreActionDialog.findViewById(R.id.download_dialog_title)).setText(content.getContentData().getName());

        if (content.getHierarchyInfo() != null) {
            mMoreActionDialog.findViewById(R.id.download_share).setVisibility(View.GONE);
            mMoreActionDialog.findViewById(R.id.download_progress).setVisibility(View.GONE);
        }

        //view details
        mMoreActionDialog.findViewById(R.id.download_view_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMoreActionDialog.dismiss();
                if (basePresenter instanceof MyContentContract.Presenter) {
                    MyContentContract.Presenter myContentPresenter = (MyContentContract.Presenter) basePresenter;
                    myContentPresenter.handleContentDetailClick(content);
                }
            }
        });

        //share
        mMoreActionDialog.findViewById(R.id.download_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMoreActionDialog.dismiss();
                if (basePresenter instanceof MyContentContract.Presenter) {
                    MyContentContract.Presenter myContentPresenter = (MyContentContract.Presenter) basePresenter;
                    myContentPresenter.handleShareClick(content);
                }
            }
        });


        //delete
        mMoreActionDialog.findViewById(R.id.download_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMoreActionDialog.dismiss();
                if (basePresenter instanceof MyContentContract.Presenter) {
                    MyContentContract.Presenter myContentPresenter = (MyContentContract.Presenter) basePresenter;
                    myContentPresenter.handleDeleteClick(content);
                }
            }
        });


        //progress
        mMoreActionDialog.findViewById(R.id.download_progress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMoreActionDialog.dismiss();
                if (basePresenter instanceof MyContentContract.Presenter) {
                    MyContentContract.Presenter myContentPresenter = (MyContentContract.Presenter) basePresenter;
                    myContentPresenter.handleProgressClick(content);
                }
            }
        });


        //feedback
        mMoreActionDialog.findViewById(R.id.download_feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMoreActionDialog.dismiss();
                if (basePresenter instanceof MyContentContract.Presenter) {
                    MyContentContract.Presenter myContentPresenter = (MyContentContract.Presenter) basePresenter;
                    myContentPresenter.handleFeedbackClick(content);
                }
            }
        });


        mMoreActionDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        mMoreActionDialog.setCancelable(true);
        DeviceUtility.displayFullScreenDialog(mMoreActionDialog, (Activity) context);
    }

}
