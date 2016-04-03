package com.server.core;
/* Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.*/

import java.awt.TexturePaint;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import android.R.string;

/*
 * DESCRIPTION
 *     TODO
 *
 * PRIVATE CLASSES
 *     NONE
 *
 * NOTES
 *    <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *   bofan     2016年4月4日 - Creation
 *
 */
public class ReadEngine implements Runnable
{
    private BlockingMsgQueue receivedMsgQueue;
    private Thread thread;
    private Socket socket;
    public ReadEngine(Socket socket,BlockingMsgQueue queue)
    {
        receivedMsgQueue = queue;
        this.socket = socket;
        
        thread = new Thread(this);
        thread.start();
    }
    
    @Override
    public void run()
    {
        StringBuilder sb = new StringBuilder();
        byte[] buffer = new byte[1024];
        int bufNumber = 0;
        
        DataInputStream dis = null;
        try
        {
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        while(true)
        {
            try
            {
                bufNumber = dis.read(buffer, 0, 1024);
                if (bufNumber == 0)
                {
                    continue;
                }
                String msg = new String(buffer, "utf-8");
                msg = msg.trim();
                System.out.println(String.format("read [%s] from client",msg));
                sb.append(msg);
                
                String currentMsg = sb.toString();
                
                // 12为 length:00000 的字符长度，用于标示消息体的长度
                int FLAG_LENGTH = 12;
                while(currentMsg.length() > FLAG_LENGTH)
                {
                    // 获取标示位表示的消息体长度
                    String flag = currentMsg.substring(0, FLAG_LENGTH);
                    flag = flag.substring(7);
                    flag = flag.replaceFirst("^0*", "");
                    Integer len = Integer.parseInt(flag);
                    
                    // 截取标示位后面的字符
                    currentMsg = currentMsg.substring(FLAG_LENGTH);
                    
                    // 截取标示位中指定长度的字符
                    String fragmentMsg = currentMsg.substring(0,len);
                    receivedMsgQueue.add(fragmentMsg);
                    
                    // 移除len长度的字符
                    currentMsg = currentMsg.substring(len);
                }
                
                sb = new StringBuilder(currentMsg);
            }
            catch (IOException exception)
            {
                
            }
        }
        
    }

    
}
