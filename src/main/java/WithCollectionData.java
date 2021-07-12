import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* Объект с предком и коллекциями*/
public class WithCollectionData extends WithArrayData {

    private List<String> listString;
    private Map<String, SimpleData> mapStringSimpleData;

    public WithCollectionData(int intData, byte byteData, boolean boolData, String stringData, String[] stringArray, List<String> listString, Map<String, SimpleData> mapStringSimpleData) {
        super(intData, byteData, boolData, stringData, stringArray);
        this.listString = listString;
        this.mapStringSimpleData = mapStringSimpleData;
    }

    public List<String> getListString() {
        return listString;
    }

    public void setListString(List<String> listString) {
        this.listString = listString;
    }

    public Map<String, SimpleData> getMapStringSimpleData() {
        return mapStringSimpleData;
    }

    public void setMapStringSimpleData(Map<String, SimpleData> mapStringSimpleData) {
        this.mapStringSimpleData = mapStringSimpleData;
    }
}
