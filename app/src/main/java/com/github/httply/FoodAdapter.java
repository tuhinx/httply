package com.github.httply;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class FoodAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<HashMap<String, String>> foodList;
    private LayoutInflater inflater;

    public FoodAdapter(Context context, ArrayList<HashMap<String, String>> foodList) {
        this.context = context;
        this.foodList = foodList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.food_item_card, parent, false);
            holder = new ViewHolder();
            holder.tvFoodName = convertView.findViewById(R.id.tvFoodName);
            holder.tvFoodCategory = convertView.findViewById(R.id.tvFoodCategory);
            holder.tvFoodPrice = convertView.findViewById(R.id.tvFoodPrice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap<String, String> foodItem = foodList.get(position);

        holder.tvFoodName.setText(foodItem.get("name"));
        holder.tvFoodCategory.setText(foodItem.get("category"));
        holder.tvFoodPrice.setText(foodItem.get("price"));

        return convertView;
    }

    static class ViewHolder {
        TextView tvFoodName;
        TextView tvFoodCategory;
        TextView tvFoodPrice;
    }
}
