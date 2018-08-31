package com.dibi.livepull.bean;

import java.util.List;

/**
 * Created by Admin on 2018/8/28.
 */

public class Test {


    private List<List<DataBean>> data;

    public List<List<DataBean>> getData() {
        return data;
    }

    public void setData(List<List<DataBean>> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 24
         * path : bilbil.com
         * gid : 3
         * createTime : null
         * styleType : 2
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
