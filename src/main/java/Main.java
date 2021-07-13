import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class Main {

    public static void main(String[] args)  {
        SimpleData a = new SimpleData(10, (byte)0x34, "str a", null, true);
        SimpleData b = new SimpleData(20, (byte)0x75, "str b", a, true);
        SimpleData c = new SimpleData(30, (byte)0x91, "str c", b, true);

        SimpleData d = (SimpleData) Main.copyOf(c);
    }

    private static String primitiveValueToString(Object obj, Field f) {
        try {
            switch (f.getType().getTypeName()) {
                case "byte":
                    return String.valueOf(f.getByte(obj));
                case "short":
                    return String.valueOf(f.getShort(obj));
                case "int":
                    return String.valueOf(f.getInt(obj));
                case "long":
                    return String.valueOf(f.getLong(obj));
                case "float":
                    return String.valueOf(f.getFloat(obj));
                case "double":
                    return String.valueOf(f.getDouble(obj));
                case "char":
                    return String.valueOf(f.getChar(obj));
                case "boolean":
                    return String.valueOf(f.getBoolean(obj));
                case "java.lang.String":
                    return (String) f.get(obj);
                default:
                    return "can't get primitive value";
            }
        }catch (IllegalAccessException e){
            System.out.println(e);
        }
        return "error";

    }


    private static void serializeObject(Object obj) throws IllegalAccessException {

        try(
            PrintWriter printWriter = new PrintWriter(new FileWriter("src/Main/resources/serlzd.txt"))){

            printWriter.println(":"+obj.getClass().getCanonicalName()+" = ");
            printWriter.println("{");
            //==============================================

            for (Field f: obj.getClass().getDeclaredFields()) {

                f.setAccessible(true);
                printWriter.print("   "+f.getName()+":"+f.getType().getTypeName()); //примитивное поле или String
                if (f.getType().isPrimitive() || f.getType().getTypeName().equals("java.lang.String")){
                    String primitiveValue = primitiveValueToString(obj, f);
                    printWriter.println(" = " + primitiveValue+";");
                }

                if (f.getType().getTypeName().equals("SimpleData")){ //вывод SimpleData
                    printWriter.println(" = {");

                    for (Field SimpleField: f.get(obj).getClass().getDeclaredFields()) {
                        SimpleField.setAccessible(true);
                        printWriter.print("      " + SimpleField.getName() + ":" + SimpleField.getType().getTypeName());
                        if (SimpleField.getType().isPrimitive() || SimpleField.getType().getTypeName().equals("java.lang.String")) {
                            String primitiveValue = primitiveValueToString(obj, SimpleField);
                            printWriter.println(" = " + primitiveValue + ";");
                        }
                    }

                    printWriter.println("   };");
                }


                if (f.getType().isArray()){ //Вывод String массива
                    printWriter.println(" = ");
                    ArrayList<String> buf = new ArrayList<>();
                    for (int i = 0; i < ((String[])(f.get(obj))).length  ;i++ ){
                        buf.add("   "+((String[])(f.get(obj)))[i]+",");
                    }

                    buf.set(buf.size()-1, buf.get(buf.size()-1).replace(',', ';'));
                    for (String elem: buf)
                        printWriter.println(elem);
                }

                if (Collection.class.isAssignableFrom(f.getType())){//Вывод коллекции
                    printWriter.println(" = ");
                    ArrayList<String> buf = new ArrayList<>();
                    for (Object elem : (Collection)f.get(obj))
                        buf.add("   "+elem.toString()+",");

                    buf.set(buf.size()-1, buf.get(buf.size()-1).replace(',', ';'));
                    for (String elem: buf)
                        printWriter.println(elem);

                }


                if (Map.class.isAssignableFrom(f.getType())){ //Вывод Map<String, SimpleData>
                    printWriter.println(" = {");
                    for(Map.Entry<String, SimpleData> entry : ((HashMap<String, SimpleData> )(f.get(obj))   ).entrySet()) {
                        String key = entry.getKey();
                        SimpleData val = entry.getValue();

                        //System.out.println(key+" "+value.getIntData());

                        printWriter.println("      "+key+",");
                        printWriter.println("      {");

                        for (Field valueSimpleData: val.getClass().getDeclaredFields() ) {
                            valueSimpleData.setAccessible(true);
                            printWriter.print("         " + valueSimpleData.getName() + ":" + valueSimpleData.getType().getTypeName());
                            if (valueSimpleData.getType().isPrimitive() || valueSimpleData.getType().getTypeName().equals("java.lang.String")) {
                                String primitiveValue = primitiveValueToString(obj, valueSimpleData);
                                printWriter.println(" = " + primitiveValue + ";");

                            } else printWriter.println(" - Объект;");
                        }
                        printWriter.println("      },");
                    }
                    printWriter.println("   };");
                }
            }

            //=======================================
            printWriter.println("};");

            System.out.println("Объект сериализован");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void printLine() {
        System.out.println("--------------------------------------------");
    }

    private static void printParenInfo(Object object){ //задание 1
        Class<?> parentClass = object.getClass().getSuperclass();
        ArrayList<String> parentsMethodsStrArray = new ArrayList<>();

        System.out.println("Parents fileds:");
        Field[] parenFields = parentClass.getDeclaredFields();
        for (Field parentFeild : parenFields)
            System.out.println("   " + parentFeild.getName() + " as " + parentFeild.getType().getCanonicalName());

        System.out.println("Parents methods:");
        Method[] parentMethods = parentClass.getDeclaredMethods();
        for (Method m : parentMethods)
            parentsMethodsStrArray.add("   "+m.getName() + " return " + m.getReturnType().getCanonicalName());

        parentsMethodsStrArray.sort(null); //вывод по алфавиту
        for (String str: parentsMethodsStrArray)
            System.out.println(str);
    }


    public static void publicPrintFieldsNames(Object object){ //public для тестов
        printFieldsNames(object);
    }
    private static void printFieldsNames(Object object) {
        printLine();
        printParenInfo(object);

        System.out.println("Self fields:");
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field f : fields) {
            System.out.println("   "+f.getName() + " as " + f.getType().getCanonicalName());
         }


    }

    public static void publicPrintMethodsNames(Object object){ //public для тестов
        printMethodsNames(object);
    }
    private static void printMethodsNames(Object object) {
        printLine();
        printParenInfo(object);

        System.out.println("Self methods:");
        ArrayList<String> rez = new ArrayList<>();
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method m : methods)
            rez.add("   "+m.getName() + " return " + m.getReturnType().getCanonicalName());

        rez.sort(null); //вывод по алфавиту
        for (String str: rez)
            System.out.println(str);

    }

    private static void printFieldsValues(Object object) {
        printLine();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                Object value = f.get(object);
                System.out.println(f.getName() + " as " + f.getType().getCanonicalName() + " = " + value);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private static void callSetterWhith8(Object object) {
        printLine();
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method m : methods) {
            String name = m.getName();
            if (!name.substring(0, 3).equals("set"))
                continue;
            Class<?>[] argTypes = m.getParameterTypes();
            try {
                switch (argTypes[0].getTypeName()) {
                    case "int":
                        m.invoke(object, 8);
                        break;
                    case "byte":
                        m.invoke(object, (byte) 8);
                        break;
                    default:
                        m.invoke(object, "8");
                        break;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }



    public static String[] copyArray(String[] arr){
        ArrayList<String> returnArr = new ArrayList<>(Arrays.asList(arr));
        return returnArr.toArray(new String[0]);
    }

    public static List<String> copyList(List<String> list){
        ArrayList<String> returnList = new ArrayList<>();
        returnList.addAll(list);
        return returnList;
    }

    public static SimpleData copySimpleData(SimpleData source){

        if (source == null) return null;

        Object objectData = new Object();

        if (source.getObjectData() instanceof SimpleData)
            objectData = copySimpleData((SimpleData) source.getObjectData());

        return new SimpleData(
                source.getIntData(),
                source.getByteData(),
                source.getStringData(),
                objectData,
                source.isBoolData()
        );
    }

    public  static Map<String, SimpleData> copyCollection(Map<String, SimpleData> collectoin){
        HashMap<String, SimpleData> returnCollection = new HashMap<>();

        for(Map.Entry<String, SimpleData> entry : collectoin.entrySet()) {
            String key = entry.getKey();
            SimpleData value = entry.getValue();
            returnCollection.put(key, copySimpleData(value));
        }
        return returnCollection;
    }

    public static Object copy(Object source) {


        Method [] methods = source.getClass().getDeclaredMethods();
        for (Method method: methods) {
            if (method.isAnnotationPresent(noCopy.class)){
                System.err.println("A-A-A-A");
            }
        }

        String sourceTypeName = source.getClass().getTypeName();
        if (sourceTypeName.equals("WithCollectionData")){
            WithCollectionData current = (WithCollectionData) source;
            WithCollectionData newObject = new WithCollectionData(
                    current.getIntData(),
                    current.getByteData(),
                    current.getStringData(),
                    copySimpleData((SimpleData) current.getObjectData()),
                    current.isBoolData(),
                    copyArray(current.getStringArray()),
                    copyList(current.getListString()),
                    copyCollection(current.getMapStringSimpleData()));
            return newObject;
        }

        if (sourceTypeName.equals("WithArrayData")){
            WithArrayData current = (WithArrayData) source;
            Object buf;
            buf = current.getObjectData();
            return new WithArrayData(
                    current.getIntData(),
                    current.getByteData(),
                    current.getStringData(),
                    copySimpleData((SimpleData) source),
                    current.isBoolData(),
                    copyArray(current.getStringArray())
            );
        }

        if (sourceTypeName.equals("SimpleData")){
            return copySimpleData((SimpleData) source);
        }

        return source;
    }

    public static Object copyOf(Object source) {
        try {
            Object dist;
            dist = copy(source);
            return dist;
        } catch (Exception e) {
            return null;
        }
    }

    public static void benchmark() {
        try {
            Date start = new Date();
            SimpleData simpleData = new SimpleData();
            for (int i = 0; i < 1000000000; ++i)
                simpleData.setIntData(500);
            System.out.println(new Date().getTime() - start.getTime());

            Method method = SimpleData.class.getDeclaredMethod("setIntData", Integer.TYPE);
            start = new Date();
            for (int i = 0; i < 1000000000; ++i) {
                method.invoke(simpleData, 500);
            }
            System.out.println(new Date().getTime() - start.getTime());

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
