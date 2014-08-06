package com.github.taskmaster.qq50.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import com.github.taskmaster.qq50.R;

/**
 * A simple demo to simulate QQ v5.0's home page UI
 */
public class HomeFragment extends Fragment implements ViewTreeObserver.OnScrollChangedListener {
    private FrameLayout mDrawer;
    private FrameLayout mContainer;
    private HorizontalScrollView mScrollView;
    private int mMinDrawerWidth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        if (view == null) return null;
        mDrawer = (FrameLayout) view.findViewById(R.id.drawer);
        FrameLayout.LayoutParams drawerLp = (FrameLayout.LayoutParams) mDrawer.getLayoutParams();
        drawerLp.width = getResources().getDisplayMetrics().widthPixels;
        mDrawer.setLayoutParams(drawerLp);
        mContainer = (FrameLayout) view.findViewById(R.id.container);
        FrameLayout.LayoutParams containerLp = (FrameLayout.LayoutParams) mContainer.getLayoutParams();
        containerLp.width = getResources().getDisplayMetrics().widthPixels;
        mContainer.setLayoutParams(containerLp);
        mScrollView = (HorizontalScrollView) mDrawer.getRootView();
        mScrollView.getViewTreeObserver().addOnScrollChangedListener(this);
        mMinDrawerWidth = -getResources().getDimensionPixelSize(R.dimen.drawer_width);
        moveToFullContainer();
        return view;
    }

    private void moveToFullContainer() {
        mScrollView.post(new Runnable() {
            @Override
            public void run() {
                mScrollView.scrollTo(getResources().getDimensionPixelSize(R.dimen.drawer_width), 0);
            }
        });
    }

    @Override
    public void onScrollChanged() {
        int scrollX = mScrollView.getScrollX();
        float ratio = clamp((float)Math.max(-scrollX, mMinDrawerWidth) / mMinDrawerWidth,
                0.0f, 1.0f);
        /*
        *       Min   Max
        * Ratio 1.0f 0.0f
        *
        * Alpha 0.0f 1.0f
        * Scale 0.75f 1.0f
        * ScaleC 1.0f 0.85f
        * Margin 50px 0px
        */
        float alpha = 1.0f - (1.0f - 0.0f) * ratio;
        float scaleC = 0.85f - (0.85f - 1.0f) * ratio;
        float scale = 1.0f - (1.0f - 0.75f) * ratio;
        float margin = 0 - (0 - getResources().getDimensionPixelSize(R.dimen.drawer_width) / 1.6f) * ratio;
        mDrawer.setAlpha(alpha);
        mDrawer.setScaleX(scale);
        mDrawer.setScaleY(scale);
        mDrawer.setTranslationX(margin);

        mContainer.setScaleX(scaleC);
        mContainer.setScaleY(scaleC);
    }

    private float clamp(float value, float max, float min) {
        return Math.max(Math.min(value, min), max);
    }
}
