package com.salfetka.fishing.ui.discussions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.salfetka.fishing.R;
import com.salfetka.fishing.models.DateFormatter;
import com.salfetka.fishing.models.discussions.Message;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

/** Связывает сообщения в чате с их представлением */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    interface OnMessageSelectChangedListener {
        void OnMessageSelectChanged(HashSet<Integer> selectedMessages);
    }

    private final OnMessageSelectChangedListener onMessageSelectChangedListener;
    private final LayoutInflater inflater;
    private List<Message> messages;
    private final HashSet<Integer> selectedMessages = new HashSet<>();

    public ChatAdapter(Context context, List<Message> messages, OnMessageSelectChangedListener onMessageSelectChangedListener) {
        this.onMessageSelectChangedListener = onMessageSelectChangedListener;
        this.inflater = LayoutInflater.from(context);
        if (messages != null) {
            this.messages = messages;
        }
        else this.messages = new ArrayList<>();
        setHasStableIds(true);
    }

    public Message getItem(int index) {
        return messages.get(index);
    }

    public void updateAll(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    public HashSet<Integer> getSelectedMessages() {
        return selectedMessages;
    }

    public void clearSelect() {
        HashSet<Integer> copy = new HashSet<>(selectedMessages);
        selectedMessages.clear();
        copy.forEach(this::notifyItemChanged);
        onMessageSelectChangedListener.OnMessageSelectChanged(selectedMessages);
    }

    @Override
    public long getItemId(int position) {
        return messages.get(position).getId();
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_chat, parent, false);
        return new ChatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.userName.setText(message.getUserName());
        holder.message.setText(message.getMessage());
        Calendar messageDate = message.getDate();
        String pattern = "k:mm";
        if (position > 0 ) {
            Calendar lastMessageDate = messages.get(position-1).getDate();
            if (messageDate.get(Calendar.YEAR) != lastMessageDate.get(Calendar.YEAR)) {
                pattern = "d MMMM y, k:mm";
            } else if (messageDate.get(Calendar.MONTH) != lastMessageDate.get(Calendar.MONTH) ||
                       messageDate.get(Calendar.DAY_OF_MONTH) != lastMessageDate.get(Calendar.DAY_OF_MONTH)){
                pattern = "d MMMM, k:mm";
            }
        }
        else {
            pattern = "d MMMM y, k:mm";
        }
        holder.sendTime.setText(DateFormatter.format(messageDate, pattern));
        holder.messageCard.setOnLongClickListener((view)->{
            if (holder.messageCard.isChecked()){
                selectedMessages.remove(position);
            }
            else {
                selectedMessages.add(position);
            }
            notifyItemChanged(position);
            onMessageSelectChangedListener.OnMessageSelectChanged(selectedMessages);
            return true;
        });
        holder.messageCard.setChecked(selectedMessages.contains(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final MaterialCardView messageCard;
        final TextView userName, message, sendTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageCard = itemView.findViewById(R.id.chat_message_card);
            userName = itemView.findViewById(R.id.chat_item_user_name);
            message = itemView.findViewById(R.id.chat_item_message);
            sendTime = itemView.findViewById(R.id.chat_item_send_time);
        }
    }
}
