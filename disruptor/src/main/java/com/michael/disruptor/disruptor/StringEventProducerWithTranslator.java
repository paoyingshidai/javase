package com.michael.disruptor.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.michael.disruptor.support.StringEvent;

import java.nio.ByteBuffer;

public class StringEventProducerWithTranslator {

    //一个translator可以看做一个事件初始化器，publicEvent方法会调用它
    //填充Event
    public static final EventTranslatorOneArg<LongEvent, ByteBuffer> TRANSLATOR =
            (event, sequence, bb) -> event.setValue(bb.getLong(0));

}
