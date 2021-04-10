package com.github.DarknightDragon.cluehelper;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.nio.file.Watchable;
import java.util.ArrayList;

public class ViewsAdapter extends FragmentStateAdapter {
    static ArrayList<Fragment> fragList = new ArrayList<>();

    public ViewsAdapter( @NonNull FragmentActivity fragmentActivity ) {
        super( fragmentActivity );

        // init array to stop same fragment having multiple instances
        if ( fragList.isEmpty() ) {
            fragList.add( 0, new HomeFragment() );
            fragList.add( 1, new CharacterFragment() );
            fragList.add( 2, new WeaponFragment() );
            fragList.add( 3, new RoomFragment() );
        }
    }

    @NonNull
    @Override
    public Fragment createFragment( int position ) {
        Fragment frag = null;
        switch ( position ) {
            case 0:
                frag = fragList.get( 0 );
                break;
            case 1:
                frag = fragList.get( 1 );
                break;
            case 2:
                frag = fragList.get( 2 );
                break;
            case 3:
                frag = fragList.get( 3 );
                break;
            default:
                Log.e( "Fragment Position Error", "Fragment position is not a correctly listed value! The value is: " + position );
                throw new IllegalArgumentException( "Error in Fragment position! Position #: " + position );
        }
        return frag;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
