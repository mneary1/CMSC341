package driver ;
import lazybst.* ;
import java.util.* ;


class GroceryItem implements Comparable<GroceryItem> {
   String itemName ;
   String category ;
   int quantity ;

   GroceryItem(String name) {
      itemName = name ;
      category = null ;
      quantity = 0 ;
   }

   GroceryItem(String name, String kind, int howMany) {
      itemName = name ;
      category = kind ;
      quantity = howMany ;
   }

   public int compareTo(GroceryItem otherItem) {
      return itemName.compareTo(otherItem.itemName) ;
   }

   public String toString() {
      return itemName ;
   }
}


public class Proj2GroceryTestJava6 {

   public static void main (String [] args) {

      LazyBST<GroceryItem> groceryList = new LazyBST<GroceryItem>() ;

      System.out.println("Constructing grocery list.") ;
      
      // Try example from project description
      //
      groceryList.insert( new GroceryItem("Apple", "Produce", 5) ) ;
      groceryList.insert( new GroceryItem("Banana", "Produce", 1) ) ;
      groceryList.insert( new GroceryItem("Coca Cola","Soft Drink", 2) ) ;
      groceryList.insert( new GroceryItem("Donuts", "Baked Goods", 2) ) ;
      groceryList.insert( new GroceryItem("Eggs", "Eggs & Dairy", 12) ) ;
      groceryList.insert( new GroceryItem("Fettucini", "Pasta", 1) ) ;
      groceryList.insert( new GroceryItem("Green Tea", "Tea & Coffee", 1) ) ;
      groceryList.insert( new GroceryItem("Ham", "Deli", 1) ) ;
      groceryList.insert( new GroceryItem("Ice Cream", "Dessert", 5) ) ;
      groceryList.insert( new GroceryItem("Jelly", "PB & Jelly", 1) ) ;
      groceryList.insert( new GroceryItem("Kit Kat", "Candy", 9) ) ;
      groceryList.insert( new GroceryItem("Lemons", "Produce", 1) ) ;
      groceryList.insert( new GroceryItem("Marinara Sauce", "Pasta", 1) ) ;
      groceryList.insert( new GroceryItem("Nutella", "PB & Jelly", 2) ) ;
      groceryList.insert( new GroceryItem("Oreos", "Snacks", 4) ) ;
      groceryList.insert( new GroceryItem("Pretzels", "Snacks", 1) ) ;
      groceryList.insert( new GroceryItem("Quaker Oats", "Breakfast", 1) ) ;
      groceryList.insert( new GroceryItem("Rice", "Rice", 2) ) ;
      groceryList.insert( new GroceryItem("Spaghetti", "Pasta", 1) ) ;
      groceryList.insert( new GroceryItem("Tuna Fish", "Canned Goods", 3) ) ;
      groceryList.insert( new GroceryItem("Unsalted Butter", "Eggs & Dairy", 2) ) ;
      groceryList.insert( new GroceryItem("Vinegar", "Condiments", 1) ) ;
      groceryList.insert( new GroceryItem("Worcestershire Sauce", "Condiments", 1) ) ;
      groceryList.insert( new GroceryItem("Xanax", "Pharmacy", 15) ) ;
      groceryList.insert( new GroceryItem("Yams", "Produce", 1) ) ;
      groceryList.insert( new GroceryItem("Ziti", "Pasta", 1) ) ;
      
      groceryList.dump() ;
      System.out.println() ;

      placeOrder( "Kit Kat", groceryList ) ;
      placeOrder( "Snickers", groceryList ) ;

   }


   public static void placeOrder(String thisItem, LazyBST<GroceryItem> gList ) {

      GroceryItem result ;

      result = gList.find( new GroceryItem(thisItem) ) ;

      if (result != null) {
         System.out.print("Get some " + result.itemName) ;
         System.out.print(" from the " + result.category + " aisle. ") ;
         System.out.println("Get " + result.quantity + ".") ;
      } else {
         System.out.println("There are no " + thisItem + " on the list.") ;
      }



   }
}
