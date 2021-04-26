package com.github.DarknightDragon.cluehelper;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;

public class RoomFragment extends Fragment {
    // flag for reset button pressed
    private volatile boolean reset = false;

    public RoomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        // set listener for reset button
        getParentFragmentManager().setFragmentResultListener( "Room", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult( @NonNull String requestKey, @NonNull Bundle result ) {
                if ( !reset ) {
                    reset = result.getBoolean( "resetRoom" );
                }
            }
        } );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_room, container, false );
    }

    @Override
    public void onResume() {
        super.onResume();

        if ( reset ) {
            reset = false;
            ViewGroup vg = ( ViewGroup ) requireView();
            if ( vg instanceof ScrollView ) {
                vg = ( ViewGroup ) vg.getChildAt( 0 );
            }

            if ( vg.getChildAt( 0 ) != null ) {
                for ( int i = 0; i < vg.getChildCount(); ++i ) {
                    View v = vg.getChildAt( i );
                    if ( v instanceof EditText ) {
                        ( ( EditText ) v ).setText( "" );
                    } else if ( v instanceof RadioGroup ) {
                        ( ( RadioGroup ) v ).clearCheck();
                    }
                }
            }
        }
    }
}