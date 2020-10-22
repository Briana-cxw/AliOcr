package com.briana.aliocr;

import java.util.List;

/**
 * @description:
 * @date :2020/10/22 10:42
 */
public class HttpResult{
    private String request_id;
    private List<Bean> ret;
    private boolean success;

    public HttpResult() {
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "request_id='" + request_id + '\'' +
                ", ret=" + ret +
                ", success=" + success +
                '}';
    }

    public HttpResult(String request_id, List<Bean> ret, boolean success) {
        this.request_id = request_id;
        this.ret = ret;
        this.success = success;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public List<Bean> getRet() {
        return ret;
    }

    public void setRet(List<Bean> ret) {
        this.ret = ret;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

class Bean{
    private Rect rect;
    private String word;

    public Bean() {
    }

    public Bean(Rect rect, String word) {
        this.rect = rect;
        this.word = word;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "rect=" + rect +
                ", word='" + word + '\'' +
                '}';
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    class Rect{
        private float angle;
        private float height;
        private float left;
        private float top;
        private float width;

        public Rect() {
        }

        public Rect(float angle, float height, float left, float top, float width) {
            this.angle = angle;
            this.height = height;
            this.left = left;
            this.top = top;
            this.width = width;
        }

        @Override
        public String toString() {
            return "Rect{" +
                    "angle=" + angle +
                    ", height=" + height +
                    ", left=" + left +
                    ", top=" + top +
                    ", width=" + width +
                    '}';
        }

        public float getAngle() {
            return angle;
        }

        public void setAngle(float angle) {
            this.angle = angle;
        }

        public float getHeight() {
            return height;
        }

        public void setHeight(float height) {
            this.height = height;
        }

        public float getLeft() {
            return left;
        }

        public void setLeft(float left) {
            this.left = left;
        }

        public float getTop() {
            return top;
        }

        public void setTop(float top) {
            this.top = top;
        }

        public float getWidth() {
            return width;
        }

        public void setWidth(float width) {
            this.width = width;
        }
    }
}