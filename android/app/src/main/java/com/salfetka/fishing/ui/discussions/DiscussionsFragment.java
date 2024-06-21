package com.salfetka.fishing.ui.discussions;

import android.content.ClipboardManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.salfetka.fishing.R;
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
    private boolean isShowMenu = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DiscussionsViewModel discussionsViewModel = new ViewModelProvider(this).get(DiscussionsViewModel.class);

        binding = FragmentDiscussionsBinding.inflate(inflater, container, false);
        topDateCard = binding.chatTopDateCard;
        View root = binding.getRoot();

        final MenuHost menuHost = requireActivity();
        ChatAdapter chatAdapter = getChatAdapter(menuHost, discussionsViewModel);
        binding.chatView.setAdapter(chatAdapter);
        binding.chatView.scrollToPosition(chatAdapter.getItemCount()-1);
        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.message_selected_menu, menu);
            }

            @Override
            public void onPrepareMenu(@NonNull Menu menu){
                menu.findItem(R.id.chat_menu_copy).setVisible(isShowMenu);
                menu.findItem(R.id.chat_menu_deselect).setVisible(isShowMenu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.chat_menu_copy){
                    ClipboardManager clipboardManager = requireContext().getSystemService(ClipboardManager.class);
                    discussionsViewModel.copyMessages(chatAdapter.getSelectedMessages(), clipboardManager);
                    Snackbar.make(root, "Текст скопирован", Snackbar.LENGTH_SHORT).show();
                    chatAdapter.clearSelect();
                } else if (menuItem.getItemId() == R.id.chat_menu_deselect) {
                    chatAdapter.clearSelect();
                }
                return true;
            }
        }, getViewLifecycleOwner());

        // Кажется чат обновляется успешно. Если он будет обновляться при загрузке сообщений с сервера, то это можно удалить
//        final ObservableList.OnListChangedCallback<ObservableList<Message>> messageListener = new ObservableList.OnListChangedCallback<ObservableList<Message>>() {
//            @Override
//            public void onChanged(ObservableList<Message> sender) {
//
//            }
//
//            @Override
//            public void onItemRangeChanged(ObservableList<Message> sender, int positionStart, int itemCount) {
//                //chatAdapter.notifyItemRangeChanged(positionStart, itemCount);
//            }
//
//            @Override
//            public void onItemRangeInserted(ObservableList<Message> sender, int positionStart, int itemCount) {
//                //chatAdapter.notifyItemRangeInserted(positionStart, itemCount);
//            }
//
//            @Override
//            public void onItemRangeMoved(ObservableList<Message> sender, int fromPosition, int toPosition, int itemCount) {
//
//            }
//
//            @Override
//            public void onItemRangeRemoved(ObservableList<Message> sender, int positionStart, int itemCount) {
//                //chatAdapter.notifyItemRangeRemoved(positionStart, itemCount);
//            }
//        };
        discussionsViewModel.getMessages().observe(getViewLifecycleOwner(), messages -> {
            chatAdapter.updateAll(messages);
            binding.chatView.scrollToPosition(chatAdapter.getItemCount()-1);
//            messages.removeOnListChangedCallback(messageListener);
//            messages.addOnListChangedCallback(messageListener);
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
        binding.chatSendButton.setOnClickListener((view)-> {
            String message = binding.chatInput.getText().toString();
            if (!message.isEmpty()){
                discussionsViewModel.sendMessage(message);
                binding.chatInput.setText("");
                binding.chatView.scrollToPosition(chatAdapter.getItemCount()-1);
            }
        });
        return root;
    }

    private @NonNull ChatAdapter getChatAdapter(MenuHost menuHost, DiscussionsViewModel discussionsViewModel) {
        final ChatAdapter.OnMessageSelectChangedListener onMessageSelectChangedListener = (selectedItems) -> {
            if (selectedItems.isEmpty()){
                isShowMenu = false;
                menuHost.invalidateMenu();
            }
            else {
                if (!isShowMenu) {
                    isShowMenu = true;
                    menuHost.invalidateMenu();
                }
            }
        };
        return new ChatAdapter(getContext(), discussionsViewModel.getMessages().getValue(), onMessageSelectChangedListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}