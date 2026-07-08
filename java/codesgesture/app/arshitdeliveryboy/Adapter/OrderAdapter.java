package codesgesture.app.arshitdeliveryboy.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import codesgesture.app.arshitdeliveryboy.Models.OrderModel;
import codesgesture.app.arshitdeliveryboy.Models.OrderModel;
import codesgesture.app.arshitdeliveryboy.PageOrderDetails;
import codesgesture.app.arshitdeliveryboy.PagePendingOrder;
import codesgesture.app.arshitdeliveryboy.R;
import codesgesture.app.arshitdeliveryboy.Services.CallJson;
import codesgesture.app.arshitdeliveryboy.Services.JsonCallbacks;
import codesgesture.app.arshitdeliveryboy.Services.NetParam;
import codesgesture.app.arshitdeliveryboy.Services.UserUtil;
import codesgesture.app.arshitdeliveryboy.Utils.SessionManage;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private ArrayList<OrderModel> arrayList;
    private Context context;
    String Userid="";
    String User="";
    private int layout;

    public OrderAdapter(Context context, ArrayList<OrderModel> arrayList, int layout) {
        this.arrayList = arrayList;
        this.context = context;
        this.layout=layout;
        this.Userid = SessionManage.getCurrentUser(context.getApplicationContext()).getDelivery_boy_id();
        this.User = SessionManage.getCurrentUser(context.getApplicationContext()).getDelivery_boy_name();
    }

    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new OrderAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final OrderAdapter.ViewHolder holder, final int i) {
        final OrderModel data = arrayList.get(i);

        holder.orderid.setText("Order ID : "+data.getOrder_id());
        holder.date.setText("Placed On : "+data.getOrder_date());
        holder.pstatus.setText("Payment Status : "+data.getPayment_mode());
        holder.amt.setText("Total Amount : ₹ "+data.getTotal_order_amount());
        holder.status.setText(data.getOrder_status());

        holder.btview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PageOrderDetails.class);
                intent.putExtra("data",data);
                context.startActivity(intent);
            }
        });

        if (data.getOrder_status().equals("Confirm")){
            holder.btaccept.setVisibility(View.VISIBLE);
        }else {
            holder.btaccept.setVisibility(View.GONE);
        }

        holder.btaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<NetParam> param;
                param = new ArrayList<NetParam>();
                CallJson jc = new CallJson((Activity) context);
                param.add(new NetParam("orderid",data.getOrder_id()));
                param.add(new NetParam("delivery_boy_id",Userid));
                param.add(new NetParam("delivery_boy_name",User));
                jc.SendRequest("accept_order", param, new JsonCallbacks() {
                    @Override
                    public void onPostSuceess(String json, String method) throws JSONException {
                        UserUtil.ShowMsg("Order Accepted for delivery.",context);
                    }

                    @Override
                    public void onPostError(String msg) {

                    }
                }, "", "Loading..");
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderid,date,pstatus,amt;
        Button btview,status,btaccept;

        ViewHolder(View view) {
            super(view);
            orderid = view.findViewById(R.id.orderid);
            date= view.findViewById(R.id.date);
            pstatus= view.findViewById(R.id.pstatus);
            amt= view.findViewById(R.id.amt);
            btview= view.findViewById(R.id.btview);
            status= view.findViewById(R.id.status);
            btaccept= view.findViewById(R.id.btaccept);
        }
    }

    public void updateList(ArrayList<OrderModel> list) {
        arrayList = list;
        notifyDataSetChanged();
    }

}