package com.github.DarknightDragon.cluehelper;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CharacterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CharacterFragment extends Fragment {
    // flag for reset button pressed
    private volatile boolean reset = false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CharacterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CharacterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CharacterFragment newInstance( String param1, String param2 ) {
        CharacterFragment fragment = new CharacterFragment();
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
        getParentFragmentManager().setFragmentResultListener( "Character", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult( @NonNull String requestKey, @NonNull Bundle result ) {
                if ( !reset ) {
                    reset = result.getBoolean( "resetChar" );
                }
            }
        } );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_character, container, false );
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated( @NonNull View view, @Nullable Bundle savedInstanceState ) {
        // view is ScrollView
        ViewGroup radGroups = (ViewGroup) ( (ViewGroup) view ).getChildAt( 0 );

        for ( int i = 0; i < radGroups.getChildCount(); ++i ) {
            if ( radGroups.getChildAt( i ) instanceof RadioGroup ) {
                RadioButton radBtn = ( RadioButton ) ( (RadioGroup) radGroups.getChildAt( i ) ).getChildAt( 2 );
                radBtn.setOnTouchListener( new View.OnTouchListener() {
                    @Override
                    public boolean onTouch( View v, MotionEvent event ) {
                        // TODO: Add if statement to see if sharedPreferences forceCheckmark toggle is on
                        if ( event.getActionMasked() == MotionEvent.ACTION_UP && true ) {
                            AlertDialog.Builder builder = new AlertDialog.Builder( requireActivity() );
                            builder.setTitle( "Confirm?" );
                            builder.setMessage( "Are you sure you want to continue? All other options on this page will be set to X" );
                            builder.setPositiveButton( "YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick( DialogInterface dialog, int which ) {
                                    ( (RadioButton) v).toggle();

                                    // this gives ConstraintLayout
                                    ViewGroup vg = ( ViewGroup ) v.getParent().getParent();

                                    for ( int j = 0; j < vg.getChildCount(); ++j ) {
                                        View radGroup = vg.getChildAt( j );
                                        // check if group is a radio group and make sure it is not the same as the one going to be checked
                                        if ( radGroup instanceof RadioGroup && ( ( ViewGroup ) v.getParent() ).getId() != radGroup.getId() ) {
                                            RadioGroup rg = (RadioGroup) radGroup;
                                            rg.check( rg.getChildAt( 0 ).getId() );
                                        }
                                    }
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

                        // needed to say that the event was handled in this override and not passed through the hierarchy
                        return true;
                    }
                } );
            }
        }
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