package CommandToByteArray;

import java.nio.ByteBuffer;

/**
 * Created by idalov on 18.05.16.
 * Commands classes v0.01
 */

/**Чтение состояния стола (1)
 * Остановить всё (5)
 */
class C00Command {
    private byte Id;
    private int mark;
    private int size;
    C00Command(byte Id, int mark, int size){
        this.Id=Id;
        this.mark = mark;
        this.size = size;
    }

    byte [] getByteArray(){
        byte [] array = new byte[9];
        int i = 0;
        array[i++]=Id;
        byte [] bufarray = new byte[4];
        for(byte b : ByteBuffer.wrap(bufarray).putInt(mark).array())
            array[i++]=b;
        for(byte b : ByteBuffer.wrap(bufarray).putInt(size).array())
            array[i++]=b;
        return array;
    }

    byte getId(){
        return Id;
    }
}

//Запуск с постоянной скоростью по индексу (2)
class C00CommandSI extends C00Command{
    private byte DriveNum;
    private byte SpeedIndex;
    C00CommandSI(byte Id, int mark, int size, byte DriveNum, byte SpeedIndex){
        super(Id,mark,size);
        this.DriveNum = DriveNum;
        this.SpeedIndex = SpeedIndex;
    }

    @Override
    byte[] getByteArray() {
        int i = 0;
        byte [] array = new byte[11];
        for(byte b : super.getByteArray())
            array[i++] = b;
        array[i++]=DriveNum;
        array[i]=SpeedIndex;
        return array;
    }
}

//Запуск с заданной скоростью (3)
class C00CommandSV extends C00Command{
    private byte DriveNum;
    private int SpeedValue;
    C00CommandSV(byte Id, int mark, int size, byte DriveNum, int SpeedValue){
        super(Id,mark,size);
        this.DriveNum = DriveNum;
        this.SpeedValue = SpeedValue;
    }

    @Override
    byte[] getByteArray() {
        int i = 0;
        byte [] array = new byte[11];
        for(byte b : super.getByteArray())
            array[i++] = b;
        array[i++]=DriveNum;
        byte [] bufarray = new byte[4];
        for(byte b : ByteBuffer.wrap(bufarray).putInt(SpeedValue).array())
            array[i++]=b;
        return array;
    }
}

//Движение к заданной позиции (4)
class C00CommandDPS extends C00Command{
    private byte DriveNum;
    private float Position;
    private float Skew;
    C00CommandDPS(byte Id, int mark, int size,byte DriveNum, float Position, float Skew){
        super(Id,mark,size);
        this.DriveNum = DriveNum;
        this.Position = Position;
        this.Skew = Skew;
    }
    @Override
    byte[] getByteArray() {
        int i = 0;
        byte [] array = new byte[18];
        for(byte b : super.getByteArray())
            array[i++] = b;
        array[i++]=DriveNum;
        byte [] bufarray = new byte[4];
        for(byte b : ByteBuffer.wrap(bufarray).putFloat(Position).array())
            array[i++]=b;
        for(byte b : ByteBuffer.wrap(bufarray).putFloat(Skew).array())
            array[i++]=b;
        return array;
    }
}

//Запись параметра (6)
class C00CommandATV <T> extends C00Command {
    private int Addr;
    private byte Type;
    private T Value;

    C00CommandATV(byte Id, int mark, int size, int Addr, byte Type, T Value) {
        super(Id, mark, size);
        this.Addr = Addr;
        this.Type = Type;
        this.Value = Value;
    }

    @Override
    byte[] getByteArray() {
        int i = 0;
        byte[] array;
        String sType = this.Value.getClass().toString();
        if (sType.equals("class java.lang.Byte"))
            array = new byte[15];
        else
            array = new byte[18];
        for (byte b : super.getByteArray())
            array[i++] = b;
        byte[] bufarray = new byte[4];
        for (byte b : ByteBuffer.wrap(bufarray).putInt(Addr).array())
            array[i++] = b;
        array[i++] = Type;
        switch (sType) {
            case ("class java.lang.Byte"):
                array[i] = (Byte) Value;
                break;
            case ("class java.lang.Integer"):
                for (byte b : ByteBuffer.wrap(bufarray).putInt((Integer) Value).array())
                    array[i++] = b;
                break;
        }
        return array;
    }
}

//Чтение параметра (7)
class C00CommandA extends C00Command{
    private int Addr;
    C00CommandA(byte Id, int mark, int size, int Addr){
        super(Id, mark, size);
        this.Addr = Addr;
    }

    @Override
    byte[] getByteArray() {
        int i = 0;
        byte [] array = new byte[13];
        for(byte b : super.getByteArray())
            array[i++] = b;
        byte [] bufarray = new byte[4];
        for(byte b : ByteBuffer.wrap(bufarray).putInt(Addr).array())
            array[i++]=b;
        return array;
    }
}

//Выполнение функции (10)
class C00CommandF extends C00Command{
    private byte FunctionCode;
    C00CommandF(byte Id, int mark, int size, byte FunctionCode){
        super(Id, mark, size);
        this.FunctionCode = FunctionCode;
    }

    @Override
    byte[] getByteArray() {
        int i = 0;
        byte [] array = new byte[13];
        for(byte b : super.getByteArray())
            array[i++] = b;
        array[i]=FunctionCode;
        return array;
    }
}