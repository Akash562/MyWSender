package com.mmi.mywsender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    MaterialButton btn_send;
    TextInputEditText ed_number,ed_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed_number=findViewById(R.id.ed_no);
        ed_msg=findViewById(R.id.ed_msg);

        btn_send=findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_number.getText().toString().isEmpty()){
                    ed_number.setError("Please Enter Number");
                }else if(ed_msg.getText().toString().isEmpty()){
                    ed_msg.setError("Please Enter Message here");
                }else{
                    sendTextMsgToWhatsapp();
                }
            }
        });


    }

    public void sendTextMsgToWhatsapp(){

        boolean installed  = appInstalledOrNot("com.whatsapp");

        if (installed ) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setPackage("com.whatsapp");
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"91"+ed_number.getText().toString() +"&text="+ed_msg.getText().toString()));
                startActivity(intent);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "App is not installed on your phone", Toast.LENGTH_SHORT).show();
        }

    }

    public void sendImageToWhatspp(){

        boolean installed  = appInstalledOrNot("com.whatsapp");

        if (installed) {
            Intent sendIntent = new Intent("android.intent.action.MAIN");
        //    sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile());
        //    sendIntent.putExtra("jid", toNumber + "@s.whatsapp.net");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "message");
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setPackage("com.whatsapp");
            sendIntent.setType("image/png");
            startActivity(sendIntent);
        }else {
            Toast.makeText(getApplicationContext(), "App is not installed on your phone", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean appInstalledOrNot(String uri)  {
        PackageManager pm = getApplicationContext().getPackageManager();
        boolean app_installed = false;
        try{
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }catch (PackageManager.NameNotFoundException e){
            app_installed = false;
        }
        return app_installed ;
    }


}