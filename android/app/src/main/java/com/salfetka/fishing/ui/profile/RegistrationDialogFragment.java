package com.salfetka.fishing.ui.profile;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.salfetka.fishing.databinding.DialogFragmentRegistrationBinding;

public class RegistrationDialogFragment extends DialogFragment {
    private DialogFragmentRegistrationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = DialogFragmentRegistrationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.registrationDialogButton.setOnClickListener( view -> {
            String username = binding.registrationDialogUsername.getText().toString();
            String email = binding.registrationDialogEmail.getText().toString();
            String password = binding.registrationDialogPassword.getText().toString();
            profileViewModel.registration(username, email, password);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(onCreateView(LayoutInflater.from(getActivity()), null, savedInstanceState));
        return builder.create();
    }

}
