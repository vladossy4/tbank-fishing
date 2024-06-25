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

import com.salfetka.fishing.databinding.DialogFragmentLoginBinding;

public class LoginDialogFragment extends DialogFragment {
    private DialogFragmentLoginBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = DialogFragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.loginDialogButton.setOnClickListener( view -> {
            String username = binding.loginDialogUsername.getText().toString();
            String password = binding.loginDialogPassword.getText().toString();
            profileViewModel.login(username, password);
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
