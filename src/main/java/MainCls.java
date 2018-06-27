import com.alibaba.fastjson.JSON;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import doc.Api;
import doc.ApiBean;
import doc.ApiInfo;
import doc.Tags;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author xuejike
 */
public class MainCls {


    protected static String url = "http://192.168.1.99:8080/v2/api-docs";
    protected static Template temp;
    protected static String path = "E:\\project\\swagger-retrofit2\\cls";
    protected static List<String> outApiTags=new ArrayList<>();
    static {

    }
    public static void main(String[] arg) throws IOException, UnirestException, TemplateException {
        initTpl();
        ApiBean apiBean = getApiData();
        HashMap<String, List<Api>> clsMap = buildClsMap(apiBean);
        clsMap = classClean(clsMap);
        outClassFiles(clsMap, path);

    }

    private static void initTpl() throws IOException {
        // Create your Configuration instance, and specify if up to what FreeMarker
// version (here 2.3.22) do you want to apply the fixes that are not 100%
// backward-compatible. See the Configuration JavaDoc for details.
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);

// Specify the source where the template files come from. Here I set a
// plain directory for it, but non-file-system sources are possible too:
        cfg.setDirectoryForTemplateLoading(new File("E:\\project\\swagger-retrofit2\\tpl"));

// Set the preferred charset template files are stored in. UTF-8 is
// a good choice in most applications:
        cfg.setDefaultEncoding("UTF-8");

// Sets how errors will appear.
// During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        temp = cfg.getTemplate("test.ftl");
    }

    private static void outClassFiles(HashMap<String, List<Api>> clsMap,
                                      String path) throws IOException, TemplateException {
        for (Map.Entry<String, List<Api>> entry : clsMap.entrySet()) {
            String key = entry.getKey();
            String clsName = buildName(key);
            File file = new File(path, clsName + ".java");
            if (file.exists()){
                continue;
            }else{
                file.createNewFile();
            }
            HashMap<String, Object> data = new HashMap<>();
            data.put("clsName",clsName);
            data.put("apis",entry.getValue());
            data.put("tool",new Tool());
            FileWriter fileWriter = new FileWriter(file);
            temp.process(data,fileWriter);


        }
    }


    @Nullable
    private static ApiBean getApiData() throws UnirestException {
        String body = Unirest.get(url).asString().getBody();
        return JSON.parseObject(body, ApiBean.class);
    }

    /**
     *
     * @param apiBean
     * @return
     */
    @NotNull
    private static HashMap<String, List<Api>> buildClsMap(ApiBean apiBean) {
        HashMap<String, List<Api>> clsMap = new HashMap<>(apiBean.tags.size());
        for (Tags tag : apiBean.tags) {
            clsMap.put(tag.name,new ArrayList<>());
        }
        for (Map.Entry<String, Api> entry : apiBean.paths.entrySet()) {

            ApiInfo apiInfo = null;
            entry.getValue().url = entry.getKey();
            int i = entry.getKey().lastIndexOf("/");
            if (i >= 0){
                entry.getValue().name = entry.getKey().substring(i+1);
            }

            if (entry.getValue().get != null){
                apiInfo=entry.getValue().get;
            }else if (entry.getValue().post != null){
                apiInfo = entry.getValue().post;
            }else{
                continue;
            }
            String tagStr = apiInfo.tags.get(0);
            Optional.ofNullable(clsMap.get(tagStr))
                    .ifPresent(ls->{
                        ls.add(entry.getValue());
                    });
        }
        return clsMap;
    }

    private static HashMap<String, List<Api>> classClean(HashMap<String, List<Api>> clsMap) {

        if (outApiTags.size()>0){
            HashMap<String, List<Api>> map = new HashMap<>();
            for (String tag : outApiTags) {
                Optional.ofNullable(clsMap.get(tag))
                        .ifPresent(ls->{
                            map.put(tag,ls);
                        });
            }
            if (map.size()>0){
                return map;
            }
        }
        return clsMap;
    }


    public static String buildName(String name){
        String replace = name.replace("controller", "service");
        String[] split = replace.split("-");
        StringBuilder sb=new StringBuilder();
        for (String s : split) {
            char[] chars = s.toCharArray();
            if (chars[0]>=97){
                chars[0]=(char) (chars[0]-32);
            }
           sb.append(chars);
        }
        return sb.toString();
    }




}
