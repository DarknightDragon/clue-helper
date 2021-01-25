package com.github.DarknightDragon.cluehelper;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_tablayout );

        // setup swipable tab view
        ViewPager2 vp2 = findViewById( R.id.view_pager );
        vp2.setAdapter( new ViewsAdapter( this ) );

        // TabLayoutMediator
        TabLayout tabLayout = findViewById( R.id.tab_layout );
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, vp2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch ( position ) {
                    case 0:
                        tab.setText( "Characters" );
                        break;
                    case 1:
                        tab.setText( "Weapons" );
                        break;
                    case 2:
                        tab.setText( "Rooms" );
                        break;
                }
            }
        });
        tabLayoutMediator.attach();

        // get caller and open corresponding fragment
        int caller = getIntent().getExtras().getInt("id" );
        if ( caller == 0 || caller == 1 || caller == 2 )
        {
            vp2.setCurrentItem( caller );
        }
        else
        {
            throw new IllegalArgumentException( "Error! Caller number: " + caller );
        }
    }
}
