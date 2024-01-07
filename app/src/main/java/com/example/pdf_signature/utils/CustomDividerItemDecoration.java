package com.example.pdf_signature.utils;

import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.pdf_signature.R;

public class CustomDividerItemDecoration extends DividerItemDecoration {

    public CustomDividerItemDecoration(Context context, int orientation) {
        super(context, orientation);
        setDrawable(ContextCompat.getDrawable(context, R.drawable.custom_divider));
    }
}