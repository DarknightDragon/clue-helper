package com.github.DarknightDragon.cluehelper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static boolean reset = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup buttons
        Button btn_character = findViewById( R.id.btn_characters );
        btn_character.setOnClickListener( this );
        Button btn_weapon = findViewById( R.id.btn_weapons );
        btn_weapon.setOnClickListener( this );
        Button btn_room = findViewById( R.id.btn_rooms );
        btn_room.setOnClickListener( this );
        Button btn_settings = findViewById( R.id.btn_reset);
        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder( MainActivity.this );
                builder.setTitle( "Confirm?" );
                builder.setMessage( "Are you sure you want to reset everything?" );
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reset = true;
                        Intent intent = new Intent( MainActivity.this, MainActivity.class );
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        dialog.dismiss();
                        startActivity( intent );
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public void onClick( View v ) {
        Intent intent = new Intent(this, TabActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        if ( reset )
        {
            intent.putExtra( "reset", true );
            reset = false;
        }

        switch ( v.getId() ) {
            case R.id.btn_characters:
                // open character fragment
                intent.putExtra("id", 0);
                break;
            case R.id.btn_weapons:
                // open weapon fragment
                intent.putExtra("id", 1);
                break;
            case R.id.btn_rooms:
                // open room fragment
                intent.putExtra("id", 2);
        }

        startActivity(intent);
    }
}