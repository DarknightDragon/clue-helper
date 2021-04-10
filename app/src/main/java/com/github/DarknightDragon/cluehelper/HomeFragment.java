package com.github.DarknightDragon.cluehelper;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance( String param1, String param2 ) {
        HomeFragment fragment = new HomeFragment();
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
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_home, container, false );
    }

    @Override
    public void onViewCreated( View view, @Nullable Bundle savedInstanceState ) {
        Button btn_reset = view.findViewById( R.id.btn_reset );
        btn_reset.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                AlertDialog.Builder builder = new AlertDialog.Builder( Objects.requireNonNull( getActivity() ) );
                builder.setTitle( "Confirm?" );
                builder.setMessage( "Are you sure you want to reset everything?" );
                builder.setPositiveButton( "YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface dialog, int which ) {
                        reset( view );
                    }
                } );
                builder.setNegativeButton( "NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface dialog, int which ) {
                        dialog.dismiss();
                    }
                } );

                AlertDialog alert = builder.create();
                alert.show();
            }
        } );
    }

    private void reset( View view ) {
        ArrayList<Fragment> fragList = ViewsAdapter.fragList;
        int fragSize = fragList.size();
        if ( fragSize > 4 ) {
            for ( int i = 0; i < fragSize; ++i ) {
                Log.e( "FragList", "Frag #" + ( i + 1 ) + ": " + fragList.get( i ).getClass().toString() );
            }
            throw new IllegalStateException( "ERROR: Adapter created twice! There are " + fragSize + " fragments!" );
        }
        // check all fragment layouts
        for ( int i = 0; i < fragSize; ++i ) {
            ViewGroup vg = ( ViewGroup ) fragList.get( i ).getView();

            if ( vg.getClass() == ScrollView.class ) {
                vg = ( ViewGroup ) vg.getChildAt( 0 ); // this is a constraint layout
            }

            if ( vg != null && vg.getChildAt( i ) != null ) {
                for ( int j = 0; j < vg.getChildCount(); ++j ) {
                    if ( vg.getChildAt( j ).getClass() == AppCompatEditText.class || vg.getChildAt( j ).getClass() == EditText.class ) {
                        ( ( EditText ) vg.getChildAt( j ) ).setText( "" );
                    } else if ( vg.getChildAt( j ).getClass() == RadioGroup.class && ( ( RadioGroup ) vg.getChildAt( j ) ).getCheckedRadioButtonId() != -1 ) {
                        ( ( RadioGroup ) vg.getChildAt( j ) ).clearCheck();
                    }
                }
            } else {
                if ( vg == null ) {
                    Log.e( "ViewGroup", "VG is null!" );
                    throw new IllegalStateException( "ERROR: VG is null at position " + i + "!" );
                } else {
                    Log.e( "ViewGroup", "VG Child is null!" );
                    throw new IllegalStateException( "ERROR: VG child is null at position " + i + "!" );
                }
            }
        }
    }
}