package com.salfetka.fishing.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.salfetka.fishing.R;
import com.salfetka.fishing.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.profileUsername.setText(R.string.profile_login_guest);
        binding.loginBtn.setOnClickListener( view -> {
            LoginDialogFragment loginDialog = new LoginDialogFragment();
            loginDialog.show(requireActivity().getSupportFragmentManager(), "loginDialog");
        });
        binding.registerBtn.setOnClickListener( view -> {
            RegistrationDialogFragment registrationDialog = new RegistrationDialogFragment();
            registrationDialog.show(requireActivity().getSupportFragmentManager(), "registrationDialog");
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}