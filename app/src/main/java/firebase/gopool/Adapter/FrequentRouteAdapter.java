package firebase.gopool.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import firebase.gopool.FrequentRoute.MapFrequentRouteActivity;
import firebase.gopool.R;
import firebase.gopool.models.FrequentRouteResults;

public class FrequentRouteAdapter extends RecyclerView.Adapter<FrequentRouteAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<FrequentRouteResults> routes;

    public FrequentRouteAdapter(Context mContext, ArrayList<FrequentRouteResults> routes) {
        this.mContext = mContext;
        this.routes = routes;
    }

    @NonNull
    @Override
    public FrequentRouteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FrequentRouteAdapter.MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.individual_frequent_route, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FrequentRouteAdapter.MyViewHolder holder, int position) {
        FrequentRouteResults route = routes.get(position);

        String from = "From: " + route.getAddress_start().replaceAll("\n", ", ");
        String to = "To: " + route.getAddress_destination().replaceAll("\n", ", ");

        if (route.isShared()) {
            holder.cardView.setCardBackgroundColor(Color.rgb(234, 255, 236));
            holder.frequentRouteStatusTextview.setText("You have shared!");
            holder.frequentRouteStatusTextview.setTextColor(Color.rgb(0, 160, 66));
        }

        holder.view.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, MapFrequentRouteActivity.class);
            intent.putExtra("data", route);
            mContext.startActivity(intent);
        });

        if (to.length() > 20) {
            to = to.substring(0, Math.min(to.length(), 21));
            to = to + "...";
            holder.to.setText(to);
        } else {
            holder.to.setText(to);
        }

        if (from.length() > 20) {
            from = from.substring(0, Math.min(from.length(), 21));
            from = from + "...";
            holder.from.setText(from);
        } else {
            holder.from.setText(from);
        }

        holder.length.setText(route.getLength_route() + " km");
        holder.date.setText("Time: " + route.getTime_start() + " - " + route.getTime_destination());
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout view;
        TextView from, to, date, length, frequentRouteStatusTextview;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.bookingCardView);
            view = (LinearLayout) itemView.findViewById(R.id.view);
            from = (TextView) itemView.findViewById(R.id.fromTxt);
            to = (TextView) itemView.findViewById(R.id.toTxt);
            date = (TextView) itemView.findViewById(R.id.individualDateTxt);
            length = (TextView) itemView.findViewById(R.id.lengthTxt);
            frequentRouteStatusTextview = (TextView) itemView.findViewById(R.id.frequentRouteStatusTextview);

        }
    }
}
