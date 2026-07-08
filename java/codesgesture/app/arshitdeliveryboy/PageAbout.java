package codesgesture.app.arshitdeliveryboy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import codesgesture.app.arshitdeliveryboy.Models.UserModel;
import codesgesture.app.arshitdeliveryboy.Services.CallJson;
import codesgesture.app.arshitdeliveryboy.Services.JsonCallbacks;
import codesgesture.app.arshitdeliveryboy.Services.NetParam;
import codesgesture.app.arshitdeliveryboy.Services.UserUtil;
import codesgesture.app.arshitdeliveryboy.Utils.SessionManage;

public class PageAbout extends AppCompatActivity {
    TextView txpolicy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        ImageView btback=findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title = findViewById(R.id.title);
        title.setText("About");

        txpolicy=findViewById(R.id.txpolicy);

        BindPolicy();
    }

    private void BindPolicy() {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(PageAbout.this);
        jc.SendRequest("get_about", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONObject obj = UserUtil.ConvertStringToJsonObject(json);
                if (obj.length() != 0) {
                    txpolicy.setText(Html.fromHtml(obj.getString("privacy_information")));
                }            }

            @Override
            public void onPostError(String msg) {

            }
        }, "", "Loading..");

    }


}