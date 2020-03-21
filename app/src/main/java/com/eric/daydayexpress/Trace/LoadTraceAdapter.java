package com.eric.daydayexpress.Trace;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.eric.daydayexpress.R;

import org.w3c.dom.Text;

import java.util.List;

public class LoadTraceAdapter extends RecyclerView.Adapter<LoadTraceAdapter.ViewHolder> {


    private List<LoadTrace>mLoadTraces;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView traceInfo;
        TextView time;
        public ViewHolder(View view){
            super(view);
            traceInfo = (TextView)view.findViewById(R.id.tv_traceInfo);
            time = (TextView)view.findViewById(R.id.tv_time_item);

        }
    }


    public LoadTraceAdapter(List<LoadTrace>loadTraceList){
        mLoadTraces = loadTraceList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent , int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.loadtrace_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    //滚动到这里加载
    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        LoadTrace loadTrace = mLoadTraces.get(position);
        holder.traceInfo.setText(loadTrace.getTraceInfo());
        holder.time.setText(loadTrace.getTime());
    }


    @Override
    public  int  getItemCount(){

        return mLoadTraces.size();
    }

}
