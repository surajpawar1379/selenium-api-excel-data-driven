package resources;

public enum APIResources {

    // Google Maps / Places
    ADD_PLACE("/maps/api/place/add/json"),
    GET_PLACE("/maps/api/place/get/json"),
    DELETE_PLACE("/maps/api/place/delete/json"),

    // E-Commerce
    ECOM_LOGIN("/api/ecom/auth/login"),
    ECOM_ADD_PRODUCT("/api/ecom/product/add-product"),
    ECOM_CREATE_ORDER("/api/ecom/order/create-order"),
    ECOM_DELETE_PRODUCT("/api/ecom/product/delete-product/{productId}"),

    // Library
    LIBRARY_ADD_BOOK("/Library/Addbook.php"),
    LIBRARY_GET_BOOK("/Library/GetBook.php"),
    LIBRARY_DELETE_BOOK("/Library/DeleteBook.php");

    private final String resource;

    APIResources(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }
}
