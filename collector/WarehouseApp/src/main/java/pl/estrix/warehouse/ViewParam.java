package pl.estrix.warehouse;

public class ViewParam {

    private String code;
    private String name;

    private Integer id;

    public ViewParam(){
        this.id = 0;
    }

    public ViewParam(Integer id){
        this.id = id;
    }

    public ViewParam(String code){
        this.code = code;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ViewParam{" +
                ", code='" + code + '\'' +
                ", id=" + id +
                '}';
    }
}
