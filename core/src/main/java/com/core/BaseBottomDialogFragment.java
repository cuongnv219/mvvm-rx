package com.core;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.lang.reflect.Field;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseBottomDialogFragment<V extends ViewDataBinding> extends BottomSheetDialogFragment {

    protected V binding;

    public enum BottomDialogDisplayType {
        FULL_SCREEN,
        FULL_SCREEN_MARGIN_TOP,
        WRAP_CONTENT
    }

    public float heightPercent() {
        return 1f;
    }

    public abstract int getIdLayout();

    protected boolean getDragMode() {
        return true;
    }

    public boolean isCancelOutside() {
        return true;
    }

    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        if (getDragMode()) {
            super.setupDialog(dialog, style);
            return;
        }
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialog;
        bottomSheetDialog.setContentView(getIdLayout());

        try {
            Field behaviorField = bottomSheetDialog.getClass().getDeclaredField("behavior");
            behaviorField.setAccessible(true);
            final BottomSheetBehavior behavior = (BottomSheetBehavior) behaviorField.get(bottomSheetDialog);
            behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        } catch (NoSuchFieldException | IllegalAccessException e) {
        }
    }

    public void doViewCreated(View view) {
    }

    protected BottomDialogDisplayType getDisplayType() {
        return BottomDialogDisplayType.FULL_SCREEN_MARGIN_TOP;
    }

    @Override
    public int getTheme() {
        return R.style.BaseBottomSheetDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
//        dialog.getWindow().getAttributes().windowAnimations = R.style.ios_sheet_anim;
        dialog.setCanceledOnTouchOutside(isCancelOutside());
        dialog.setOnShowListener(dialogInterface -> {
            BottomSheetDialog dialog1 = (BottomSheetDialog) dialogInterface;
            FrameLayout bottomSheet = dialog1.findViewById(R.id.design_bottom_sheet);
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
            bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
        });
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        viewModel = (T) (ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(BaseClassFactory.getClassViewModel(getClass())));
//        viewModel.setMainView(this);
        binding = DataBindingUtil.inflate(inflater, getIdLayout(), container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.executePendingBindings();
        final View view = binding.getRoot();
//        view.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return false;
//            }
//        });
        setupFullHeight(view);
        return view;
    }

    private void setupFullHeight(View bottomSheet) {
        final int windowHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        ViewGroup.LayoutParams layoutParams;
        switch (getDisplayType()) {
            case FULL_SCREEN:
                layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, windowHeight);
                break;
            case WRAP_CONTENT:
                layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                break;
            default:
                // Calculate ActionBar height
                int actionBarHeight = 0;
                TypedValue tv = new TypedValue();
                if (getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                    actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
                }
                int height = (int) ((windowHeight - actionBarHeight * 2 / 3) * heightPercent());
                layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                break;
        }
        bottomSheet.setLayoutParams(layoutParams);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (isAdded()) {
                doViewCreated(view);
            }
        }, 200);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (binding.getRoot().getParent() != null) {
            ((ViewGroup) binding.getRoot().getParent()).removeView(binding.getRoot());
        }
    }
}
