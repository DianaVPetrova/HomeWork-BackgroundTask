package diana.softuni.bg.mytrackingapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import diana.softuni.bg.mytrackingapp.R;


public class LineAdapter extends RecyclerView.Adapter<LineHolder> {

    private  List<String> myLocations;

    public LineAdapter(List<String> data) {
        myLocations = data;
    }

    @Override
    public LineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LineHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_line_view, parent, false));
    }

    @Override
    public void onBindViewHolder(LineHolder holder, int position) {
        holder.txtTitle.setText(String.format("Location: %d", position + 1));
        holder.txtLocation.setText(myLocations.get(position));
    }

    @Override
    public int getItemCount() {
        return myLocations.size() ;
    }

    public void changeDataItems(List<String> items) {
        this.myLocations = items;
        notifyDataSetChanged();
    }

}