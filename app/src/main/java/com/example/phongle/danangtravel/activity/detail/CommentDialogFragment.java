package com.example.phongle.danangtravel.activity.detail;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.phongle.danangtravel.R;
import com.example.phongle.danangtravel.activity.utils.ScreenUtil;

public class CommentDialogFragment extends DialogFragment {
    private RatingBar mRatingEvaluate;
    private TextView mEdtContent;
    private Button mBtnComment;
    private Button mBtnCancel;
    private int mNumstar;
    private DialogCommentListener mDialogCommentListener;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dialog_comment, container, false);
        initViews(view);
        mRatingEvaluate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mNumstar = (int) rating;
            }
        });
        mBtnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialogCommentListener != null) {
                    String content = mEdtContent.getText().toString();
                    mDialogCommentListener.onComment(mNumstar, content);
                }
                dismiss();
            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        if (window != null) {
            window.requestFeature(Window.FEATURE_NO_TITLE);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setLayout((int) (ScreenUtil.getWidthScreen(getActivity()) * 0.9), (int) (ScreenUtil.getHeightScreen(getActivity()) * 0.8));
        }
        dialog.setContentView(R.layout.dialog_comment);
        return dialog;
    }

    private void initViews(View view) {
        mRatingEvaluate = view.findViewById(R.id.evaluate);
        mEdtContent = view.findViewById(R.id.edtContent);
        mBtnComment = view.findViewById(R.id.btnDialogComment);
        mBtnCancel = view.findViewById(R.id.btnDialogCancel);
    }

    public void setDialogCommentListener(DialogCommentListener dialogCommentListener) {
        mDialogCommentListener = dialogCommentListener;
    }

    public interface DialogCommentListener {
        void onComment(int evaluate, String content);
    }
}

