package com.medikeen.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.medikeen.patient.R;
import com.medikeen.dataModel.HistoryModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HistoryAdapter extends ArrayAdapter<HistoryModel> {

    ArrayList<HistoryModel> list;
    LayoutInflater inflater;
    Context context;

    public HistoryAdapter(Context context, ArrayList<HistoryModel> list) {
        super(context, R.layout.history_list_item, list);

        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.history_list_item, parent, false);

            holder.historyImage = (ImageView) convertView.findViewById(R.id.history_thumbnail);
            holder.name = (TextView) convertView.findViewById(R.id.rec_name);
            holder.address = (TextView) convertView.findViewById(R.id.rec_address);
            holder.number = (TextView) convertView.findViewById(R.id.rec_number);
            holder.offer = (TextView) convertView.findViewById(R.id.rec_offer);
            holder.date = (TextView) convertView.findViewById(R.id.rec_date);
            holder.orderNumber = (TextView) convertView.findViewById(R.id.hist_order_number);
            holder.cost = (TextView) convertView.findViewById(R.id.rec_cost);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(context).load("http://www.medikeen.com/android_api/prescription/thumbnail.php?image="
                + list.get(position).getResourceId()).into(holder.historyImage);

        holder.name.setText(list.get(position).getRecepientName());
        holder.address.setText(list.get(position).getRecepientAddress());
        holder.number.setText(list.get(position).getRecepientNumber());
        holder.offer.setText(list.get(position).getOfferType());
        holder.orderNumber.setText("Order Number: " + list.get(position).getResourceId());

        if (Double.valueOf(list.get(position).getCost()) == 0.0) {
            holder.cost.setVisibility(View.GONE);
        } else {
            holder.cost.setVisibility(View.VISIBLE);
        }

        holder.cost.setText("Rs. " + Double.valueOf(list.get(position).getCost()));
//        holder.date.setText(list.get(position).getCreated_Date());

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse(list.get(position).getCreated_Date());

            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd MMM, yyyy HH:mm");

            holder.date.setText(simpleDateFormat1.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    class ViewHolder {
        ImageView historyImage;
        TextView name, address, number, offer, date, orderNumber, cost;
    }

}
