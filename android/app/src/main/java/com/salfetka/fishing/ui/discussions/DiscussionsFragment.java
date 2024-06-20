package com.salfetka.fishing.ui.discussions;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.salfetka.fishing.databinding.FragmentDiscussionsBinding;
import com.salfetka.fishing.models.DateFormatter;
import com.salfetka.fishing.models.discussions.Message;

import java.util.Calendar;

/** Подложка интерфейса: отображение данных о переписках и обработка событий */
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
        final ObservableList.OnListChangedCallback<ObservableList<Message>> messageListener = new ObservableList.OnListChangedCallback<ObservableList<Message>>() {
            @Override
            public void onChanged(ObservableList<Message> sender) {

            }

            @Override
            public void onItemRangeChanged(ObservableList<Message> sender, int positionStart, int itemCount) {
                chatAdapter.notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList<Message> sender, int positionStart, int itemCount) {
                chatAdapter.notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList<Message> sender, int fromPosition, int toPosition, int itemCount) {

            }

            @Override
            public void onItemRangeRemoved(ObservableList<Message> sender, int positionStart, int itemCount) {
                chatAdapter.notifyItemRangeRemoved(positionStart, itemCount);
            }
        };
        discussionsViewModel.getMessages().observe(getViewLifecycleOwner(), messages -> {
            chatAdapter.updateAll(messages);
            binding.chatView.scrollToPosition(chatAdapter.getItemCount()-1);
            messages.removeOnListChangedCallback(messageListener);
            messages.addOnListChangedCallback(messageListener);
        });
        binding.chatView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && dy != 0) {
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    if (firstVisibleItemPosition != RecyclerView.NO_POSITION) {
                        Calendar date = chatAdapter.getItem(firstVisibleItemPosition).getDate();
                        binding.chatTopMessageDate.setText(DateFormatter.format(date, "d MMMM y"));
                        topDateCard.setVisibility(View.VISIBLE);
                        handler.removeCallbacks(hideDateHint);
                        handler.postDelayed(hideDateHint, 2000);
                    } else {
                        topDateCard.setVisibility(View.GONE);
                    }
                }
            }
        });
        binding.chatSendButton.setOnClickListener((view)->{
            String message = binding.chatInput.getText().toString();
            if (!message.isEmpty()){
                discussionsViewModel.sendMessage(message);
                binding.chatInput.setText("");
                binding.chatView.scrollToPosition(chatAdapter.getItemCount()-1);
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