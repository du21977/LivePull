package com.dibi.livepull.bean;

import java.util.List;

/**
 * Created by Admin on 2018/8/31.
 */

public class AllUrlBean {


    /**
     * msg :
     * data : [[{"id":23,"path":"rtmp://live.hkstv.hk.lxdns.com/live/hks","gid":1,"createTime":"1535704359134","styleType":1},{"id":26,"path":"rtmp://202.69.69.180:443/webcast/bshdlive-pc","gid":1,"createTime":"1535704359136","styleType":1},{"id":27,"path":"rtmp://mobliestream.c3tv.com:554/live/goodtv.sdp","gid":1,"createTime":"1535704359138","styleType":1}],[{"id":25,"path":"acfun.com","gid":2,"createTime":null,"styleType":1}],[{"id":24,"path":"bilbil.com","gid":3,"createTime":null,"styleType":2}]]
     * code : 1
     */

    private String msg;
    private int code;
    private List<List<DataBean>> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<List<DataBean>> getData() {
        return data;
    }

    public void setData(List<List<DataBean>> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 23
         * path : rtmp://live.hkstv.hk.lxdns.com/live/hks
         * gid : 1
         * createTime : 1535704359134
         * styleType : 1
         */

        private int id;
        private String path;
        private int gid;
        private String createTime;
        private int styleType;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getStyleType() {
            return styleType;
        }

        public void setStyleType(int styleType) {
            this.styleType = styleType;
        }
    }
}
