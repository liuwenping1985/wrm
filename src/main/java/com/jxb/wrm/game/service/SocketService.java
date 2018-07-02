package com.jxb.wrm.game.service;

import com.jxb.wrm.game.constant.EnumSocketStatus;
import com.jxb.wrm.game.context.GameContext;
import com.jxb.wrm.game.event.GamingEvent;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;

/**
 * Created by liuwenping on 2018/6/22.
 */
@Component
public class SocketService {

    public static final int START_PORT = 12208;

    public static final String SERVER_IP = "";

    private static  int CUR_PORT = START_PORT;




    public SocketInfo createSocket(GameContext context){

        SocketInfo info = new SocketInfo();
        info.setStatus(EnumSocketStatus.RUNNING);
        info.setPort(CUR_PORT);
        CUR_PORT = CUR_PORT+1;

        return info;
    }

    //发送数据, json 格式
    public static  void send(String obj,Socket socket) throws Exception{
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                socket.getOutputStream(), "UTF-8"));
        writer.append(obj);
        writer.newLine();
        writer.flush();
    }
    //接受数据
    public static String accept(Socket socket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
        String line = reader.readLine();
        return line;
    }


    public static void notifyCustomThroughEvent(GamingEvent event){



    }




}
