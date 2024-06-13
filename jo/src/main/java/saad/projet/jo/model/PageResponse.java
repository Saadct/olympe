package saad.projet.jo.model;

import java.util.List;

public class PageResponse<T> {
    private List<T> content;
    private int totalPage;

    public PageResponse(List<T> content, int totalPage) {
        this.content = content;
        this.totalPage = totalPage;
    }
}
