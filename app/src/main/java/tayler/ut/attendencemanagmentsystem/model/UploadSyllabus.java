package tayler.ut.attendencemanagmentsystem.model;


public class UploadSyllabus {


    public String name;
    public String url;

    public UploadSyllabus() {
    }

    public UploadSyllabus(String name, String url) {
        this.name = name;
        this.url = url;
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
