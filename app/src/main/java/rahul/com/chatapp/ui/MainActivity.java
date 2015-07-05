package rahul.com.chatapp.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import rahul.com.chatapp.R;
import rahul.com.chatapp.adapter.MessageAdapter;
import rahul.com.chatapp.common.DataBaseHandler;
import rahul.com.chatapp.common.MessageItem;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<MessageItem> mMessageItemArrayList;
    private EditText mMessageEditText;
    private MessageAdapter mAdapter;
    private RecyclerView mMessageRecyclerView;
    private DataBaseHandler mDataBaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        mMessageRecyclerView = (RecyclerView) findViewById(R.id.message_list);
        mMessageEditText = (EditText) findViewById(R.id.message_edit_text);
        Button send = (Button) findViewById(R.id.send);
        send.setOnClickListener(this);
        setSupportActionBar(toolbar);
        setCustomActionBar();
        mDataBaseHandler = new DataBaseHandler(this);
        mMessageItemArrayList = mDataBaseHandler.getMessageList();
        if (mMessageItemArrayList != null && (!mMessageItemArrayList.isEmpty())) {
            setAdapter();
        } else {
            setWelcomeMessage();
            setAdapter();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        String message = mMessageEditText.getText().toString();
        if (message != null && (!message.isEmpty())) {
            MessageItem item = new MessageItem();
            Date d = new Date();
            item.setTime((String) DateFormat.format("h:mm A ", d.getTime()));
            item.setMessage(message);
            item.setImageUrl("https://slack-files.com/files-tmb/T043116HE-F060B6S94-e165eab1af/ic_launcher_360.png");
            item.setUserName("You");
            mMessageItemArrayList.add(item);
            mAdapter.notifyDataSetChanged();
            mDataBaseHandler.insertMessage(item);
            mMessageRecyclerView.getScrollState();
            mMessageEditText.setText("");
            mMessageRecyclerView.smoothScrollToPosition(mAdapter.getItemCount()-1);
            //   InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            // inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        } else {
            Toast.makeText(this, "Please enter text", Toast.LENGTH_SHORT).show();
        }
    }

    private void setAdapter() {
        mAdapter = new MessageAdapter(this, mMessageItemArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mMessageRecyclerView.setLayoutManager(layoutManager);
        mMessageRecyclerView.setAdapter(mAdapter);
    }

    private void setCustomActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        View view = LayoutInflater.from(this).inflate(R.layout.action_bar_title, null, false);
        actionBar.setCustomView(view);
    }


    private void setWelcomeMessage() {
        MessageItem messageItem = new MessageItem();
        messageItem.setImageUrl("https://slack-files.com/files-tmb/T043116HE-F060B6S94-e165eab1af/ic_launcher_360.png");
        Date d = new Date();
        String s = (String) DateFormat.format("h:mm a ", d.getTime());
        messageItem.setTime(s);
        messageItem.setMessage("Marketing orientated publication spins strongly marketing Repeated exposure," +
                "casting details ,based on thorough  analysis of data   ");
        messageItem.setTime(s);
        messageItem.setUserName("Admin");
        mMessageItemArrayList = new ArrayList<>();
        mDataBaseHandler.insertMessage(messageItem);
        mMessageItemArrayList.add(messageItem);
    }

}