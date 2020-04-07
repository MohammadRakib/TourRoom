package com.example.tourroom.ui.noti_fication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class notification_page_adapter extends FragmentStateAdapter {
    notification_page_adapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new general_notification_tab();
            case 1:
                return new invitation_notification_tab();

        }
        return new general_notification_tab();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
