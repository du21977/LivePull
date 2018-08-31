package com.dibi.livepull.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dibi.livepull.R;

/**
 * Created by Admin on 2018/8/27.
 */

public class AddUrlView extends LinearLayout{

    public AddUrlView(Context context) {
        this(context,null);
    }

    public AddUrlView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AddUrlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = inflate(context, R.layout.add_url_view,this);

        EditText editText = view.findViewById(R.id.et_pull);
        TextView tv_del = view.findViewById(R.id.tv_del);

        tv_del.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(View.GONE);
               ViewParent view = getParent();

            }
        });

    }



}
