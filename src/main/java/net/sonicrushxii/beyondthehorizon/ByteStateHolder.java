package net.sonicrushxii.beyondthehorizon;

import java.util.ArrayList;
import java.util.List;

public class ByteStateHolder
{
    List<Byte> byteArray;

    public ByteStateHolder() {
        this.byteArray = new ArrayList<>();
    }

    public ByteStateHolder(int capacity) {
        this.byteArray = new ArrayList<>(capacity);
    }

    public ByteStateHolder(List<Byte> byteArray) {
        this.byteArray = byteArray;
    }

    public ByteStateHolder(ByteStateHolder e) {
        this.byteArray = e.byteArray;
    }

    public ByteStateHolder(byte[] concurrentState) {
        this.byteArray = ByteStateHolder.toList(concurrentState);
    }

    public void setState(int pos)
    {
        int index = pos / 8;

        //Expand Array if it gets too long
        while (index >= byteArray.size()) {
            byteArray.add((byte) 0);
        }

        //Set appropriate bit
        byteArray.set(index, (byte) (byteArray.get(index) | (1 << pos % 8)));
    }

    public void clearState(int pos)
    {
        int index = pos / 8;

        //Expand Array if it gets too long
        while (index >= byteArray.size()) {
            byteArray.add((byte) 0);
        }

        //Clear appropriate bit
        byteArray.set(index, (byte) (byteArray.get(index) & ~(1 << pos % 8)));
    }

    public boolean getState(int pos)
    {
        int index = pos / 8;
        int bitPosition = pos % 8;

        // Expand Array if it gets too long
        while (index >= byteArray.size()) {
            byteArray.add((byte) 0);
        }

        // Get corresponding bit
        return (byteArray.get(index) & (1 << bitPosition)) != 0;
    }

    public List<Byte> getByteArray() {
        return byteArray;
    }

    private static byte[] toArray(List<Byte> dataList) {
        byte[] abyte = new byte[dataList.size()];

        for (int i = 0; i < dataList.size(); i++) {
            Byte obyte = dataList.get(i);
            abyte[i] = obyte == null ? 0 : obyte;
        }

        return abyte;
    }

    private static List<Byte> toList(byte[] abyte) {
        List<Byte> dataList = new ArrayList<>(abyte.length);

        for (byte b : abyte) {
            dataList.add(b);
        }

        return dataList;
    }
}
