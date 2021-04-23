package com.github.DarknightDragon.cluehelper;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

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

    private int notesHeight = -1;

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
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_home, container, false );
    }

    @Override
    public void onViewCreated( View view, @Nullable Bundle savedInstanceState ) {
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