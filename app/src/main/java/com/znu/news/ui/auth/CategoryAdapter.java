package com.znu.news.ui.auth;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.google.android.material.color.MaterialColors;
import com.znu.news.R;
import com.znu.news.databinding.CategoryItemBinding;
import com.znu.news.ui.base.BaseAdapter;
import com.znu.news.ui.base.BaseViewHolder;

public class CategoryAdapter extends BaseAdapter<String> {


    @Override
    protected CategoryViewModel initViewHolder(ViewGroup parent) {
        return new CategoryViewModel(
                CategoryItemBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                )
        );
    }

    public static class CategoryViewModel extends BaseViewHolder<String, CategoryItemBinding> {

        public CategoryViewModel(CategoryItemBinding itemBinding) {
            super(itemBinding);
        }

        @Override
        public void bind(String s) {
            itemBinding.categoryButton.setText(s);
            itemBinding.categoryButton.setOnClickListener(v -> {
                boolean isSelected = !itemBinding.categoryButton.isSelected();
                if (isSelected) {
                    ViewCompat.setBackgroundTintList(itemBinding.categoryButton
                            , ColorStateList.valueOf(
                                    ContextCompat.getColor(itemView.getContext()
                                            , R.color.indigo_a_200_25)
                            )
                    );

                    itemBinding.categoryButton.setStrokeColor(
                            ColorStateList.valueOf(
                                    ContextCompat.getColor(itemView.getContext()
                                            , R.color.indigo_a_200)
                            )
                    );
                } else {
                    ViewCompat.setBackgroundTintList(itemBinding.categoryButton
                            , ColorStateList.valueOf(
                                    MaterialColors.getColor(
                                            itemView.getContext()
                                            , com.google.android.material.R.attr.colorSurface
                                            , Color.BLACK)
                            )
                    );

                    itemBinding.categoryButton.setStrokeColor(
                            ColorStateList.valueOf(Color.parseColor("#10000000"))
                    );
                }
                itemBinding.categoryButton.setSelected(isSelected);
            });
        }
    }
}
