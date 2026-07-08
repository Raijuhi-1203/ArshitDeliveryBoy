package codesgesture.app.arshitdeliveryboy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AutoCompleteTextView;
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

import codesgesture.app.arshitdeliveryboy.Adapter.FAQAdapter;
import codesgesture.app.arshitdeliveryboy.Models.PolicyModel;
import codesgesture.app.arshitdeliveryboy.Services.CallJson;
import codesgesture.app.arshitdeliveryboy.Services.JsonCallbacks;
import codesgesture.app.arshitdeliveryboy.Services.NetParam;
import codesgesture.app.arshitdeliveryboy.Utils.SessionManage;

public class PageFAQ extends AppCompatActivity  {
    TextView txpolicy;

    ArrayList<PolicyModel> policyModels=new ArrayList<>();
    FAQAdapter faqAdapter;
    RecyclerView recycler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq);

        ImageView btback=findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title = findViewById(R.id.title);
        title.setText("FAQ");

        txpolicy=findViewById(R.id.txpolicy);
        recycler=findViewById(R.id.recycler);

        GridLayoutManager mLayoutManager = new GridLayoutManager(PageFAQ.this, 1);
        recycler.setLayoutManager(mLayoutManager);
        faqAdapter = new FAQAdapter(PageFAQ.this, policyModels, R.layout.item_faq);
        recycler.setAdapter(faqAdapter);
        recycler.setItemViewCacheSize(policyModels.size());
        GetData();

    }


    private void GetData() {
        policyModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(PageFAQ.this);
        jc.SendRequest("get_faq", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    PolicyModel product = new PolicyModel();
                    product.setFaq_description(obj.getString("faq_description"));
                    product.setFaq_question(obj.getString("faq_question"));
                    policyModels.add(product);
                }
                faqAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPostError(String msg) {

            }
        }, "", "Loading..");
    }


}