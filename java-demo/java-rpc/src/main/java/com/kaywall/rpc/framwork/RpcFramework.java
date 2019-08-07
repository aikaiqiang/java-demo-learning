package com.kaywall.rpc.framwork;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

public class RpcFramework {

    /**
     * 暴露服务
     * @param service
     * @param port
     */
    public static void export(final Object service, int port) throws Exception{

        if (service == null){
            throw new IllegalArgumentException("service intance == null");
        }

        if(port <= 0 || port > 65535){
            throw new IllegalArgumentException("Invalid port " + port);
        }

        System.out.println("export service " + service.getClass().getName() + " on port " + port);
        ServerSocket serverSocket = new ServerSocket(port);
        for (;;){
            try {
                // 监听端口
                final Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                        String methodName = input.readUTF();
                        Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                        Object[] arguments = (Object[]) input.readObject();
                        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

                        // 反射
                        try {
                            Method method = service.getClass().getMethod(methodName, parameterTypes);
                            Object result = method.invoke(service, arguments);
                            output.writeObject(result);
                        } catch (Throwable t) {
                            output.writeObject(t);
                        } finally {
                            output.close();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            // 关闭 socket 异常
                            e.printStackTrace();
                        }
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 引用服务
     * @param interfaceClass
     * @param host
     * @param port
     * @param <T>
     * @return
     */
    public static <T> T refer(final Class<T> interfaceClass, final String host, int port){

        if (interfaceClass == null) {
            throw new IllegalArgumentException("Interface class is null");
        }

        if(!interfaceClass.isInterface()){
            throw new IllegalArgumentException("The " + interfaceClass.getName() +" must be interface class!");
        }

        if (host == null || host.length() == 0) {
            throw new IllegalArgumentException("host is null");
        }

        if(port <= 0 || port > 65535){
            throw new IllegalArgumentException("Invalid port " + port);
        }

        System.out.println("Get remote service " + interfaceClass.getName() + " from server " + host + ":" + port);

        // 动态代理生成对应 interface 的实例

        return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = new Socket(host, port);
                try {
                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                    try {
                        output.writeUTF(method.getName());
                        output.writeObject(method.getParameterTypes());
                        output.writeObject(args);

                        ObjectInputStream inuput = new ObjectInputStream(socket.getInputStream());
                        try {
                            Object result = inuput.readObject();
                            if (result instanceof Throwable) throw (Throwable) result;
                            return result;
                        } finally {
                            inuput.close();
                        }
                    } finally {
                        output.close();
                    }
                } finally {
                    socket.close();
                }
            }
        });
    }
}
