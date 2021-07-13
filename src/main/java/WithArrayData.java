import java.util.Arrays;

/* Объект с предком и массивом*/
public class WithArrayData extends SimpleData {
    private String[] stringArray = new String[100];
    public WithArrayData() {
    }

    public WithArrayData(int intData, byte byteData, String stringData, Object objectData, boolean boolData, String[] stringArray) {
        super( intData,  byteData, stringData, objectData, boolData);
        this.stringArray = stringArray;
    }

    public String[] getStringArray() {
        return stringArray;
    }

    public void setStringArray(String[] stringArray) {
        this.stringArray = stringArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WithArrayData that = (WithArrayData) o;
        return Arrays.equals(stringArray, that.stringArray);
    }

}
