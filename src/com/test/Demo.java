package com.test;
/* Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.*/

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.test.engine.MsgEngine;

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
 *   bofan     2016Äê4ÔÂ3ÈÕ - Creation
 *
 */
public class Demo
{
    public static void main(String[] args) throws IOException
    {
   
        @SuppressWarnings("resource")
        ServerSocket server=new ServerSocket(8885);
        Socket socket = server.accept();
        
        Thread thread = new Thread(new MsgEngine(socket));
        thread.start();
        System.out.println("hello");
        
        System.in.read();
    }
}
