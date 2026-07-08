package codesgesture.app.arshitdeliveryboy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import codesgesture.app.arshitdeliveryboy.Models.OrderModel;
import codesgesture.app.arshitdeliveryboy.Models.UserModel;
import codesgesture.app.arshitdeliveryboy.Services.CallJson;
import codesgesture.app.arshitdeliveryboy.Services.JsonCallbacks;
import codesgesture.app.arshitdeliveryboy.Services.NetParam;
import codesgesture.app.arshitdeliveryboy.Services.UserUtil;
import codesgesture.app.arshitdeliveryboy.Utils.CallJsonWithoutProgress;
import codesgesture.app.arshitdeliveryboy.Utils.SessionManage;

public class PageOrderDetails extends AppCompatActivity {

    OrderModel orderModel;
    UserModel userModel;
    TextView orderid,sorderid,status,cnm,add,mob,mail;
    TextView pmode,tmrp,scharge,dis,cdis,gtotal;
    Button btcancel,btallcncl,istatus,btdelivered;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details);
        userModel=(UserModel) SessionManage.getCurrentUser(getApplicationContext());
        orderModel=(OrderModel)getIntent().getSerializableExtra("data");

        ImageView btback=findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title = findViewById(R.id.title);
        title.setText("Order Details : "+orderModel.getOrder_id());


        gtotal=findViewById(R.id.gtotal);cdis=findViewById(R.id.cdis);
        dis=findViewById(R.id.dis);scharge=findViewById(R.id.scharge);
        tmrp=findViewById(R.id.tmrp);pmode=findViewById(R.id.pmode);

        orderid=findViewById(R.id.orderid);sorderid=findViewById(R.id.sorderid);
        status=findViewById(R.id.status);cnm=findViewById(R.id.cnm);
        add=findViewById(R.id.add);mob=findViewById(R.id.mob);
        mail=findViewById(R.id.mail);

        btcancel=findViewById(R.id.btcancel);btallcncl=findViewById(R.id.btallcncl);
        istatus=findViewById(R.id.istatus); btdelivered=findViewById(R.id.btdelivered);

        gtotal.setText("₹ "+orderModel.getTotal_order_amount());
        cdis.setText("₹ "+orderModel.getCoupan_value());
        tmrp.setText("₹ "+orderModel.getTotal_order_amount());
        pmode.setText(orderModel.getPayment_mode());
        scharge.setText("₹ "+orderModel.getProduct_shipping_charge());

        orderid.setText("Order ID : "+orderModel.getOrder_id());
        sorderid.setText("SubOrder ID : "+orderModel.getSub_order_id());
        status.setText("Status : "+orderModel.getOrder_status());
        cnm.setText(orderModel.getCustomer_name());
        add.setText(orderModel.getBilling_address_line1()+", "+orderModel.getBilling_address_line2()+","+orderModel.getBilling_city_name()+", "+orderModel.getBilling_state_name()+", "+orderModel.getBilling_pincode());
        mob.setText(orderModel.getCustomer_mobileno());
        mail.setText(orderModel.getCustomer_email());

        istatus.setText(orderModel.getOrder_status());

        GetDiscount(orderModel.getOrder_id());

        if (orderModel.getOrder_status().equals("Out For Delivery")){
            btdelivered.setVisibility(View.VISIBLE);
        }else {
            btdelivered.setVisibility(View.GONE);
        }
        btdelivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderDelivered();
            }
        });

    }

    private void OrderDelivered() {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress(PageOrderDetails.this);
        param.add(new NetParam("orderid",orderModel.getOrder_id()));
        jc.SendRequest("develired_order", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                UserUtil.ShowMsg("Oreder Delivered !",PageOrderDetails.this);
                finish();
            }

            @Override
            public void onPostError(String msg) {

            }
        }, "", "Loading..");
    }

    private void GetDiscount(String order_id) {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress(PageOrderDetails.this);
        param.add(new NetParam("orderid",order_id));
        jc.SendRequest("get_discount", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    dis.setText("₹ "+obj.getString("discount"));
                }
            }

            @Override
            public void onPostError(String msg) {

            }
        }, "", "Loading..");
    }

}
