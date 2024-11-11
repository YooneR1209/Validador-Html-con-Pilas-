package models;

public class HtmlValidador {
    private Integer id;
    private String htmlCode;
    private boolean isValid;

    public HtmlValidador(String htmlCode) {
        this.htmlCode = htmlCode;
        this.isValid = true;
    }

    public HtmlValidador(){
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHtmlCode() {
        return htmlCode;
    }

    public void setHtmlCode(String htmlCode) {
        this.htmlCode = htmlCode;
    }

    public boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

}
