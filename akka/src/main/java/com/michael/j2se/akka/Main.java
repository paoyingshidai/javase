package com.michael.j2se.akka;


/**
 * https://blog.csdn.net/zhuchuangang/article/details/52116248
 */
public class Main {

    public static void main(String[] args) {

//        ActorSystem system = ActorSystem.create("Main");

        akka.Main.main(new String[]{HelloWorld.class.getName()});
    }


}
