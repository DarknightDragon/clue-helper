package com.github.DarknightDragon.cluehelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
        Button btn_settings = findViewById( R.id.btn_settings );
        btn_settings.setOnClickListener( this );
    }

    @Override
    public void onClick( View v ) {
        Intent intent = new Intent( this, TabActivity.class );
        intent.addFlags( Intent.FLAG_ACTIVITY_REORDER_TO_FRONT );
        switch ( v.getId() ) {
            case R.id.btn_characters:
                // open character fragment
                intent.putExtra( "id", 0 );
                break;
            case R.id.btn_weapons:
                // open weapon fragment
                intent.putExtra( "id", 1 );
                break;
            case R.id.btn_rooms:
                // open room fragment
                intent.putExtra( "id", 2 );
                break;
            default:
                // open settings page
        }
        startActivity( intent );
    }
}