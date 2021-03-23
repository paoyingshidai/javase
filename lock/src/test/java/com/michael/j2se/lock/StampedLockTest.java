package com.michael.j2se.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.StampedLock;

/**
 * @author Michael
 */
public class StampedLockTest {


    @Test
    void stampedLockTest() {

        StampedLock lock = new StampedLock();

        lock.tryConvertToReadLock(0);

    }

}
