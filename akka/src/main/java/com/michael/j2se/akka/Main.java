package com.michael.j2se.akka;


import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class Main {

    public static void main(String[] args) {

//        ActorSystem system = ActorSystem.create("Main");

        akka.Main.main(new String[]{HelloWorld.class.getName()});
    }


}
