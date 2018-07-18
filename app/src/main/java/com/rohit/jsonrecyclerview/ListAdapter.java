package com.rohit.jsonrecyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Model> {


    public ListAdapter(Context context, List<Model> list) {
        super(context, 0,list);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);
        }



        Model model = getItem(position);

        TextView mTextViewId = (TextView) listItemView.findViewById(R.id.id);
        mTextViewId.setText(model.getId());

        TextView mTextViewName = (TextView) listItemView.findViewById(R.id.name);
        mTextViewName.setText(model.getName());

        TextView mTextViewEmail = (TextView) listItemView.findViewById(R.id.email);
        mTextViewEmail.setText(model.getEmail());


        TextView mTextVewGender = (TextView) listItemView.findViewById(R.id.gender);
        mTextVewGender.setText(model.getGender());

        return listItemView;
    }

}
