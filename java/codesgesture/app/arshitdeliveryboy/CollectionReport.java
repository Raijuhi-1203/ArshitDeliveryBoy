package codesgesture.app.arshitdeliveryboy;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import codesgesture.app.arshitdeliveryboy.Adapter.OrderAdapter;
import codesgesture.app.arshitdeliveryboy.Models.OrderModel;
import codesgesture.app.arshitdeliveryboy.Models.UserModel;
import codesgesture.app.arshitdeliveryboy.Services.CallJson;
import codesgesture.app.arshitdeliveryboy.Services.JsonCallbacks;
import codesgesture.app.arshitdeliveryboy.Services.NetParam;
import codesgesture.app.arshitdeliveryboy.Utils.SessionManage;

public class CollectionReport extends AppCompatActivity {
    EditText fdt,todt;
    Button btsearch;
    OrderAdapter orderAdapter;
    ArrayList<OrderModel> orderModels=new ArrayList<>();
    RecyclerView recycler;
    UserModel userModel;

    DatePickerDialog datePickerDialog;
    DatePickerDialog datePickerDialog2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_report);
        userModel=(UserModel) SessionManage.getCurrentUser(getApplicationContext());

        ImageView btback=findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title = findViewById(R.id.title);
        title.setText("Cash Collection");

        fdt=findViewById(R.id.fdt);todt=findViewById(R.id.todt);
        btsearch=findViewById(R.id.btsearch);recycler=findViewById(R.id.recycler);

        btsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetData();
            }
        });

        GridLayoutManager mLayoutManager = new GridLayoutManager(CollectionReport.this, 1);
        recycler.setLayoutManager(mLayoutManager);
        orderAdapter = new OrderAdapter(CollectionReport.this, orderModels, R.layout.item_c_order);
        recycler.setAdapter(orderAdapter);
        recycler.setItemViewCacheSize(orderModels.size());


        fdt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                BindDate();
                return false;
            }
        });

        todt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                BindDate2();
                return false;
            }
        });
    }

    private void GetData() {
        orderModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(CollectionReport.this);
        param.add(new NetParam("fdt",fdt.getText().toString()));
        param.add(new NetParam("todt",todt.getText().toString()));
        param.add(new NetParam("customer_id",userModel.getDelivery_boy_id()));
        jc.SendRequest("get_collection", param, new JsonCallbacks() {
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

    private void BindDate() {
        // perform click event on text view
        fdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog2 = new DatePickerDialog(CollectionReport.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text

                                int mnth = monthOfYear+1;
                                int day = dayOfMonth;

                                if(mnth <= 9 && day <= 9){
                                    fdt.setText(year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth);
                                }else if(mnth <= 9){
                                    fdt.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);
                                }else if(day <= 9){
                                    fdt.setText(year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth);
                                }else {
                                    fdt.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog2.show();
            }
        });
    }

    private void BindDate2() {
        // perform click event on text view
        todt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(CollectionReport.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text

                                int mnth = monthOfYear+1;
                                int day = dayOfMonth;

                                if(mnth <= 9 && day <= 9){
                                    todt.setText(year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth);
                                }else if(mnth <= 9){
                                    todt.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);
                                }else if(day <= 9){
                                    todt.setText(year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth);
                                }else {
                                    todt.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }
}
