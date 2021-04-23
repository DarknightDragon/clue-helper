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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoomFragment extends Fragment {
    // flag for reset button pressed
    private volatile boolean reset = false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RoomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RoomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RoomFragment newInstance( String param1, String param2 ) {
        RoomFragment fragment = new RoomFragment();
        Bundle args = new Bundle();
        args.putString( ARG_PARAM1, param1 );
        args.putString( ARG_PARAM2, param2 );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        if ( getArguments() != null ) {
            mParam1 = getArguments().getString( ARG_PARAM1 );
            mParam2 = getArguments().getString( ARG_PARAM2 );
        }

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