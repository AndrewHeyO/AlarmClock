package com.andrew.alarmclock.settings.presentation.rssList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.andrew.alarmclock.R;
import com.andrew.alarmclock.base.MvpDialogFragment;
import com.andrew.alarmclock.data.entities.Feed;
import com.andrew.alarmclock.settings.presentation.addRss.AddRssDialog;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RssListDialog extends MvpDialogFragment implements RssListView, AddRssDialog.AddingRssListener {

    @InjectPresenter
    RssListPresenter presenter;

    @BindView(R.id.dialog_settings_rss_recycler_view)
    RecyclerView recyclerView;

    private RssListAdapter adapter;

    public static RssListDialog getInstance() {
        return new RssListDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (isInLayout())
            return super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_rss_list, null);

        builder.setView(view)
                .setNeutralButton(getString(R.string.dialog_add), (dialogInterface, i) -> {})
                .setPositiveButton(getString(R.string.dialog_cancel), (dialogInterface, i) -> dialogInterface.dismiss());

        Dialog dialog = builder.create();

        dialog.setOnShowListener(dialogInterface -> {
            Button buttonNeutral =
                    ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEUTRAL);
            buttonNeutral.setOnClickListener(this::onAddClick);
        });

        ButterKnife.bind(this, view);

        adapter = new RssListAdapter(feed -> {
            adapter.removeItem(feed);
            presenter.onDeleteFeed(feed);
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        return dialog;
    }


    @Override
    public void setFeedsData(List<Feed> feedList) {
        adapter.setData(feedList);
    }

    private void onAddClick(View view) {
        AddRssDialog addRssDialog = AddRssDialog.getInstance();
        addRssDialog.setAddingRssListener(this);
        addRssDialog.show(getActivity().getFragmentManager(), "addRss");
    }

    @Override
    public void onAddSuccessful() {
        presenter.onGetAllFeeds();
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(Feed feed);
    }
}
