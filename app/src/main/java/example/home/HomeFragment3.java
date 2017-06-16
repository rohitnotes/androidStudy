package example.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.style.base.BaseFragment;
import com.style.bean.Friend;
import com.style.bean.IMsg;
import com.style.bean.User;
import com.style.db.msg.MsgDBManager;
import com.style.db.user.UserDBManager;
import com.style.framework.R;
import com.style.manager.AccountManager;
import com.style.utils.MyDateUtil;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.OnClick;
import xj.mqtt.service.MQTTService;


public class HomeFragment3 extends BaseFragment {
    private User curUser;
    private List<Friend> friends;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLayoutResID = R.layout.fragment_home_3;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initData() {
        curUser = AccountManager.getInstance().getCurrentUser();

        friends = UserDBManager.getInstance().getAllFriend(curUser.getUserId());
    }

    @OnClick(R.id.view_start_listener_msg)
    public void view_start_listener_msg() {
        //startListener();
        MQTTService.publish("恍恍惚惚好");
    }

    @OnClick(R.id.view_end_listener_msg)
    public void view_end_listener_msg() {
        stopListener();
    }

    @OnClick(R.id.view_get_all_msg)
    public void view_get_all_msg() {
        MsgDBManager.getInstance().getAllMsg();
    }

    @OnClick(R.id.view_get_friend_msg)
    public void view_get_friend_msg() {
        MsgDBManager.getInstance().getMsg(curUser.getUserId(), 1);

    }

    protected Timer timer;
    private MyTimerTask task;
    public static final long DELAY_TIME = 5000;

    private void startListener() {
        task = new MyTimerTask();
        timer = new Timer(true);
        timer.schedule(task, 0, DELAY_TIME); //延时0ms后执行，1000ms执行一次
    }

    private void stopListener() {
        timer.cancel();
        task = null;
        timer = null;
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
           /* if (msg.what == UPDATE) {
                updateCallingTime();
            }*/
            //timer.purge();
        }
    };

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            /*Message message = new Message();
            message.what = UPDATE;
            handler.sendMessage(message);*/
            for (Friend f : friends) {
                IMsg o = new IMsg();
                o.setMsgId(System.currentTimeMillis());
                o.setSenderId(f.getFriendId());
                o.setReceiverId(f.getOwnerId());
                o.setState(0);
                o.setCreateTime(System.currentTimeMillis());
                //o.setContent(f.getMark() + ":" + MyDateUtil.longToString(System.currentTimeMillis(), MyDateUtil.FORMAT_yyyy_MM_dd_HH_mm_ss));
                MsgDBManager.getInstance().insertMsg(o);
            }
        }
    }
}