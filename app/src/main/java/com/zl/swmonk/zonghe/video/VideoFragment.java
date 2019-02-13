package com.zl.swmonk.zonghe.video;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zl.swmonk.zonghe.R;
import com.zl.swmonk.zonghe.base.ui.BaseFragment;
import com.zl.swmonk.zonghe.base.ui.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class VideoFragment extends BaseFragment {


    @BindView(R.id.id_videoList)
    RecyclerView recyclerView;

    RecyclerAdapter<BeanVideo> recyclerAdapter;

    List<BeanVideo> beanVideoList=new ArrayList<>();

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        recyclerAdapter=new RecyclerAdapter<BeanVideo>() {

            @Override
            protected int getItemViewType(int position, BeanVideo beanVideo) {
                return R.layout.item_video;
            }

            @Override
            protected ViewHolder onCreateViewHolder(View root, int viewType) {
                return new VideoFragment.ViewHolder(root);
            }

        };
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        beanVideoList.add(new BeanVideo("岳云鹏相声","https://vd3.bdstatic.com/mda-ikff9ry71p4apt9y/hd/mda-ikff9ry71p4apt9y.mp4"));
        beanVideoList.add(new BeanVideo("春晚爆料","https://vd4.bdstatic.com/mda-imdg336m7mu0e759/hd/mda-imdg336m7mu0e759.mp4"));
        beanVideoList.add(new BeanVideo("2019年春晚相声","https://vd4.bdstatic.com/mda-imcfcq3n6ieqsrw0/hd/mda-imcfcq3n6ieqsrw0.mp4"));

        beanVideoList.add(new BeanVideo("岳云鹏相声","https://vd3.bdstatic.com/mda-ikff9ry71p4apt9y/hd/mda-ikff9ry71p4apt9y.mp4"));
        beanVideoList.add(new BeanVideo("春晚爆料","https://vd4.bdstatic.com/mda-imdg336m7mu0e759/hd/mda-imdg336m7mu0e759.mp4"));
        beanVideoList.add(new BeanVideo("2019年春晚相声","https://vd4.bdstatic.com/mda-imcfcq3n6ieqsrw0/hd/mda-imcfcq3n6ieqsrw0.mp4"));

        recyclerAdapter.add(beanVideoList);
    }


    class ViewHolder extends RecyclerAdapter.ViewHolder<BeanVideo>{

        @BindView(R.id.id_title)
        TextView title;
        @BindView(R.id.id_jzvd)
        JzvdStd jzvdStd;

        ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(BeanVideo beanVideo) {
            title.setText(beanVideo.getTitle());
            jzvdStd.setUp(beanVideo.getUrl(),beanVideo.getTitle(),Jzvd.SCREEN_WINDOW_NORMAL);
            Glide.with(itemView).load("http://img05.tooopen.com/images/20141029/sy_73637397388.jpg").into(jzvdStd.thumbImageView);
        }
    }
}
