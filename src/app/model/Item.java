package app.model;

public class Item {
    private String id;
    private String product;
    private Integer quantity;
    private Boolean purchased;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Item(String id, String product, Integer quantity, Boolean purchased) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.purchased = purchased;
    }

    public Item(String id, String product, Integer quantity, String purchased) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        setPurchasedAsString(purchased);
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(Boolean purchased) {
        this.purchased = purchased;
    }

    public void setPurchasedAsString(String string) {
        if (string.equalsIgnoreCase("yes")) {
            setPurchased(true);
        }
        else {
            setPurchased(false);
        }
    }

    public String getPurchasedAsString() {
        return purchased ? "Yes" : "No";
    }
}
