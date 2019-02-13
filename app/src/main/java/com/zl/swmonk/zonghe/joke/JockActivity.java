package com.zl.swmonk.zonghe.joke;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zl.swmonk.zonghe.R;
import com.zl.swmonk.zonghe.base.ui.BaseActivity;

import butterknife.BindView;

public class JockActivity extends BaseActivity {

    public static final String JOCK_TYPE="JOCK_TYPE";
    public static final String JOCK_BEAN="JOCK_BEAN";

    private int type;
    private BeanJoke beanJoke;

    @BindView(R.id.title)
    TextView tvTitle;
    @BindView(R.id.text)
    TextView tvText;
    @BindView(R.id.image)
    ImageView ivImage;

    public static void showActivity(Context context,BeanJoke beanJoke){
        Intent intent=new Intent(context,JockActivity.class);
        intent.putExtra(JOCK_TYPE,beanJoke.getType());
        intent.putExtra(JOCK_BEAN, beanJoke);
        context.startActivity(intent);
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        type=bundle.getInt(JOCK_TYPE);
        beanJoke=(BeanJoke) bundle.get(JOCK_BEAN);
        return true;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_jock;
    }

    @Override
    protected void initData() {
        tvTitle.setText(beanJoke.getTitle());
        if (type!=1){
            tvText.setVisibility(View.GONE);
            Glide.with(this)
                    .load(beanJoke.getImg())
                    .into(ivImage);
        }else{
            tvText.setText(beanJoke.getText());
        }


    }
}
