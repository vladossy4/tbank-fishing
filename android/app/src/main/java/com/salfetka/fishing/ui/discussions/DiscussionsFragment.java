package com.salfetka.fishing.ui.discussions;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.salfetka.fishing.databinding.FragmentDiscussionsBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DiscussionsFragment extends Fragment {

    private FragmentDiscussionsBinding binding;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private MaterialCardView topDateCard;
    private final Runnable hideDateHint = () -> {
        topDateCard.setVisibility(View.GONE);
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DiscussionsViewModel discussionsViewModel =
                new ViewModelProvider(this).get(DiscussionsViewModel.class);

        binding = FragmentDiscussionsBinding.inflate(inflater, container, false);
        topDateCard = binding.chatTopDateCard;
        View root = binding.getRoot();

        ChatAdapter chatAdapter = new ChatAdapter(getContext(), discussionsViewModel.getMessages().getValue());
        binding.chatView.setAdapter(chatAdapter);
        binding.chatView.scrollToPosition(chatAdapter.getItemCount()-1);
        discussionsViewModel.getMessages().observe(getViewLifecycleOwner(), messages -> {
            chatAdapter.updateAll(messages);
            binding.chatView.scrollToPosition(chatAdapter.getItemCount()-1);
        });
        binding.chatView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && dy != 0) {
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    if (firstVisibleItemPosition != RecyclerView.NO_POSITION) {
                        Date date = chatAdapter.getItem(firstVisibleItemPosition).getDate().getTime();
                        SimpleDateFormat formatter = new SimpleDateFormat("d MMMM y", Locale.getDefault());
                        binding.chatTopMessageDate.setText(formatter.format(date));
                        topDateCard.setVisibility(View.VISIBLE);
                        handler.removeCallbacks(hideDateHint);
                        handler.postDelayed(hideDateHint, 2000);
                    } else {
                        topDateCard.setVisibility(View.GONE);
                    }
                }
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}