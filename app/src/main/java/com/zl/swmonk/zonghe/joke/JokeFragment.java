package com.zl.swmonk.zonghe.joke;


import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.zl.swmonk.zonghe.R;
import com.zl.swmonk.zonghe.base.model.NetWork;
import com.zl.swmonk.zonghe.base.ui.BaseFragment;
import com.zl.swmonk.zonghe.base.ui.RecyclerAdapter;
import com.zl.swmonk.zonghe.base.util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class JokeFragment extends BaseFragment {

    @BindView(R.id.SRL_refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rv_jock)
    RecyclerView recyclerView;

    RecyclerAdapter<BeanJoke> adapter;

    private int mPage=1;
    List<BeanJoke> mJokeListLocal=new ArrayList<>();
    //List<BeanJoke> mJokeListNet=new ArrayList<>();

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_joke;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        adapter=new RecyclerAdapter<BeanJoke>(
                new RecyclerAdapter.AdapterListener<BeanJoke>() {
                    @Override
                    public void onItemClick(RecyclerAdapter.ViewHolder holder, BeanJoke beanJoke) {
                        JockActivity.showActivity(getActivity(),beanJoke);
                    }

                    @Override
                    public void onItemLongClick(RecyclerAdapter.ViewHolder holder, BeanJoke beanJoke) {

                    }
                }) {
            @Override
            protected int getItemViewType(int position, BeanJoke beanJoke) {
                return R.layout.item_joke;
            }

            @Override
            protected ViewHolder<BeanJoke> onCreateViewHolder(View root, int viewType) {
                return new JokeFragment.ViewHolder(root);
            }

        };
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(() ->{
            List<BeanJoke> jokeList=new ArrayList<>();
            mPage+=1;
            Disposable disposable=Observable.merge(NetWork.remote().getBeanJokeText("10",String.valueOf(mPage),Utils.getDateString()),NetWork.remote().getBeanJokePic("10",String.valueOf(mPage),Utils.getDateString()),NetWork.remote().getBeanJokeGif("10",String.valueOf(mPage),Utils.getDateString()))
                .subscribeOn(Schedulers.io())
                .filter(beanJokeRspMode -> beanJokeRspMode.getShowapiResCode()==0)
                .map(beanJokeRspMode ->beanJokeRspMode.getShowapiResBody().getContentlist())
                .compose(bindToLifecycle())
                .observeOn(Schedulers.io())
                .doOnNext(beanJokes -> {
                    jokeList.addAll(beanJokes);
                    //mJokeListNet.addAll(beanJokes);
                    for (BeanJoke beanJoke:beanJokes) {
                        beanJoke.save();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(beanJokes -> {}, throwable -> {}, () -> {
                    Collections.shuffle(jokeList);
                    adapter.addOnStart(jokeList);
                    refreshLayout.setRefreshing(false);
                });

        });

    }



    @Override
    protected void initData() {
        super.initData();

        Disposable disposable=Observable.merge(NetWork.remote().getBeanJokeText("10","1",Utils.getDateString()),NetWork.remote().getBeanJokePic("10","1",Utils.getDateString()),NetWork.remote().getBeanJokeGif("10","1",Utils.getDateString()))
                .subscribeOn(Schedulers.io())
                .filter(beanJokeRspMode -> beanJokeRspMode.getShowapiResCode()==0)
                .map(beanJokeRspMode ->beanJokeRspMode.getShowapiResBody().getContentlist())
                .compose(bindToLifecycle())
                .observeOn(Schedulers.io())
                .doOnNext(beanJokes -> {
                    mJokeListLocal.addAll(beanJokes);
                    for (BeanJoke beanJoke:beanJokes) {
                        beanJoke.save();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(beanJokes -> {}, throwable -> {}, () -> {
                    Collections.shuffle(mJokeListLocal);
                    adapter.add(mJokeListLocal);
                });

    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<BeanJoke>{

        @BindView(R.id.tv_title)
        TextView tittle;
        @BindView(R.id.tv_text)
        TextView text;
        @BindView(R.id.iv_image)
        ImageView img;


        ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(BeanJoke beanJoke) {
            tittle.setText(beanJoke.getTitle());
            text.setText(beanJoke.getText());
            Glide.with(itemView).load(beanJoke.getImg()).into(img);

        }
    }

}
