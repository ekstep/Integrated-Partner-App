package org.ekstep.ipa.model;

/**
 * @author vinayagasundar
 */

public class IntroModel {

    private int mImageResId;
    private String mTitleText;
    private String mDescText;

    public IntroModel(int mImageResId, String mTitleText, String mDescText) {
        this.mImageResId = mImageResId;
        this.mTitleText = mTitleText;
        this.mDescText = mDescText;
    }

    public int getImageResId() {
        return mImageResId;
    }

    public String getTitleText() {
        return mTitleText;
    }

    public String getDescText() {
        return mDescText;
    }
}
