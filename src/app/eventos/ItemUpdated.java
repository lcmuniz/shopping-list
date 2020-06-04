package app.eventos;

import app.model.Item;

public class ItemUpdated {

    private Item item;

    public ItemUpdated(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
