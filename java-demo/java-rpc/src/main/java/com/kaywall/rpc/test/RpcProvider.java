package com.kaywall.rpc.test;

import com.kaywall.rpc.framwork.RpcFramework;

public class RpcProvider {

    public static void main(String[] args) throws Exception {
        HelloService service = new HelloServiceImpl();
        RpcFramework.export(service, 1234);
    }

}