package doc;

import java.util.List;
import java.util.Map;

/**
 * @author xuejike
 */
public class ApiBean {
    public List<Tags> tags;
    public Map<String,Api> paths;

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public Map<String, Api> getPaths() {
        return paths;
    }

    public void setPaths(Map<String, Api> paths) {
        this.paths = paths;
    }
}
