package ds.ac.kr.dsbusapplication;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ARRIVAL = 0;
    private static final int VIEW_TYPE_POSITION = 1;

    private ArrayList<ArrivalInfo> arrivalInfoArrayList;
    private ArrayList<PositionInfo> positionInfoArrayList;

    private int arrivalInfoArrayListSize;
    private int positionInfoArrayListSize;

    public MyAdapter(ArrayList<ArrivalInfo> arrivalInfoArrayList, ArrayList<PositionInfo> positionInfoArrayList) {
        this.arrivalInfoArrayList = arrivalInfoArrayList;
        this.positionInfoArrayList = positionInfoArrayList;

        arrivalInfoArrayListSize = arrivalInfoArrayList.size();
        positionInfoArrayListSize = positionInfoArrayList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("onCreate", "ViewHoler");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test2, parent, false);

        if (viewType == VIEW_TYPE_ARRIVAL) {
            return new ArrivalViewHolder(view);
        }

        if (viewType == VIEW_TYPE_POSITION) {
            return new PositionViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d("onBindViewHolder", "" + position);

        if (holder instanceof ArrivalViewHolder) {
            ((ArrivalViewHolder) holder).bind(arrivalInfoArrayList.get(position));
        }

        if (holder instanceof PositionViewHolder) {
            ((PositionViewHolder)holder).bind(positionInfoArrayList.get(position - arrivalInfoArrayListSize));
        }

    }

    @Override
    public int getItemCount() {
        Log.d("getItemCount", "" + (arrivalInfoArrayListSize + positionInfoArrayListSize));
        return arrivalInfoArrayListSize + positionInfoArrayListSize;
    }

    @Override
    public int getItemViewType(int position){
        Log.d("getItemViewType", "" + position);
        if(position < arrivalInfoArrayListSize){
            Log.d("VIEW_TYPE", "ARRIVAL");
            return VIEW_TYPE_ARRIVAL;
        }

        if(position - arrivalInfoArrayListSize < positionInfoArrayListSize){
            Log.d("VIEW_TYPE", "POSITION");
            return VIEW_TYPE_POSITION;
        }

        return -1;
    }

    public class ArrivalViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_stNm;
        private TextView tv_arsId;

        public ArrivalViewHolder(View v) {
            super(v);
            tv_stNm = v.findViewById(R.id.stNm);
            tv_arsId = v.findViewById(R.id.arsId);
        }

        public void bind(ArrivalInfo arrivalInfo) {
            tv_arsId.setText(arrivalInfo.getArsId());
            tv_stNm.setText(arrivalInfo.getStNm());
        }

    }

    public class PositionViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_bus1;
        private ImageView iv_bus2;
        // private TextView tv_plainNo1;
        // private TextView tv_plainNo2;

        public PositionViewHolder(View v) {
            super(v);
            iv_bus1 = v.findViewById(R.id.busImage1);
            iv_bus2 = v.findViewById(R.id.busImage2);
            // tv_plainNo1 = v.findViewById(R.id.plainNo1);
            // tv_plainNo2 = v.findViewById(R.id.plainNo2);
        }

        public void bind(PositionInfo positionInfo) {
            if(positionInfo.getStopFlag().equals("1")) iv_bus1.setVisibility(positionInfo.getSectOrd());
            if(positionInfo.getStopFlag().equals("0")) iv_bus2.setVisibility(positionInfo.getSectOrd());

        }
    }

}
