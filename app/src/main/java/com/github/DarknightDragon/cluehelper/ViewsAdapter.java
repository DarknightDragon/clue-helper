package com.github.DarknightDragon.cluehelper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewsAdapter extends FragmentStateAdapter {
    public ViewsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch ( position ) {
            case 0:
                return new CharacterFragment();
            case 1:
                return new WeaponFragment();
            default:
                return new RoomFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
