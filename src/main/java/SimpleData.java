import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

/* Объект с полями примитивных и ссылочных типов*/
public class SimpleData {
    private int intData;
    private byte byteData;
    private String stringData;
    private Object objectData = null;
    private boolean boolData;

    public int getIntData() {
        return intData;
    }

    @noCopy
    public void setIntData(int intData) {
        this.intData = intData;
    }

    public byte getByteData() {
        return byteData;
    }

    public void setByteData(byte byteData) {
        this.byteData = byteData;
    }

    public String getStringData() {
        return stringData;
    }

    public void setStringData(String stringData) {
        this.stringData = stringData;
    }

    public Object getObjectData() {
        return objectData;
    }

    public void setObjectData(Object objectData) {
        this.objectData = objectData;
    }

    public boolean isBoolData() {
        return boolData;
    }

    public void setBoolData(boolean boolData) {
        this.boolData = boolData;
    }


    public SimpleData() {

    }

    public SimpleData(int intData, byte byteData, String stringData, Object objectData, boolean boolData) {
        this.intData = intData;
        this.byteData = byteData;
        this.stringData = stringData;
        this.objectData = objectData;
        this.boolData = boolData;
    }


}
