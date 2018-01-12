package com.spoorthi.tasklogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText email,password;
    Button logInBtn;

    String logInURL = "http://52.224.222.102:9245/api/Login";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText)findViewById(R.id.emailED);
        password = (EditText)findViewById(R.id.passwordED);

        logInBtn = (Button)findViewById(R.id.logInBtn);

        logInBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == logInBtn)
        {
            String emailStr = email.getText().toString();
            String passStr = password.getText().toString();
            if(emailStr.isEmpty() && passStr.isEmpty())
            {
                Toast.makeText(MainActivity.this,"Validation error",Toast.LENGTH_SHORT).show();
            }
            else{
                JSONObject jObj = new JSONObject();
                try {
                    jObj.put("Email",""+emailStr);
                    jObj.put("Password",""+passStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                new logInRequest(){

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        if(s!=null)
                        {
                            Log.e("s",""+s);
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                boolean status = (boolean) jsonObject.get("Status");

                                if(status)
                                {
                                    String id  = (String)jsonObject.get("UserId");
                                    Intent i = new Intent(MainActivity.this,DashBoardActivity.class);
                                    i.putExtra("id",""+id);
                                    startActivity(i);
                                }
                                else {
                                    Toast.makeText(MainActivity.this,"Login error",Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                }.execute(logInURL,jObj.toString());
            }
        }
    }
}
