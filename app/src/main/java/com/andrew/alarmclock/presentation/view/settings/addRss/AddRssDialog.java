package com.andrew.alarmclock.presentation.view.settings.addRss;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andrew.alarmclock.R;
import com.andrew.alarmclock.presentation.view.base.LoadingDialog;
import com.andrew.alarmclock.presentation.view.base.MvpDialogFragment;
import com.andrew.alarmclock.presentation.presenters.settings.addRss.AddRssPresenter;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddRssDialog extends MvpDialogFragment implements AddRssView {

    @InjectPresenter
    AddRssPresenter presenter;

    @BindView(R.id.dialog_settings_news_add_edit_text)
    EditText addFeedEditText;

    private AddingRssListener listener;

    private LoadingDialog loadingDialog;

    public static AddRssDialog getInstance() {
        return new AddRssDialog();
    }

    public void setAddingRssListener(AddingRssListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = LoadingDialog.getInstance();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (isInLayout())
            return super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_rss_add, null);

        builder.setView(view)
                .setNegativeButton(getString(R.string.dialog_save), (dialogInterface, i) -> {})
                .setPositiveButton(getString(R.string.dialog_cancel),
                        (dialogInterface, i) -> {
                            closeKeyBoard();
                            dialogInterface.dismiss();
                        });

        Dialog dialog = builder.create();

        dialog.setOnShowListener(dialogInterface -> {
            Button buttonNegative = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
            buttonNegative.setOnClickListener(view1 ->
                    presenter.onSaveNewFeed(addFeedEditText.getText().toString()));
        });

        setCancelable(false);
        ButterKnife.bind(this, view);

        return dialog;
    }

    @Override
    public void showLoading() {
        loadingDialog.show(getActivity().getFragmentManager(), "loading");
    }

    @Override
    public void closeDialog() {
        loadingDialog.dismiss();
        closeKeyBoard();
        dismiss();
        listener.onAddSuccessful();
    }

    @Override
    public void showWtfException(String error) {
        loadingDialog.dismiss();
        Toast.makeText(getActivity().getApplicationContext(),
                error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showExistsUrlError() {
        loadingDialog.dismiss();
        Toast.makeText(getActivity().getApplicationContext(),
                getString(R.string.error_url_exists), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTimeoutError() {
        loadingDialog.dismiss();
        Toast.makeText(getActivity().getApplicationContext(),
                getString(R.string.error_timeout), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showBadUrlError() {
        loadingDialog.dismiss();
        Toast.makeText(getActivity().getApplicationContext(),
                getString(R.string.error_bad_url), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showParseError() {
        loadingDialog.dismiss();
        Toast.makeText(getActivity().getApplicationContext(),
                getString(R.string.error_parse), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showOfflineParseError() {
        Toast.makeText(getActivity().getApplicationContext(),
                getString(R.string.error_bad_url), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoInternetError() {
        loadingDialog.dismiss();
        Toast.makeText(getActivity().getApplicationContext(),
                getString(R.string.error_no_internet_connection), Toast.LENGTH_SHORT).show();
    }

    public interface AddingRssListener {
        void onAddSuccessful();
    }

    private void closeKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
                addFeedEditText.getWindowToken(), 0);
    }
}
