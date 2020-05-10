package com.michael.disruptor.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

public class LongEventProducer {

    private final RingBuffer ringBuffer;

    EventTranslatorOneArg translatorOneArg = StringEventProducerWithTranslator.TRANSLATOR;

    public LongEventProducer(RingBuffer ringBuffer) {
        this.ringBuffer = ringBuffer;
    }


    /**
     * 使用消息转换器
     * @param byteBuffer
     */
    public void onDataTranslate(ByteBuffer byteBuffer) {
        ringBuffer.publishEvent(translatorOneArg, byteBuffer);
    }


    /**
     * onData用来发布事件，每调用一次就发布一次事件事件
     * 它的参数会通过事件传递给消费者
     *
     * @param bb
     */
    public void onData(ByteBuffer bb) {
        //可以把ringBuffer看做一个事件队列，那么next就是得到下面一个事件槽
        long sequence = ringBuffer.next();

        try {
            //用上面的索引取出一个空的事件用于填充
            LongEvent event = (LongEvent) ringBuffer.get(sequence);
            event.setValue(bb.getLong(0));
        } finally {
            //发布事件
            ringBuffer.publish(sequence);
        }
    }
}
