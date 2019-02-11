package interview.com.hiya;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CallListAdapter extends RecyclerView.Adapter {

    private List<Call> mCallList = new ArrayList<>();

    public void setData(List<Call> callList) {
        mCallList = callList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CallViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.call_info, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CallViewHolder viewHolder = (CallViewHolder)holder;
        Call call = mCallList.get(position);
        viewHolder.phoneNumber.setText(call.getNumber());
        viewHolder.date.setText(call.getDate());
        viewHolder.type.setText(call.getType());
    }

    @Override
    public int getItemCount() {
        return mCallList.size();
    }


    class CallViewHolder extends RecyclerView.ViewHolder {
        TextView phoneNumber;
        TextView date;
        TextView type;

        public CallViewHolder(View itemView) {
            super(itemView);
            phoneNumber = itemView.findViewById(R.id.phone_number);
            date = itemView.findViewById(R.id.date);
            type = itemView.findViewById(R.id.type);
        }
    }

}
