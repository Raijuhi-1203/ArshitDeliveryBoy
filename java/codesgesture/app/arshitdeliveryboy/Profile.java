package codesgesture.app.arshitdeliveryboy;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import codesgesture.app.arshitdeliveryboy.Models.CityModel;
import codesgesture.app.arshitdeliveryboy.Models.StateModel;
import codesgesture.app.arshitdeliveryboy.Models.UserModel;
import codesgesture.app.arshitdeliveryboy.Services.CallJson;
import codesgesture.app.arshitdeliveryboy.Services.JsonCallbacks;
import codesgesture.app.arshitdeliveryboy.Services.NetParam;
import codesgesture.app.arshitdeliveryboy.Services.UserUtil;
import codesgesture.app.arshitdeliveryboy.Services.Utility;
import codesgesture.app.arshitdeliveryboy.Utils.SessionManage;

public class Profile extends AppCompatActivity {

    EditText txnm,txmail,txmob,add,txcp,txnp;
    TextView imguserpicnm;
    ImageView img_user;
    Spinner spnrgender,spnrstate,spnrcity;
    String spnrid;
    private static final int PERMISSION_REQUEST_CODE = 200 ;
    private static final int PERMISSION_REQUEST_CODE1 = 100 ;
    private static final int PERMISSION_REQUEST_CODE2 = 300 ;
    private static final int CAMERA_REQUEST = 1888;
    Bitmap userphoto;
    UserModel customerModel;
    Button btn_submit2,btn_submit,btn_submit3;
    String imgString;
    ArrayAdapter<StateModel> stateModelArrayAdapter;
    ArrayList<StateModel> stateModels=new ArrayList<>();
    ArrayAdapter<CityModel> cityModelArrayAdapter;
    ArrayList<CityModel> cityModels=new ArrayList<>();
    String spnrstid,spnrctid,statnm,citynm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        ImageView btback=findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title = findViewById(R.id.title);
        title.setText("My Profile");

        customerModel=(UserModel) SessionManage.getCurrentUser(getApplicationContext());
        txnm=findViewById(R.id.txnm);
        imguserpicnm=findViewById(R.id.imguserpicnm);
        txmob=findViewById(R.id.txmob);
        txmail=findViewById(R.id.txmail);
        add=findViewById(R.id.add);
        txcp=findViewById(R.id.txcp);
        txnp=findViewById(R.id.txnp);
        img_user=findViewById(R.id.img_user);
        spnrgender=findViewById(R.id.spnrgender);
        btn_submit2=findViewById(R.id.btn_submit2);
        btn_submit=findViewById(R.id.btn_submit);
        btn_submit3=findViewById(R.id.btn_submit3);

        spnrcity=findViewById(R.id.spnrcity);
        spnrstate=findViewById(R.id.spnrstate);

        spnrstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = spnrstate.getSelectedItemPosition();
                spnrstid=String.valueOf(stateModels.get(pos).getState_id());
                statnm=String.valueOf(stateModels.get(pos).getState_name());
                CityCall(spnrstid);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spnrcity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = spnrcity.getSelectedItemPosition();
                spnrctid=String.valueOf(cityModels.get(pos).getDistrict_id());
                citynm=String.valueOf(cityModels.get(pos).getDistrict_name());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        txnm.setText(customerModel.getDelivery_boy_name());
        txmob.setText(customerModel.getDelivery_boy_mobileno());
        txmail.setText(customerModel.getDelivery_boy_email());
        add.setText(customerModel.getDelivery_boy_address_line_1()+", "+customerModel.getDelivery_boy_address_line_2());

        if (checkPermission()) {
        } else {
            requestPermission();
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrgender.setAdapter(adapter);

        spnrgender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spnrid = spnrgender.getSelectedItem().toString();;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        img_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        btn_submit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txcp.getText().length()==0){
                    txcp.setError("Please enter current password");
                }else if(txnp.getText().length()==0){
                    txnp.setError("Please enter new password");
                }else {
                    UpdatePassword();
                }
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imguserpicnm.getText().length()==0){
                    imguserpicnm.setError("Please select Picture first");
                }else {
                    SavePicture();
                }
            }
        });

        btn_submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txnm.getText().length()==0){
                    txnm.setError("Please enter name");
                }else  if (txmail.getText().length()==0){
                    txmail.setError("Please enter mail id");
                }else if (txmob.getText().length()==0){
                    txmob.setError("Please enter mobile no");
                }else if (add.getText().length()==0){
                    add.setError("Please enter address");
                }else {
                    UpdateData();
                }
            }
        });

        stateModels = new ArrayList<>();
        stateModelArrayAdapter = new ArrayAdapter<StateModel>(Profile.this, android.R.layout.simple_spinner_item, stateModels);
        stateModelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrstate.setAdapter(stateModelArrayAdapter);

        cityModels = new ArrayList<>();
        cityModelArrayAdapter = new ArrayAdapter<CityModel>(Profile.this, android.R.layout.simple_spinner_item, cityModels);
        cityModelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrcity.setAdapter(cityModelArrayAdapter);

        AreaJsonCall();

    }

    private void UpdatePassword() {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(Profile.this);
        param.add(new NetParam("password",txcp.getText().toString()));
        param.add(new NetParam("newpass",txnp.getText().toString()));
        param.add(new NetParam("custid",customerModel.getDelivery_boy_id()));
        jc.SendRequest("update_dpassword", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                UserUtil.ShowMsg("Password Updated !!",Profile.this);
                finish();
            }
            @Override
            public void onPostError(String msg) {

            }
        }, " ", "Loading..");
    }

    private void SavePicture() {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(Profile.this);
        param.add(new NetParam("b64string",imgString));
        param.add(new NetParam("custid",customerModel.getDelivery_boy_id()));
        param.add(new NetParam("filenm",imguserpicnm.getText().toString()));
        jc.SendRequest("dupdate_picture", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                UserUtil.ShowMsg("Picture Uploaded !!",Profile.this);
                finish();
            }
            @Override
            public void onPostError(String msg) {

            }
        }, " ", "Loading..");
    }

    private void UpdateData() {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(Profile.this);
        param.add(new NetParam("name",txnm.getText().toString()));
        param.add(new NetParam("mobile",txmob.getText().toString()));
        param.add(new NetParam("gender",spnrgender.getSelectedItem().toString()));
        param.add(new NetParam("mail",txmail.getText().toString()));
        param.add(new NetParam("address",add.getText().toString()));
        param.add(new NetParam("stateid",spnrstid));
        param.add(new NetParam("statenm",statnm));
        param.add(new NetParam("cityid",spnrctid));
        param.add(new NetParam("citynm",citynm));
        jc.SendRequest("dupdate_user", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                UserUtil.ShowMsg("Details Updated !!",Profile.this);
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
                        Intent act = new Intent(Profile.this, Dashboard.class);
                        startActivity(act);
                       // UserUtil.ShowMsg("Login Successfully !", Profile.this);
                        finish();
                    } else {
                        Utility.ShowMEssage(Profile.this, "Invalid details !");
                    }
                } catch (JSONException e) {
                    Utility.ShowMEssage(Profile.this, "Invalid details !");
                    e.printStackTrace();
                }
            }
            @Override
            public void onPostError(String msg) {

            }
        }, " ", "Loading..");
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE1);
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE2);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {
            userphoto = (Bitmap) data.getExtras().get("data");
            img_user.setImageBitmap(userphoto);
            // String imgnm3 = txnm.getText().toString() + "CP" + ".png";
            String imgnm3 = "demoCP" + ".png";


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            userphoto.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteFormat = stream.toByteArray();
            imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

            imguserpicnm.setText(imgnm3);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }

                        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }

                        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(Profile.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void CityCall(String spnrstid) {
        cityModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(Profile.this);
        param.add(new NetParam("state_id",spnrstid));
        jc.SendRequest("get_city", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    CityModel mod = new CityModel();
                    mod.setDistrict_id(obj.getString("district_id"));
                    mod.setDistrict_name(obj.getString("district_name"));
                    cityModels.add(mod);
                    cityModelArrayAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onPostError(String msg) {
            }
        }, "LOGIN", "Please wait while getting..");
    }
    private void AreaJsonCall() {
        stateModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(Profile.this);
        jc.SendRequest("get_state", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    StateModel mod = new StateModel();
                    mod.setState_id(obj.getString("state_id"));
                    mod.setState_name(obj.getString("state_name"));
                    stateModels.add(mod);
                    stateModelArrayAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onPostError(String msg) {
            }
        }, "LOGIN", "Please wait while getting..");
    }

}
