package com.github.DarknightDragon.cluehelper;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.google.android.material.switchmaterial.SwitchMaterial;

// from https://stackoverflow.com/questions/33508841/onsharedpreferencechange-listener-in-fragment
public class HomeFragment extends Fragment {
    private int notesHeight = -1;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_home, container, false );
    }

    @Override
    public void onViewCreated( View view, @Nullable Bundle savedInstanceState ) {
        SwitchMaterial forceCheck = view.findViewById( R.id.force_checkmarks );

        // set onclick listener to save shared preference
        forceCheck.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences( requireContext() );
                pref.edit().putBoolean( "forceCheckOn", ( (SwitchMaterial) v).isChecked() ).apply();
            }
        } );

        // set switch to default shared preferences file
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences( requireContext() );
        if ( pref.getBoolean( "forceCheckOn", false ) ) {
            if ( !forceCheck.isChecked() ) {
                forceCheck.setChecked( true );
            }
        } else {
            if ( forceCheck.isChecked() ) {
                forceCheck.setChecked( false );
            }
        }

        Button btn_reset = view.findViewById( R.id.btn_reset );
        btn_reset.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                AlertDialog.Builder builder = new AlertDialog.Builder( requireActivity() );
                builder.setTitle( "Confirm?" );
                builder.setMessage( "Are you sure you want to reset everything?" );
                builder.setPositiveButton( "YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface dialog, int which ) {
                        // reset notes
                        ( (EditText) view.findViewById( R.id.notes ) ).setText( "" );

                        // reset Characters
                        Bundle resetChar = new Bundle();
                        resetChar.putBoolean( "resetChar", true );
                        getParentFragmentManager().setFragmentResult( "Character", resetChar );

                        // reset Weapons
                        Bundle resetWeap = new Bundle();
                        resetWeap.putBoolean( "resetWeap", true );
                        getParentFragmentManager().setFragmentResult( "Weapon", resetWeap );

                        // reset Rooms
                        Bundle resetRoom = new Bundle();
                        resetRoom.putBoolean( "resetRoom", true );
                        getParentFragmentManager().setFragmentResult( "Room", resetRoom );
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

        // allow edittext to scroll inside scrollview
        handleEditTextScrollable( view );

        // grow EditText to the full screen or default size
        view.getViewTreeObserver().addOnGlobalLayoutListener( new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                EditText notes = view.findViewById( R.id.notes );
                View spacer = view.findViewById( R.id.bottom_spacer_home );

                // from https://stackoverflow.com/a/31852757
                // instantiate DisplayMetrics
                DisplayMetrics dm = new DisplayMetrics();
                // fill dm with data from current display
                requireActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
                // notesLoc will hold the coordinates of notes
                int[] notesLoc = new int[2];
                // fill notesLoc with the coordinates of notes (notesLoc[0] = x, notesLoc[1] = y)
                notes.getLocationOnScreen( notesLoc );

                // set the notes height once
                if ( notesHeight == -1 ) {
                    notesHeight = notesLoc[1] + spacer.getMeasuredHeight() + 1;
                }

                // calculate the distance from the TOP(its y-coordinate) of your view to the bottom of the screen
                int distance = dm.heightPixels - notesHeight;

                if ( distance > 50 ) {
                    notes.setMaxHeight( distance );
                } else {
                    notes.setMaxHeight( notes.getMaxHeight() );
                }

                // from https://stackoverflow.com/a/26193736
                view.getViewTreeObserver().removeOnGlobalLayoutListener( this );
            }
        } );
    }

    // from https://stackoverflow.com/a/50345118
    // suppress lint to get rid of error on setontouchlistener not also overriding performclick
    @SuppressLint("ClickableViewAccessibility")
    // from https://stackoverflow.com/a/27520614
    private void handleEditTextScrollable( final View view ) {
        EditText notes = (EditText) view.findViewById( R.id.notes );

        notes.setOnTouchListener( new View.OnTouchListener() {
            @Override
            public boolean onTouch( View v, MotionEvent event ) {
                if ( v.getId() == R.id.notes ) {
                    ViewParent vp = v.getParent();
                    while ( !( vp instanceof ScrollView ) ) {
                        vp = vp.getParent();
                    }
                    vp.requestDisallowInterceptTouchEvent( true );
                    if ( ( event.getAction() & MotionEvent.ACTION_MASK ) == MotionEvent.ACTION_UP ) {
                        vp.requestDisallowInterceptTouchEvent( false );
                    }
                }
                return false;
            }
        } );
    }
}