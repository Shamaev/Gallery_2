package com.geekbrains.gallery_v_02.view;

import com.geekbrains.gallery_v_02.entity.Hit;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface HomeView extends MvpView {
    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void getHits(List<Hit> list);
}
