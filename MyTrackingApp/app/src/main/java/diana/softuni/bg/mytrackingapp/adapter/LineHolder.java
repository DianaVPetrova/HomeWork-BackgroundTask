package diana.softuni.bg.mytrackingapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import diana.softuni.bg.mytrackingapp.R;

public class LineHolder extends RecyclerView.ViewHolder {

    public TextView txtTitle;
    public TextView txtLocation;

    public LineHolder(View itemView) {
        super(itemView);
        txtTitle = itemView.findViewById(R.id.txtTitle);
        txtLocation = itemView.findViewById(R.id.txtLocation);
    }
}