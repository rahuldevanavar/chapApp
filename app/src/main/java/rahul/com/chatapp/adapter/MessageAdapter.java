package rahul.com.chatapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import rahul.com.chatapp.R;
import rahul.com.chatapp.common.CheckNetworkAvailability;
import rahul.com.chatapp.common.MessageItem;
import rahul.com.chatapp.io.ImageLoader;

/**
 * Created by rahul on 7/4/15.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<MessageItem> mMessageItems;

    public MessageAdapter(Context context, ArrayList<MessageItem> messageItems) {
        mMessageItems = messageItems;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.message_list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        viewHolder.message.setText(mMessageItems.get(i).getMessage());
        if (mMessageItems.get(i).getUserName().equalsIgnoreCase("Admin")) {
            viewHolder.userName.setTextColor(mContext.getResources().getColor(R.color.red));
        } else {
            viewHolder.userName.setTextColor(mContext.getResources().getColor(R.color.black));

        }
        viewHolder.time.setText(mMessageItems.get(i).getTime());
        viewHolder.userName.setText(mMessageItems.get(i).getUserName());
        if (CheckNetworkAvailability.isNetworkConnectionAvailable(mContext)) {
            new ImageLoader(new ImageLoader.setOnImageListener() {
                @Override
                public void onSuccess(Bitmap bitmap) {

                    viewHolder.userImage.setImageBitmap(bitmap);
                }
            }).execute(mMessageItems.get(i).getImageUrl());
        }
    }

    @Override
    public int getItemCount() {
        if (mMessageItems != null)
            return mMessageItems.size();
        else
            return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView userImage;
        private TextView userName;
        private TextView time;
        private TextView message;

        public ViewHolder(View itemView) {
            super(itemView);
            userImage = (ImageView) itemView.findViewById(R.id.user_image);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            time = (TextView) itemView.findViewById(R.id.sent_time);
            message = (TextView) itemView.findViewById(R.id.message);
        }
    }
}
