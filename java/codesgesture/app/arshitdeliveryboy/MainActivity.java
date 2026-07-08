package codesgesture.app.arshitdeliveryboy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import codesgesture.app.arshitdeliveryboy.Models.UserModel;
import codesgesture.app.arshitdeliveryboy.Services.CallJson;
import codesgesture.app.arshitdeliveryboy.Services.JsonCallbacks;
import codesgesture.app.arshitdeliveryboy.Services.NetParam;
import codesgesture.app.arshitdeliveryboy.Services.UserUtil;
import codesgesture.app.arshitdeliveryboy.Services.Utility;
import codesgesture.app.arshitdeliveryboy.Utils.SessionManage;

public class MainActivity extends AppCompatActivity implements JsonCallbacks {

    EditText mob,pass;
    Button btn_submit;
    ArrayList<NetParam> param;
    UserModel customerModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CheckLogins();
        mob=findViewById(R.id.mob);
        pass=findViewById(R.id.pass);
        btn_submit=findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Validate()) {
                    param = new ArrayList<NetParam>();
                    CallJson jc = new CallJson(MainActivity.this);
                    param.add(new NetParam("mobileno", mob.getText().toString()));
                    param.add(new NetParam("PASSWORD", pass.getText().toString()));
                    jc.SendRequest("login_delivery_boy", param, MainActivity.this, "login_delivery_boy", "Please wait while verifying..");
                }
            }
        });
    }

    private void CheckLogins() {
        customerModel = SessionManage.getCurrentUser(this);
        if (customerModel != null) {
            if (customerModel.getDelivery_boy_id() != null) {
                Intent act = new Intent(MainActivity.this, Dashboard.class);
                startActivity(act);
                finish();
            }
        }
    }

    @Override
    public void onPostSuceess(String json, String method) throws JSONException {
        UserModel sd = new UserModel();
        try {
            JSONObject obj = UserUtil.ConvertStringToJsonObject(json);
            if (obj.length() != 1) {
                sd.setDelivery_boy_id(obj.getString("delivery_boy_id"));
                sd.setId(obj.getString("id"));
                sd.setDelivery_boy_name(obj.getString("delivery_boy_name"));
                sd.setDelivery_boy_mobileno(obj.getString("delivery_boy_mobileno"));
                sd.setDelivery_boy_email(obj.getString("delivery_boy_email"));
                sd.setDelivery_boy_gender(obj.getString("delivery_boy_gender"));
                sd.setDelivery_boy_address_line_1(obj.getString("delivery_boy_address_line_1"));
                sd.setDelivery_boy_address_line_2(obj.getString("delivery_boy_address_line_2"));
                sd.setDelivery_boy_state_id(obj.getString("delivery_boy_state_id"));
                sd.setDelivery_boy_state_name(obj.getString("delivery_boy_state_name"));
                sd.setDelivery_boy_city_id(obj.getString("delivery_boy_city_id"));
                sd.setDelivery_boy_city_name(obj.getString("delivery_boy_city_name"));
                sd.setOtp(obj.getString("otp"));
                sd.setDelivery_boy_password(obj.getString("delivery_boy_password"));
                sd.setDelivery_boy_pincode(obj.getString("delivery_boy_pincode"));
                sd.setMobileno_verified(obj.getString("mobileno_verified"));
                sd.setDelivery_boy_photo(obj.getString("delivery_boy_photo"));
                sd.setDelivery_boy_paid_amount(obj.getString("delivery_boy_paid_amount"));
                sd.setDelivery_boy_due_amount(obj.getString("delivery_boy_due_amount"));
                sd.setDelivery_boy_status(obj.getString("delivery_boy_status"));
                SessionManage.SetCustomerSessions(getApplicationContext(), sd);
                Intent act = new Intent(MainActivity.this, Dashboard.class);
                startActivity(act);
                UserUtil.ShowMsg("Login Successfully !", MainActivity.this);
                finish();
            } else {
                Utility.ShowMEssage(MainActivity.this, "Invalid mobile no and Password!");
            }
        } catch (JSONException e) {
            Utility.ShowMEssage(MainActivity.this, "Invalid mobile no and Password!");
            e.printStackTrace();
        }
    }

    @Override
    public void onPostError(String msg) {

    }

    private boolean Validate() {
        Boolean valid = true;
        if (mob.getText().length() == 0) {
            mob.setError("Please  Enter Mobile No.");
            valid=false;
        } else if (pass.getText().length() == 0) {
            pass.setError("Please Enter Password");
            valid=false;
        }
        return valid;
    }
    
}