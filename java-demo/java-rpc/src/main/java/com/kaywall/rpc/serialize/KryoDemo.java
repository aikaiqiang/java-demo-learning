package com.kaywall.rpc.serialize;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class KryoDemo {


    public static void main(String[] args) throws FileNotFoundException {

        Kryo kryo = new Kryo();
        kryo.register(SomeClass.class);

        SomeClass object = new SomeClass();
        object.name = "Hello Kryo!";

        Output output = new Output(new FileOutputStream("file.bin"));
        kryo.writeObject(output, object);
        output.close();

        Input input = new Input(new FileInputStream("file.bin"));
        SomeClass object2 = kryo.readObject(input, SomeClass.class);
        input.close();
        assert "Hello Kryo!".equals(object2.name);

    }

    public static class SomeClass{
        private String name;
    }


}
