package com.ikkong.sunnylibapp.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunny on 2015-11-06.
 */
public class ResponseHotwxListEntity {

    private String reason;

    private ResultEntity result;
    private int error_code;

    public static ResponseHotwxListEntity objectFromData(String str) {

        return new Gson().fromJson(str, ResponseHotwxListEntity.class);
    }

    public static List<ResponseHotwxListEntity> arrayResponseHotwxListEntityFromData(String str) {

        Type listType = new TypeToken<ArrayList<ResponseHotwxListEntity>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultEntity getResult() {
        return result;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultEntity {
        private int totalPage;
        private int ps;
        private int pno;
        /**
         * id : wechat_20160421041492
         * title : 移动版Chrome浏览器月活跃用户突破10亿
         * source : 互联网头条
         * firstImg : 
         * mark : 
         * url : http://v.juhe.cn/weixin/redirect?wid=wechat_20160421041492
         */

        private List<ListEntity> list;

        public static ResultEntity objectFromData(String str) {

            return new Gson().fromJson(str, ResultEntity.class);
        }

        public static List<ResultEntity> arrayResultEntityFromData(String str) {

            Type listType = new TypeToken<ArrayList<ResultEntity>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getPs() {
            return ps;
        }

        public void setPs(int ps) {
            this.ps = ps;
        }

        public int getPno() {
            return pno;
        }

        public void setPno(int pno) {
            this.pno = pno;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public static class ListEntity {
            private String id;
            private String title;
            private String source;
            private String firstImg;
            private String mark;
            private String url;

            public static ListEntity objectFromData(String str) {

                return new Gson().fromJson(str, ListEntity.class);
            }

            public static List<ListEntity> arrayListEntityFromData(String str) {

                Type listType = new TypeToken<ArrayList<ListEntity>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getFirstImg() {
                return firstImg;
            }

            public void setFirstImg(String firstImg) {
                this.firstImg = firstImg;
            }

            public String getMark() {
                return mark;
            }

            public void setMark(String mark) {
                this.mark = mark;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
