package com.example.androiddownloadmanager;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,View.OnClickListener{

    Button btn;
    String[] fileurls;
    ListView listView;
    EditText eturl;
    DownloadManager downloadManager;
    long referenceId;
    IntentFilter filter;

    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)){
                if(intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1)==referenceId){
                    Toast.makeText(MainActivity.this, "Image Download Complete", Toast.LENGTH_LONG).show();
                }
            }else if(intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)){

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fileurls = getResources().getStringArray(R.array.fileurls);
        eturl = (EditText) findViewById(R.id.eturl);
        listView = (ListView) findViewById(R.id.listview);
        listView.setOnItemClickListener(this);
        btn = (Button) findViewById(R.id.btndownload);
        btn.setOnClickListener(this);
        filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        registerReceiver(downloadReceiver,filter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btndownload:
                downloadFile();
                break;
            default:
                break;
        }
    }

    private void downloadFile() {
        if(eturl.getText().toString().equals("")){
            Toast.makeText(this, "Please select a URL", Toast.LENGTH_SHORT).show();
            return;
        }
        download();
    }

    private void download() {
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(eturl.getText().toString()));
        request.setTitle("Data Download");
        request.setDescription("Data Download Using Android Download Manager.");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"abc.jpg");
        request.setVisibleInDownloadsUi(true);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        referenceId = downloadManager.enqueue(request);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        eturl.setText(fileurls[i]);
    }
}

















