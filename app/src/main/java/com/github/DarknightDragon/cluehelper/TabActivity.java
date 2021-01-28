package com.github.DarknightDragon.cluehelper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Iterator;
import java.util.Set;

public class TabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_tablayout );

        // setup swipeable tab view
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

        // add back button
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        // get caller and open corresponding fragment
        int caller = getIntent().getExtras().getInt("id" );
        if ( caller == 0 || caller == 1 || caller == 2 )
        {
            vp2.setCurrentItem( caller );
        }
    }

    @Override
    protected void onNewIntent( Intent intent )
    {
        super.onNewIntent( intent );
        setIntent( intent );
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        ViewPager2 vp2 = findViewById( R.id.view_pager );
        if ( vp2.getAdapter() != null )
        {
            int caller = getIntent().getExtras().getInt( "id" );
            if ( vp2.getCurrentItem() != caller )
            {
                vp2.setCurrentItem( caller );
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item )
    {
        switch ( item.getItemId() )
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected( item );
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent( this, MainActivity.class );
        i.addFlags( Intent.FLAG_ACTIVITY_REORDER_TO_FRONT );
        startActivity( i );
    }

}
