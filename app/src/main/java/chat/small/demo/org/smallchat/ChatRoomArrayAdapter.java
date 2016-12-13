package chat.small.demo.org.smallchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ChatRoomArrayAdapter extends ArrayAdapter<ChatRoomMessage> {

    private static final int BOT_MESSAGE = -1;
    private static final int USER_MESSAGE = 1;

    private TextView messageTextView;
    private List<ChatRoomMessage> chatMessages = new ArrayList<ChatRoomMessage>();
    private LinearLayout wrapper;

    @Override
    public void add(ChatRoomMessage object) {
        chatMessages.add(object);
        super.add(object);
    }

    public ChatRoomArrayAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
    }

    public int getCount() {
        return this.chatMessages.size();
    }

    public ChatRoomMessage getItem(int index) {
        return this.chatMessages.get(index);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return (chatMessages.get(position).bot ? BOT_MESSAGE : USER_MESSAGE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ChatRoomMessage comment = getItem(position);

        int type = getItemViewType(position);

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if(type== BOT_MESSAGE){
                row = inflater.inflate(R.layout.chat_listitem_bot, parent, false);
            }
            if(type== USER_MESSAGE){
                row = inflater.inflate(R.layout.chat_listitem_user, parent, false);
            }
        }

        wrapper = (LinearLayout) row.findViewById(R.id.wrapper);

        messageTextView = (TextView) row.findViewById(R.id.text);
        messageTextView.setText(comment.text);

        return row;
    }

}