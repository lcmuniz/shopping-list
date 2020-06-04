package app.eventos;

import app.model.Item;

public class ItemInserted {

    private Item item;

    public ItemInserted(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
