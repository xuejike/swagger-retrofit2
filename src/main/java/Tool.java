import java.util.HashMap;

/**
 * @author xuejike
 */
public class Tool {
    public HashMap<String,String> typeMaping= new HashMap<String,String>();

    public Tool() {
        typeMaping.put("string","String");
        typeMaping.put("integer","Integer");
    }

    public String typeMap(String type){
        String typeStr = typeMaping.get(type);
        if (typeStr == null){
            return "String";
        }
        return typeStr;
    }
}
