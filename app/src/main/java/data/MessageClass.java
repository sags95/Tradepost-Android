package data;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by sharathsind on 2015-06-06.
 */
public class MessageClass {
    String msg;
    Date sent;

    int userid;
    int ruserid;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getSent() {
        return sent;
    }

    public void setSent(Date sent) {
        this.sent = sent;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Date isSeen() {
        return seen;
    }

    public void setSeen(Date seen) {
        this.seen = seen;

    }

    public Bitmap getPicmsg()
    {
        return picmsg;
    }

    public void setPicmsg(Bitmap picmsg) {

        this.picmsg = picmsg;
    }
    Date seen;

    Bitmap picmsg;
    public MessageClass()
    {}

    public MessageClass(String msg, Bitmap picmsg,int userid) {
        this.msg = msg;
        this.picmsg = picmsg;
        this.userid=userid;
    }

}
