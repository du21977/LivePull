package com.dibi.livepull.bean;

import java.util.List;

/**
 * Created by Admin on 2018/8/28.
 */

public class DefaultApiBean {


    /**
     * msg : 获取首页数据成功
     * data : [{"id":23,"path":"tudou.com","gid":1,"createTime":null,"styleType":1},{"id":26,"path":"aiqiyi.com","gid":1,"createTime":null,"styleType":1},{"id":27,"path":"youku.com","gid":1,"createTime":null,"styleType":1}]
     * code : 1
     */

    private String msg;
    private int code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 23
         * path : tudou.com
         * gid : 1
         * createTime : null
         * styleType : 1
         */

        private int id;
        private String path;
        private int gid;
        private Object createTime;
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

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
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
