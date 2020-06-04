package app.eventos;

import app.model.Item;

public class ItemSelected {

    private Item item;

    public ItemSelected(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
