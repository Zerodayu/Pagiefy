package com.example.pagiefy;

import android.os.Bundle;

import android.view.View;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.pagiefy.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Make the status bar translucent
            getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

            // Add padding to the content so it doesn't go under the status bar
            View contentView = findViewById(android.R.id.content);
            contentView.setPadding(0, getStatusBarHeight(), 0, 0);
        }

        replaceFragment(new LibraryFragment());

        binding.BottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.Saves) {
                replaceFragment(new LibraryFragment());
            } else if (itemId == R.id.Add) {
                replaceFragment(new AddFragment());
            } else if (itemId == R.id.Extensions) {
                replaceFragment(new ExtensionFragment());
            } else if (itemId == R.id.Settings) {
                replaceFragment(new AboutFragment());
            }

            return true;
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.FrameLayout, fragment);

        if (!(fragment instanceof LibraryFragment ||
                fragment instanceof AddFragment ||
                fragment instanceof ExtensionFragment ||
                fragment instanceof AboutFragment)) {
            transaction.addToBackStack(null);
        }
        transaction.commit();

        updateBottomNavVisibility(fragment);
    }

    private void updateBottomNavVisibility(Fragment fragment) {
        if (fragment instanceof LibraryFragment ||
                fragment instanceof AddFragment ||
                fragment instanceof ExtensionFragment ||
                fragment instanceof AboutFragment) {
            binding.BottomNav.setVisibility(View.VISIBLE);
        } else {
            binding.BottomNav.setVisibility(View.GONE);
        }

    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}