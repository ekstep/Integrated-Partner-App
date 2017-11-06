package org.ekstep.genie.util;

import android.content.Context;
import android.content.Intent;

import org.ekstep.genie.model.enums.ContentType;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryConstant;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryUtil;
import org.ekstep.genie.ui.collection.CollectionsActivity;
import org.ekstep.genie.ui.textbook.TextbookActivity;
import org.ekstep.genie.util.geniesdk.ContentUtil;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.CorrelationData;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.utils.ContentPlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created on 4/30/2016.
 *
 * @author swayangjit_gwl
 */
public class PlayerUtil {

    private static final String TAG = PlayerUtil.class.getSimpleName();

    public static void startContent(Context context, Content content, String stageId, boolean isFromDownloads, boolean isRoot) {
        playContent(context, content, stageId, isFromDownloads, isRoot);
    }

    public static void startContent(Context context, Content content, String stageId, boolean isFromDownloads) {
        playContent(context, content, stageId, isFromDownloads, false);
    }

    private static void playContent(final Context context, final Content content, final String stageId, final boolean isFromDownloads, final boolean isRoot) {
        List<CorrelationData> cdata = TelemetryUtil.computeCData(content.getHierarchyInfo());

        if (content.getContentType().equalsIgnoreCase(ContentType.COLLECTION) || content.getContentType().equalsIgnoreCase(ContentType.TEXTBOOK)) {
            // This content is a collection so generate GE_GAME_LAUNCH and start CollectionActivity.
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, stageId, TelemetryConstant.CONTENT_PLAY, content.getIdentifier(), (Map) new HashMap<>(), cdata), new IResponseHandler() {
                @Override
                public void onSuccess(GenieResponse genieResponse) {
                    Util.processSuccess(genieResponse);
                    if (content.getContentType().equalsIgnoreCase(ContentType.COLLECTION)) {
                        startActivity(context, CollectionsActivity.class, content, content.getBasePath(), isFromDownloads, isRoot);
                    } else if (content.getContentType().equalsIgnoreCase(ContentType.TEXTBOOK)) {
                        startActivity(context, TextbookActivity.class, content, content.getBasePath(), isFromDownloads, isRoot);
                    }

                }

                @Override
                public void onError(GenieResponse genieResponse) {
                    Util.processFailure(genieResponse);
                }
            });


        } else {

            if (TelemetryUtil.isFromCollectionOrTextBook(content.getHierarchyInfo())) {
                //Content is originated from collection/Textbook so dont generate GE_LAUNCH_GAME only launch the content.
                ContentPlayer.play(context, content, ContentLanguageJsonCreator.createLanguageBundleMap());
            } else {

                //Content is not originated from collection/Textbook so  generate GE_LAUNCH_GAME and  launch the content.
                TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, stageId, TelemetryConstant.CONTENT_PLAY, content.getIdentifier(), (Map) new HashMap<>(), cdata), new IResponseHandler() {
                    @Override
                    public void onSuccess(GenieResponse genieResponse) {
                        ContentUtil.addContentAccess(content);

                        Util.processSuccess(genieResponse);
                        ContentPlayer.play(context, content, ContentLanguageJsonCreator.createLanguageBundleMap());
                    }

                    @Override
                    public void onError(GenieResponse genieResponse) {
                        Util.processFailure(genieResponse);
                    }
                });

            }
        }
    }

    private static void startActivity(Context context, Class activity, Content content, String path, boolean isFromDownloads, boolean isRoot) {
        Intent intent = new Intent(context, activity);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_CONTENT, content);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_LOCAL_PATH, path);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_IS_FROM_DOWNLOADS, isFromDownloads);
        if (isRoot) {
            intent.putExtra(Constant.EXTRAS_IS_ROOT, isRoot);
        }
        context.startActivity(intent);
    }


}
