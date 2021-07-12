import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

/* Объект с полями примитивных и ссылочных типов*/
public class SimpleData {
    private int intData;
    private byte byteData;
    private String stringData;
    public SimpleData simpleData;
    private boolean boolData;


    public SimpleData() {

    }

    public SimpleData(int intData, byte byteData, String stringData, boolean boolData) {
        this.intData = intData;
        this.byteData = byteData;
        this.stringData = stringData;
        this.boolData = boolData;
    }

    public int getIntData() {
        return intData;
    }

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

    public boolean isBoolData() {
        return boolData;
    }

    public void setBoolData(boolean boolData) {
        this.boolData = boolData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleData that = (SimpleData) o;
        return intData == that.intData && byteData == that.byteData && Objects.equals(stringData, that.stringData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(intData, byteData, stringData);
    }



/*public void setLongData(long longData) {
        this.longData = longData;
    }*/
}
