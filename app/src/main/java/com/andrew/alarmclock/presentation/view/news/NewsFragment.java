package com.andrew.alarmclock.presentation.view.news;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrew.alarmclock.BuildConfig;
import com.andrew.alarmclock.R;
import com.andrew.alarmclock.data.entities.api.news.NewsResponse;
import com.andrew.alarmclock.data.entities.api.weather.WeatherResponse;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class NewsFragment extends MvpAppCompatFragment implements NewsView, SwipeRefreshLayout.OnRefreshListener {

    private final static String TAG_Y_AXIS = "Y";
    private final static String TAG_ASK_PERMISSIONS = "ASK";

    @InjectPresenter
    NewsPresenter presenter;

    @BindView(R.id.news_coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.news_swipe_refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.news_recycler_view)
    RecyclerView newsRecyclerView;

    @BindView(R.id.news_error_news_text_view)
    TextView errorNewsTextView;

    private NewsAdapter newsAdapter;
    private RecyclerView.LayoutManager newsLayoutManager;

    private Snackbar snackbarNoInternet;
    private Snackbar snackbarEmpty;

    private boolean shouldAskPermissions;

    public static NewsFragment getInstance() {
        return new NewsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
        refreshLayout.setOnRefreshListener(this);

        setupRecycler();
        setupSnackBars();

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        NewsFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(TAG_Y_AXIS, newsLayoutManager.onSaveInstanceState());
        outState.putBoolean(TAG_ASK_PERMISSIONS, shouldAskPermissions);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            newsLayoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(TAG_Y_AXIS));
            shouldAskPermissions = savedInstanceState.getBoolean(TAG_ASK_PERMISSIONS);
        } else {
            shouldAskPermissions = true;
        }
    }

    @Override
    public void setRefreshCircleColor(boolean isDarkTheme) {
        if(isDarkTheme) {
            setupCircleRefresh(R.color.accent_dark, R.color.grey_light);
        } else {
            setupCircleRefresh(R.color.accent, R.color.white);
        }
    }

    @Override
    public void onRefresh() {
        presenter.onGetNewsAndWeather();
    }

    @Override
    public void showWeather(WeatherResponse response) {
        newsAdapter.removeError();
        newsAdapter.setWeather(response.getForecast());
        newsRecyclerView.setVisibility(View.VISIBLE);
        refreshLayout.setRefreshing(false);
        if (snackbarNoInternet != null) snackbarNoInternet.dismiss();
    }

    @Override
    public void showNews(NewsResponse response) {
        errorNewsTextView.setVisibility(View.GONE);
        newsRecyclerView.setVisibility(View.VISIBLE);
        newsAdapter.setNewsItems(response.getNewsItems());
        refreshLayout.setRefreshing(false);
        if (response.getNewsItems().size() == 0) {
            snackbarEmpty.show();
        } else {
            snackbarEmpty.dismiss();
        }
        if (snackbarNoInternet != null) snackbarNoInternet.dismiss();
    }

    @Override
    public void showPermissionsError() {
        newsAdapter.setPermissionsError(getString(R.string.error_no_permissions));
        refreshLayout.setRefreshing(false);
        if (newsAdapter.getItemCount() == 1) {
            snackbarEmpty.show();
        } else {
            snackbarEmpty.dismiss();
        }
    }

    @Override
    public void showLocationError() {
        newsAdapter.setError(getString(R.string.error_no_location));
        refreshLayout.setRefreshing(false);
        if (newsAdapter.getItemCount() == 1) {
            snackbarEmpty.show();
        } else {
            snackbarEmpty.dismiss();
        }
    }

    @Override
    public void showNetworkError() {
        errorNewsTextView.setText(getString(R.string.error_no_internet_connection));
        errorNewsTextView.setVisibility(View.VISIBLE);
        newsRecyclerView.setVisibility(View.INVISIBLE);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showNetworkErrorWeather() {
        newsAdapter.setError(getString(R.string.error_no_internet_connection));
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onShowProgressBar() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void showCachedNews(NewsResponse response) {
        errorNewsTextView.setVisibility(View.GONE);
        newsRecyclerView.setVisibility(View.VISIBLE);
        newsAdapter.setError(getString(R.string.error_cached_news));
        newsAdapter.setNewsItems(response.getNewsItems());
        refreshLayout.setRefreshing(false);

        snackbarEmpty.dismiss();
        snackbarNoInternet.setAction(getString(R.string.error_refresh), view -> {
            refreshLayout.setRefreshing(true);
            presenter.onGetNewsAndWeather();
        }).show();
    }

    private void setupRecycler() {
        newsAdapter = new NewsAdapter();
        newsLayoutManager = new LinearLayoutManager(getContext());

        newsAdapter.setPermissionsListener(() -> {
            NewsFragmentPermissionsDispatcher.showWeatherWithPermissionsWithCheck(this);
        });

        newsRecyclerView.setAdapter(newsAdapter);
        newsRecyclerView.setLayoutManager(newsLayoutManager);
    }

    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    void showWeatherWithPermissions() {
        presenter.onGetWeather();
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_COARSE_LOCATION)
    void onNeverAskAgainClick() {
        if (shouldAskPermissions) {
            AlertDialog dialog = new AlertDialog.Builder(getActivity(), R.style.SettingsAlertDialogStyle_Light)
                    .setMessage(getString(R.string.location_info))
                    .setNegativeButton(getString(R.string.dialog_ok),
                            (dialogInterface, i) -> dialogInterface.dismiss())
                    .create();
            dialog.show();
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                    .setTextColor(ContextCompat.getColor(getContext(), R.color.accent));
            dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                    .setTextColor(ContextCompat.getColor(getContext(), R.color.accent));
        } else {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + BuildConfig.APPLICATION_ID));
            startActivity(intent);
        }
        shouldAskPermissions = false;
    }

    private void setupCircleRefresh(int colorAccentId, int colorCircleBackGroundId) {
        try {
            Field f = refreshLayout.getClass().getDeclaredField("mCircleView");
            f.setAccessible(true);
            ImageView img = (ImageView)f.get(refreshLayout);
            img.setBackgroundColor(ContextCompat.getColor(getContext(), colorCircleBackGroundId));
        } catch (NoSuchFieldException | IllegalAccessException ignored) {}
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), colorAccentId));
    }

    private void setupSnackBars() {
        snackbarNoInternet = Snackbar.make(coordinatorLayout,
                getString(R.string.error_no_internet_connection),
                Snackbar.LENGTH_INDEFINITE);

        ((TextView) snackbarNoInternet.getView()
                .findViewById(android.support.design.R.id.snackbar_text))
                .setTextColor(Color.WHITE);

        snackbarEmpty = Snackbar.make(coordinatorLayout,
                getString(R.string.error_empty_list),
                Snackbar.LENGTH_INDEFINITE);

        ((TextView) snackbarEmpty.getView()
                .findViewById(android.support.design.R.id.snackbar_text))
                .setTextColor(Color.WHITE);
    }
}
