package com.server.core;
/* Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.*/

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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
public class WriteEngine implements Runnable
{
    private BlockingMsgQueue writtingMsgQueue;
    private Thread thread;
    private Socket socket;
    
    public WriteEngine(Socket socket,BlockingMsgQueue queue)
    {
        writtingMsgQueue = queue;
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
        
        DataOutputStream dos = null;
        try
        {
            dos = new DataOutputStream(socket.getOutputStream());
            
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        while(true)
        {
            try
            {
                String msg = writtingMsgQueue.fetch();
                
                System.out.println(String.format("write msg [%s] from server",msg));
                // 加入length标示
                msg = String.format("length:%05d%s", msg.length(),msg);
                
                // 转换为utf-8的标示符号
                byte[] msgBytes = msg.getBytes("utf-8");
                
                dos.writeBytes(new String(msgBytes));
                dos.flush();
            }
            catch (IOException | InterruptedException exception)
            {
                System.out.println( exception.getMessage());
            }
        }
        
    }

}
