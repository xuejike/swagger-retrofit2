package doc;

/**
 * @author xuejike
 */
public class Api {
    public String name;
    public String url;
    public ApiInfo post;
    public ApiInfo get;

    public ApiInfo getPost() {
        return post;
    }

    public void setPost(ApiInfo post) {
        this.post = post;
    }

    public ApiInfo getGet() {
        return get;
    }

    public void setGet(ApiInfo get) {
        this.get = get;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
