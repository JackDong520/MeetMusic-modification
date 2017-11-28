package com.lijunyan.blackmusic.adapter;

/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * This class is from the v7 samples of the Android SDK. It's not by me!
 * <p/>
 * See the license above for details.
 */

//1.09  删除没必要的Adapter
public class DividerItemDecoration extends RecyclerView.ItemDecoration
{

	private static final String TAG = DividerItemDecoration.class.getName();
	private static final int[] ATTRS = new int[] { android.R.attr.listDivider };

	public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

	public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

	//分割线距离左边多少宽度
	private final static int DIVIDER_PADING_LEFT = 80;
	
	

	private Drawable mDivider;

	private int mOrientation;

	public DividerItemDecoration(Context context, int orientation)
	{
		final TypedArray a = context.obtainStyledAttributes(ATTRS);
		mDivider = a.getDrawable(0);
		a.recycle();
		setOrientation(orientation);
	}

	public void setOrientation(int orientation)
	{
		if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST)
		{
			throw new IllegalArgumentException("invalid orientation");
		}
		mOrientation = orientation;
	}


	@Override
	public void onDraw(Canvas c, RecyclerView parent,RecyclerView.State state)
	{
		Log.d(TAG, "onDraw()");
		 if (mOrientation == VERTICAL_LIST) {
	            drawVertical(c, parent);
	        } else {
	            drawHorizontal(c, parent);
	        }
	}

	public void drawVertical(Canvas c, RecyclerView parent)
	{
		final int left = parent.getPaddingLeft() + DIVIDER_PADING_LEFT;
		final int right = parent.getWidth() - parent.getPaddingRight();

		final int childCount = parent.getChildCount();

		for (int i = 0; i < childCount; i++)
		{
			final View child = parent.getChildAt(i);
			RecyclerView v = new RecyclerView(
					parent.getContext());
			final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
					.getLayoutParams();
			final int top = child.getBottom() + params.bottomMargin;
			final int bottom = top + mDivider.getIntrinsicHeight();
			mDivider.setBounds(left, top, right, bottom);
			mDivider.draw(c);
		}
	}

	public void drawHorizontal(Canvas c, RecyclerView parent)
	{
		final int top = parent.getPaddingTop();
		final int bottom = parent.getHeight() - parent.getPaddingBottom();

		final int childCount = parent.getChildCount();
		for (int i = 0; i < childCount; i++)
		{
			final View child = parent.getChildAt(i);
			final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
					.getLayoutParams();
			final int left = child.getRight() + params.rightMargin;
			final int right = left + mDivider.getIntrinsicHeight();
			mDivider.setBounds(left, top, right, bottom);
			mDivider.draw(c);
		}
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		super.getItemOffsets(outRect, view, parent, state);
		if (mOrientation == VERTICAL_LIST)
		{
			outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
		} else
		{
			outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
		}
	}

}
