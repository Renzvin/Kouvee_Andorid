package com.app.p3l.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.p3l.R;
import com.app.p3l.Temporary.TemporaryRoleId;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity {
    private Button login;
    private EditText username,password;
    private TextView txtsignin;
    private ConstraintLayout parent;
    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();
    private Animation topAnim,botAnim;
    private CircleImageView profile;

    String status = "-";
    String data = "-";
    String role = "-";
    String nama = "-";
    int id = 0;
    ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        botAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        username = (EditText) findViewById(R.id.LUsername);
        password = (EditText) findViewById(R.id.LPassword);
        profile=(CircleImageView)findViewById(R.id.profile_image);
        txtsignin=(TextView)findViewById(R.id.txtSignIn);
        parent=(ConstraintLayout)findViewById(R.id.constraintLayout2);
        dialog = new ProgressDialog(this);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(username.getText().toString(), password.getText().toString());
                progDialog();
                waitingResponse();
            }
        });

        profile.setAnimation(topAnim);
        parent.setAnimation(botAnim);
        username.setAnimation(botAnim);
        password.setAnimation(botAnim);
        login.setAnimation(botAnim);
        txtsignin.setAnimation(botAnim);

        getSupportActionBar().hide();
    }

    private void progDialog() {
        dialog.setMessage("Mohon menunggu...");
        dialog.show();
    }

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };

    private void waitingResponse() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(status.equalsIgnoreCase("true")) {
                    if (role.equalsIgnoreCase("Admin")){
                        Intent i = new Intent(LoginActivity.this,MainActivity.class);
                        i.putExtra("nama",nama);
                        i.putExtra("role",role);
                        TemporaryRoleId.id = id;
                        startActivity(i);
                    } else if(role.equalsIgnoreCase("CS")){
                        Intent i = new Intent(LoginActivity.this,CSActivity.class);
                        i.putExtra("nama",nama);
                        i.putExtra("role",role);
                        TemporaryRoleId.id = id;
                        startActivity(i);
                    } else{
                        Toast.makeText(LoginActivity.this,"Role anda tidak jelas",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, "Email / password salah", Toast.LENGTH_SHORT).show();
                }


                dialog.dismiss();
            }
        }, 4000);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if (mHandler != null) { mHandler.removeCallbacks(mRunnable); }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back again to exit and logout", Toast.LENGTH_SHORT).show();

        mHandler.postDelayed(mRunnable, 2000);
        finish();
    }


    private void loginUser(final String username, final String password) {
        final String url = "http://renzvin.com/kouvee/api/Pegawai/login/";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest post = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            status = jsonObject.getString("status");
                            data = jsonObject.getString("data");

                            System.out.println("Response : " + status);
                            System.out.println("Message  : " + data);


                            String user = jsonObject.getString("data");
                            JSONObject objUser = new JSONObject(user);
                            nama = objUser.getString("nama");
                            role = objUser.getString("role_name");
                            id = objUser.getInt("id");
                            System.out.println(role);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this,"Gagal",Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }
        };
        post.setRetryPolicy(
                new DefaultRetryPolicy(
                        50000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
        );
        queue.add(post);
    }
}
