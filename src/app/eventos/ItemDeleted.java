package app.eventos;

public class ItemDeleted {

    private String id;

    public ItemDeleted(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
