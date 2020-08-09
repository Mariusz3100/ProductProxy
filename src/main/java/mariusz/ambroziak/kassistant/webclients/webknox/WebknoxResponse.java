package mariusz.ambroziak.kassistant.webclients.webknox;


import javax.persistence.*;

@Entity
@Table(name = "WebknoxResponse",schema = "cache")
public class WebknoxResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wkr_id;


    @Column(length = 10000000)
    private String response;

    @Column(length = 400)
    private String url;

    @Column(length = 400)
    private String query;


    public Long getWkr_id() {
        return wkr_id;
    }

    public void setWkr_id(Long wkr_id) {
        this.wkr_id = wkr_id;
    }


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
