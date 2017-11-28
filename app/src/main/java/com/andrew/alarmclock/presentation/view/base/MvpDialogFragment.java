package com.andrew.alarmclock.presentation.view.base;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;

import com.arellomobile.mvp.MvpDelegate;

public class MvpDialogFragment extends DialogFragment {

    private boolean mIsStateSaved;
    private MvpDelegate<? extends MvpDialogFragment> mMvpDelegate;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getMvpDelegate().onCreate(savedInstanceState);
    }

    public void onResume() {
        super.onResume();

        mIsStateSaved = false;

        getMvpDelegate().onAttach();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mIsStateSaved = true;

        getMvpDelegate().onSaveInstanceState(outState);
        getMvpDelegate().onDetach();
    }

    @Override
    public void onStop() {
        super.onStop();

        getMvpDelegate().onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        getMvpDelegate().onDetach();
        getMvpDelegate().onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (getActivity().isFinishing()) {
            getMvpDelegate().onDestroy();
            return;
        }

        if (mIsStateSaved) {
            mIsStateSaved = false;
            return;
        }

        boolean anyParentIsRemoving = false;

        Fragment parent = getParentFragment();
        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving();
            parent = parent.getParentFragment();
        }

        if (isRemoving() || anyParentIsRemoving) {
            getMvpDelegate().onDestroy();
        }
    }

    public MvpDelegate getMvpDelegate() {
        if (mMvpDelegate == null) {
            mMvpDelegate = new MvpDelegate<>(this);
        }

        return mMvpDelegate;
    }
}
