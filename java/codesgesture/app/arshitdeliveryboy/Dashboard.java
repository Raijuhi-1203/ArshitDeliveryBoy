package codesgesture.app.arshitdeliveryboy;

import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import codesgesture.app.arshitdeliveryboy.Adapter.FAQAdapter;
import codesgesture.app.arshitdeliveryboy.Adapter.OrderAdapter;
import codesgesture.app.arshitdeliveryboy.Models.OrderModel;
import codesgesture.app.arshitdeliveryboy.Models.PolicyModel;
import codesgesture.app.arshitdeliveryboy.Models.UserModel;
import codesgesture.app.arshitdeliveryboy.Services.CallJson;
import codesgesture.app.arshitdeliveryboy.Services.JsonCallbacks;
import codesgesture.app.arshitdeliveryboy.Services.NetParam;
import codesgesture.app.arshitdeliveryboy.Services.UserUtil;
import codesgesture.app.arshitdeliveryboy.Utils.CallJsonWithoutProgress;
import codesgesture.app.arshitdeliveryboy.Utils.SessionManage;

public class Dashboard extends AppCompatActivity {

    TextView totalamt,neworder,pdelivery,cdelivery,rorder;
    LinearLayout home,delivery,menu,collection,account;
    UserModel userModel;
    ArrayList<OrderModel> orderModels=new ArrayList<>();
    OrderAdapter orderAdapter;
    RecyclerView recycler;
    Button btnviewmore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        userModel=(UserModel) SessionManage.getCurrentUser(getApplicationContext());

        BindId();
        getcomplete();
        getpending();
        gettamt();

        GridLayoutManager mLayoutManager = new GridLayoutManager(Dashboard.this, 1);
        recycler.setLayoutManager(mLayoutManager);
        orderAdapter = new OrderAdapter(Dashboard.this, orderModels, R.layout.item_order);
        recycler.setAdapter(orderAdapter);
        recycler.setItemViewCacheSize(orderModels.size());
        GetData();

    }

    private void gettamt() {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress(Dashboard.this);
        param.add(new NetParam("customer_id",userModel.getDelivery_boy_id()));
        jc.SendRequest("get_total_amt", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONObject obj = UserUtil.ConvertStringToJsonObject(json);
                if (obj.length() != 0) {
                    totalamt.setText(obj.getString("tamt"));
                }
            }

            @Override
            public void onPostError(String msg) {

            }
        }, "", "Loading..");
    }

    private void GetData() {
        orderModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(Dashboard.this);
        jc.SendRequest("get_pending_order3", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    OrderModel product = new OrderModel();
                    product.setOrder_id(obj.getString("order_id"));
                    product.setOrder_delivery_date(obj.getString("order_delivery_date"));
                    product.setOrder_status(obj.getString("order_status"));
                    product.setTotal_order_amount(obj.getString("total_order_amount"));
                    product.setOrder_delivery_time(obj.getString("order_delivery_time"));
                    product.setOrder_id_temp(obj.getString("order_id_temp"));
                    product.setSub_order_id_temp(obj.getString("sub_order_id_temp"));
                    product.setSub_order_id(obj.getString("sub_order_id"));
                    product.setOrder_date(obj.getString("order_date"));
                    product.setOrder_time(obj.getString("order_time"));
                    product.setPayment_mode(obj.getString("payment_mode"));
                    product.setCustomer_name(obj.getString("customer_name"));
                    product.setCustomer_mobileno(obj.getString("customer_mobileno"));
                    product.setCustomer_email(obj.getString("customer_email"));
                    product.setBilling_address_line1(obj.getString("billing_address_line1"));
                    product.setBilling_address_line2(obj.getString("billing_address_line2"));
                    product.setBilling_city_name(obj.getString("billing_city_name"));
                    product.setBilling_state_name(obj.getString("billing_state_name"));
                    product.setBilling_pincode(obj.getString("billing_pincode"));
                    product.setBilling_landmark(obj.getString("billing_landmark"));
                    product.setCoupan_value(obj.getString("coupan_value"));
                    product.setCoupan_code(obj.getString("coupan_code"));
                    product.setProduct_shipping_charge(obj.getString("product_shipping_charge"));
                    orderModels.add(product);
                }
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPostError(String msg) {

            }
        }, "", "Loading..");
    }

    private void getcomplete() {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress(Dashboard.this);
        param.add(new NetParam("customer_id",userModel.getDelivery_boy_id()));
        jc.SendRequest("get_complete_qty", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONObject obj = UserUtil.ConvertStringToJsonObject(json);
                if (obj.length() != 0) {
                    cdelivery.setText(obj.getString("qty"));
                }
            }

            @Override
            public void onPostError(String msg) {

            }
        }, "", "Loading..");
    }

    private void getpending() {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress(Dashboard.this);
        param.add(new NetParam("customer_id",userModel.getDelivery_boy_id()));
        jc.SendRequest("get_pending_qty", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONObject obj = UserUtil.ConvertStringToJsonObject(json);
                if (obj.length() != 0) {
                    pdelivery.setText(obj.getString("qty"));
                    neworder.setText(obj.getString("qty"));
                }
            }

            @Override
            public void onPostError(String msg) {

            }
        }, "", "Loading..");
    }

    private void BindId() {
        totalamt=findViewById(R.id.totalamt); neworder=findViewById(R.id.neworder);
        pdelivery=findViewById(R.id.pdelivery); cdelivery=findViewById(R.id.cdelivery);
        rorder=findViewById(R.id.rorder); home=findViewById(R.id.home);
        delivery=findViewById(R.id.delivery); menu=findViewById(R.id.menu);
        account=findViewById(R.id.account); collection=findViewById(R.id.collection);

        recycler=findViewById(R.id.recycler);btnviewmore=findViewById(R.id.btnviewmore);

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,Profile.class));
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,Dashboard.class));
            }
        });
        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,MyDelivery.class));
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,MenuPage.class));
            }
        });
        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,CollectionReport.class));
            }
        });
        btnviewmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,PagePendingOrder.class));
            }
        });
    }

}