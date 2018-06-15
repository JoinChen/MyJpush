package com.example.administrator.myjpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2018\6\6 0006.
 */

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())){
            String strId = bundle.getString(JPushInterface.ACTION_REGISTRATION_ID);
        }else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())){
            //收到了自定义消息消息内容bundle.getstring(JPushInterface.EXTRA_MESSAGE)
            //获得message的内容
            String title = bundle.getString(JPushInterface.EXTRA_TITLE);
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            //吐司自定义内容
            Toast.makeText(context, "message title"+title+"content:"+message, Toast.LENGTH_SHORT).show();
        }else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())){
            //收到了通知

        }else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())){
            //用户点击打开了通知
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            String content = bundle.getString(JPushInterface.EXTRA_ALERT);
            Intent intent1 = new Intent(context,MessageActivity.class);
            context.startActivity(intent1);

            Log.e("打开通知",title+content+"");
            //下面执行跳转操作

        }
    }
}
