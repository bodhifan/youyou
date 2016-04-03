package com.test;
/* Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.*/

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.server.manager.ServerContext;
import com.test.engine.MsgEngine;

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
 *   bofan     2016  - Creation
 *
 */
public class TestRegister extends UiAutomatorTestCase
{

    public void testRegister() throws UiObjectNotFoundException, IOException, InterruptedException
    {
        
        ServerContext context = new ServerContext(42222);
        context.BeginServer();
        
        context.getWrittingMsgQueue().add("你好，这是服务器给你发送的消息");
        
        String msg = context.getReceivedMsgQueue().fetch();
        
        System.out.println("我接收到客户端传来的消息：" + msg);
        
        System.in.read();

    }
}
