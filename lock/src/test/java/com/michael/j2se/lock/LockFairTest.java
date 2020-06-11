package com.michael.j2se.lock;

import org.junit.Test;

public class LockFairTest {

    @Test
    public void testPrintUsingCondition() {

        LockFair lockFair = new LockFair();
        lockFair.printUsingCondition();

    }

    @Test
    public void testPrintUsingCondition2() {

        LockFair lockFair = new LockFair();
        lockFair.printUsingCondition2();

    }

    @Test
    public void testPrint() {

        LockFair lockFair = new LockFair();
        lockFair.print();

    }

}