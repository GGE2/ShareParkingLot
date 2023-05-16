package com.example.notification.util;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Message {

    private String title;
    private String body;

    private static List<Message> TYPE = Arrays.asList(new Message("다른기기에서 접속","다른 기기에서 접속하여 로그아웃합니다")
    ,new Message("판매 완료", "주차권 판매가 완료되었습니다"),new Message("판매 확정", "판매자가 판매를 확정했습니다"),
            new Message("구매 확정", "구매자가 구매를 확정했습니다")
    );


    public static Message getMessage(int type){
        return TYPE.get(type);
    }

    public Message(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return this.title;
    }

    public String getBody() {
        return this.body;
    }


}
