package com.salfetka.fishing.ui.discussions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.salfetka.fishing.R;
import com.salfetka.fishing.models.discussions.Message;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private List<Message> messages;

    public ChatAdapter(Context context, List<Message> messages) {
        this.inflater = LayoutInflater.from(context);
        this.messages = messages;
    }

    public Message getItem(int index) {
        return messages.get(index);
    }

    public void updateAll(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
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
        SimpleDateFormat formatter = new SimpleDateFormat("k:mm", Locale.getDefault());
        holder.sendTime.setText(formatter.format(message.getDate().getTime()));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView userName, message, sendTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.chat_item_user_name);
            message = itemView.findViewById(R.id.chat_item_message);
            sendTime = itemView.findViewById(R.id.chat_item_send_time);
        }
    }
}
