package com.example.seniorproject.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.seniorproject.R;
import com.example.seniorproject.TrackInspector.HeaderData;
import com.example.seniorproject.TrackInspector.MenuTI;

/**
 * Created by willw on 9/12/2017.
 */

public class MenuAdmin extends AppCompatActivity {
    private Button manageaccounts;
    private Button modifymasterfiles;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_admin);
       manageaccounts = (Button) findViewById(R.id.ManageAccounts);
       modifymasterfiles= (Button) findViewById(R.id.ModifyMasterFiles);
       setButton();
    }
    private void setButton() {
        manageaccounts.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MenuAdmin.this, ManageAccounts.class);
                startActivity(intent);
            }
        });
        modifymasterfiles.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MenuAdmin.this, ModiyMasterFiles.class);
                startActivity(intent);
            }
        });
    }



}