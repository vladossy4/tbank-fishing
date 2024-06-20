package com.salfetka.fishing.ui.discussions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.salfetka.fishing.R;
import com.salfetka.fishing.models.DateFormatter;
import com.salfetka.fishing.models.discussions.Message;

import java.util.Calendar;
import java.util.List;

/** Связывает сообщения в чате с их представлением */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private List<Message> messages;

    public ChatAdapter(Context context, List<Message> messages) {
        this.inflater = LayoutInflater.from(context);
        this.messages = messages;
        setHasStableIds(true);
    }

    public Message getItem(int index) {
        return messages.get(index);
    }

    public void updateAll(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
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
