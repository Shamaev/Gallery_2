package com.geekbrains.gallery_v_02.view;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface DetailView extends MvpView {
    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void getData(String url, String user);
}
