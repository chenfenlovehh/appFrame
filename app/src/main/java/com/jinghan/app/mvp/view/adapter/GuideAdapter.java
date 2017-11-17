package com.jinghan.app.mvp.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jinghan.core.R;
import com.jinghan.core.databinding.FgGuideViewBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuzeren
 * @time 2017/11/9    下午6:41
 * @mail lzr319@163.com
 */
public class GuideAdapter extends PagerAdapter {
    private final List<View> pageViews = new ArrayList<>();

    private View.OnClickListener viewClick;


    public void setViewPageItemClick(View.OnClickListener viewClick) {
        this.viewClick = viewClick;
    }

    public GuideAdapter(Context context,Integer[] resDraws){
        pageViews.clear();

        for(int index = 0;index < resDraws.length;index++){

            int resDraw = resDraws[index];

            FgGuideViewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.fg_guide_view,null,false);
            binding.setBg(resDraw);
            binding.setVisible(index == resDraws.length-1);

            pageViews.add(binding.getRoot());
        }
    }

    @Override
    public int getCount() {
        return pageViews == null ? 0 : pageViews.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(final ViewGroup viewGroup, final int position) {

        View view = pageViews.get(position);
        viewGroup.addView(view);

        View viewEnter = view.findViewById(R.id.ivEnter);
        if(viewEnter.getVisibility() == View.VISIBLE) {
            viewEnter.setOnClickListener(v -> {
                if (null != viewClick) {
                    viewClick.onClick(viewGroup);
                }
            });
        }
        return pageViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup v, int position, Object arg2) {
        v.removeView(pageViews.get(position));
    }
}