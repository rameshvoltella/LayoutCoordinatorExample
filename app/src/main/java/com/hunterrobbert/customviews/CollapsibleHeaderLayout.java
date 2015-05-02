package com.hunterrobbert.customviews;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by hunter on 4/27/15.
 */

public class CollapsibleHeaderLayout extends RelativeLayout{

    private static final String TAG = CollapsibleHeaderLayout.class.getSimpleName();

    private static final String COLOR_ACCENT = "color_accent";
    private static final String COLOR_PRIMARY = "color_primary";
    private static final String COLOR_TITLE_TEXT = "color_title_text";
    private static final String COLOR_SUBTITLE_TEXT = "color_subtitle_text";



    //attrs
    protected boolean mAutoHideHeader;
    protected String mTitleText;
    protected String mSubTitleText;
    protected int mHeaderImageId;
    protected int mToolbarColor;
    protected int mTitleTextColor;
    protected int mSubTitleTextColor;

    //views
    private ImageView mHeaderImage;
    private RelativeLayout mHeaderBox;
    private View mTitleBackgroundView;
    private LinearLayout mTitleBox;
    private TextView mTitleTextView;
    private TextView mSubTitleTextView;
    private SlidingTabLayout mSlidingTabLayout;


    public CollapsibleHeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.CollapsibleHeaderLayout,0,0);

        try {
            mAutoHideHeader = a.getBoolean(R.styleable.CollapsibleHeaderLayout_autoHideHeader,false);
            mTitleText = a.getString(R.styleable.CollapsibleHeaderLayout_titleText);
            mSubTitleText = a.getString(R.styleable.CollapsibleHeaderLayout_subTitleText);
            mHeaderImageId = a.getResourceId(R.styleable.CollapsibleHeaderLayout_headerImage,R.drawable.header_image_placeholder);
            mToolbarColor = a.getColor(R.styleable.CollapsibleHeaderLayout_toolbarBackgroundColor, getThemeColor(COLOR_PRIMARY));
            mTitleTextColor = a.getColor(R.styleable.CollapsibleHeaderLayout_titleTextColor, getThemeColor(COLOR_TITLE_TEXT));
            mSubTitleTextColor = a.getColor(R.styleable.CollapsibleHeaderLayout_subTitleTextColor, getThemeColor(COLOR_SUBTITLE_TEXT));
        } finally {
            a.recycle();
        }

        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.collapsible_header_layout, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        //find & set views
        mHeaderImage = (ImageView) this.findViewById(R.id.header_image);
        mHeaderBox = (RelativeLayout) this.findViewById(R.id.header_box);
        mTitleBackgroundView = this.findViewById(R.id.title_background);
        mTitleBox = (LinearLayout) this.findViewById(R.id.title_box);
        mTitleTextView = (TextView) this.findViewById(R.id.title_text);
        mSubTitleTextView = (TextView) this.findViewById(R.id.sub_title_text);
        mSlidingTabLayout = (SlidingTabLayout) this.findViewById(R.id.sliding_tabs);

        mHeaderImage.setBackgroundResource(mHeaderImageId);
        mTitleBackgroundView.setBackgroundColor(mToolbarColor);

        mTitleTextView.setText(mTitleText);
        mTitleTextView.setTextColor(mTitleTextColor);

        mSubTitleTextView.setText(mSubTitleText);
        mSubTitleTextView.setTextColor(mSubTitleTextColor);

        mSlidingTabLayout.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);
        Resources res = getResources();
        mSlidingTabLayout.setSelectedIndicatorColors(res.getColor(R.color.colorAccent));
        mSlidingTabLayout.setDistributeEvenly(true);
    }

    public void setViewPager(ViewPager viewPager) {
        mSlidingTabLayout.setViewPager(viewPager);
    }

    public void setSlidingTabLayoutContentDescriptions(int[] titleStringArray) {
        for (int i = 0; i < titleStringArray.length; i++) {
            mSlidingTabLayout.setContentDescription(i, getResources().getString(titleStringArray[i]));
        }
    }

    public boolean isAutoHideHeader() {
        return mAutoHideHeader;
    }

    public void setAutoHideHeader(boolean autoHide) {
        mAutoHideHeader = autoHide;

        // don't think invalidating and redrawing will be necessary to change the autoHide functionality
        //invalidate();
        //requestLayout();
    }

    public String getTitleText() {
        return mTitleText;
    }

    public void setTitleText(String titleText) {
        mTitleText = titleText;
        invalidate();
        requestLayout();
    }

    public String getSubTitleText() {
        return mSubTitleText;
    }

    public void setSubTitleText(String subTitleText) {
        mSubTitleText = subTitleText;
        invalidate();
        requestLayout();
    }




    private int getThemeColor(String colorString) {
        TypedValue typedValue = new TypedValue();
        int colorId = R.attr.colorPrimary;
        switch (colorString) {
            case COLOR_ACCENT :
                colorId = R.attr.colorAccent;
                break;
            case COLOR_PRIMARY :
                colorId = R.attr.colorPrimary;
                break;
            case COLOR_TITLE_TEXT :
                colorId = R.attr.titleTextColor;
                break;
            case COLOR_SUBTITLE_TEXT :
                colorId = R.attr.colorAccent;
                break;
        }

        TypedArray a = getContext().obtainStyledAttributes(typedValue.data,new int[] { colorId });
        int color = a.getColor(0,0);
        a.recycle();

        return color;
    }
}