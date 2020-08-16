package com.geekbrains.gallery_v_02.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geekbrains.gallery_v_02.AdapterPhotos;
import com.geekbrains.gallery_v_02.PhotoClick;
import com.geekbrains.gallery_v_02.R;
import com.geekbrains.gallery_v_02.entity.Hit;
import com.geekbrains.gallery_v_02.presenter.HomePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;


public class HomeFragment extends MvpAppCompatFragment implements HomeView, PhotoClick {
    private Unbinder unbinder;
    NavController navController;

    @BindView(R.id.recyclerPhotos)
    RecyclerView recyclerView;

    @InjectPresenter
    HomePresenter homePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(getActivity(), R.id.fragment);
    }
    @Override
    public void getHits(List<Hit> list) {
        initRecycler(list);
    }


    private void initRecycler(List<Hit> list) {
        GridLayoutManager gm = new GridLayoutManager(getContext(), 3);
        AdapterPhotos adapter = new AdapterPhotos(list);

        adapter.registerCallBack(this);

        recyclerView.setLayoutManager(gm);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void photoClick(String url, String user) {
        homePresenter.refrashInfo(url, user);
        NavHostFragment.findNavController(HomeFragment.this)
                .navigate(R.id.action_homeFragment_to_detailFragment);
    }
}
