package com.ikkong.sunnylibapp.entity;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:  ikkong
 * Email:   ikkong@163.com
 * Date:    2016/4/25
 * Description:
 */
public class ResponseJoke {

    /**
     * error_code : 0
     * reason : Success
     * result : {"data":[{"content":"少爷，老爷说了不让你出去","hashId":"D71266BFD3E073ACBF2F46FD28F1E7F1","unixtime":1461335592,"updatetime":"2016-04-22 22:33:12","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201604/22/D71266BFD3E073ACBF2F46FD28F1E7F1.gif"},{"content":"都流行这样的发型了","hashId":"117B4C73C64BFE8916EAF8DB8CA59DC0","unixtime":1461335592,"updatetime":"2016-04-22 22:33:12","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201604/22/117B4C73C64BFE8916EAF8DB8CA59DC0.jpg"},{"content":"小样你还穿着迷彩来的啊","hashId":"EE165A0926CC86A2756BEBE21B6F96FE","unixtime":1461335592,"updatetime":"2016-04-22 22:33:12","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201604/22/EE165A0926CC86A2756BEBE21B6F96FE.jpg"},{"content":"父母的DNA都太强了","hashId":"BAFE9593D143028758F13ED96C9945EF","unixtime":1461335592,"updatetime":"2016-04-22 22:33:12","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201604/22/BAFE9593D143028758F13ED96C9945EF.jpg"},{"content":"制服诱惑么","hashId":"EBFE9054EFE07FF1EDBADE149EE9063C","unixtime":1461335592,"updatetime":"2016-04-22 22:33:12","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201604/22/EBFE9054EFE07FF1EDBADE149EE9063C.jpg"},{"content":"霸气侧漏的行李箱","hashId":"20F2DA08777DC908CAEA07315DDAC39D","unixtime":1461335592,"updatetime":"2016-04-22 22:33:12","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201604/22/20F2DA08777DC908CAEA07315DDAC39D.jpg"},{"content":"智障朋友，走路是这样的","hashId":"6E30F08B6B210540BEB190EB67C5F185","unixtime":1461335592,"updatetime":"2016-04-22 22:33:12","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201604/22/6E30F08B6B210540BEB190EB67C5F185.gif"},{"content":"歪果仁就是会玩","hashId":"CC008C6414033641FADA5F7AA6DA6963","unixtime":1461335592,"updatetime":"2016-04-22 22:33:12","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201604/22/CC008C6414033641FADA5F7AA6DA6963.gif"},{"content":"你有这样机智的老爸吗","hashId":"77608BF8C1693E62051C7B9742ED44EA","unixtime":1460107996,"updatetime":"2016-04-08 17:33:16","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201604/08/77608BF8C1693E62051C7B9742ED44EA.jpg"},{"content":"这么高级的麻将，玩过吗","hashId":"E79154642ADF62685C5A5A27853EF880","unixtime":1460107996,"updatetime":"2016-04-08 17:33:16","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201604/08/E79154642ADF62685C5A5A27853EF880.jpg"}]}
     */

    private int error_code;
    private String reason;
    private ResultEntity result;

    public static ResponseJoke objectFromData(String str) {

        try {
            return new Gson().fromJson(str, ResponseJoke.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<ResponseJoke> arrayResponseJokeFromData(String str) {

        Type listType = new TypeToken<ArrayList<ResponseJoke>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
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

    public static class ResultEntity {
        /**
         * content : 少爷，老爷说了不让你出去
         * hashId : D71266BFD3E073ACBF2F46FD28F1E7F1
         * unixtime : 1461335592
         * updatetime : 2016-04-22 22:33:12
         * url : http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201604/22/D71266BFD3E073ACBF2F46FD28F1E7F1.gif
         */

        private List<DataEntity> data;

        public static ResultEntity objectFromData(String str) {

            return new Gson().fromJson(str, ResultEntity.class);
        }

        public static List<ResultEntity> arrayResultEntityFromData(String str) {

            Type listType = new TypeToken<ArrayList<ResultEntity>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public List<DataEntity> getData() {
            return data;
        }

        public void setData(List<DataEntity> data) {
            this.data = data;
        }

        public static class DataEntity {
            private String content;
            private String hashId;
            private int unixtime;
            private String updatetime;
            private String url;

            public static DataEntity objectFromData(String str) {

                return new Gson().fromJson(str, DataEntity.class);
            }

            public static List<DataEntity> arrayDataEntityFromData(String str) {

                Type listType = new TypeToken<ArrayList<DataEntity>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getHashId() {
                return hashId;
            }

            public void setHashId(String hashId) {
                this.hashId = hashId;
            }

            public int getUnixtime() {
                return unixtime;
            }

            public void setUnixtime(int unixtime) {
                this.unixtime = unixtime;
            }

            public String getUpdatetime() {
                return updatetime;
            }

            public void setUpdatetime(String updatetime) {
                this.updatetime = updatetime;
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
