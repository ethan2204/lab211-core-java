
package model;


public class SetMenu {
    private String menuId;
    private String menuName;
    private double price;
    private String ingredients;

    //constructor
    public SetMenu() {
    }

    public SetMenu(String menuId, String menuName, double price, String ingredients) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.price = price;
        this.ingredients = ingredients;
    }

    //Getter and setter
    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }  
    public void showMenu(){
        System.out.println("Code        : " + menuId);
        System.out.println("Name        : " + menuName);
        System.out.printf("Price       : %.2f Vnd\n", price);
        System.out.println("Ingredients:");
        System.out.println(ingredients);
        System.out.println("--------------------------------------");        
    }
}
