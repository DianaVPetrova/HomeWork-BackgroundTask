package diana.softuni.bg.andoriddesignpatternshomework.ui;


import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView)
    {
        super(itemView);
    }

    public abstract void setData(T data);
}