package com.github.DarknightDragon.cluehelper;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TabActivity extends AppCompatActivity {
    ViewPager2 vp2;
    ViewsAdapter viewsAdapter;
    TabLayout tabLayout;
    TabLayoutMediator tabLayoutMediator;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_tablayout );

        // setup swipeable tab view
        vp2 = findViewById( R.id.view_pager );
        viewsAdapter = new ViewsAdapter( this );
        vp2.setAdapter( viewsAdapter );

        // TabLayoutMediator
        tabLayout = findViewById( R.id.tab_layout );
        tabLayoutMediator = new TabLayoutMediator( tabLayout, vp2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab( @NonNull TabLayout.Tab tab, int position ) {
                switch ( position ) {
                    case 0:
                        tab.setText( "Home" );
                        break;
                    case 1:
                        tab.setText( "Characters" );
                        break;
                    case 2:
                        tab.setText( "Weapons" );
                        break;
                    case 3:
                        tab.setText( "Rooms" );
                        break;
                }
            }
        } );
        tabLayoutMediator.attach();
    }
}
