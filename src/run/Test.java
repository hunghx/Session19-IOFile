package run;

import bussiness.config.IOFile;
import bussiness.entity.Food;
import bussiness.entity.Order;
import bussiness.service.IFoodService;
import bussiness.service.impl.FoodService;

import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args) {
//        Food f1 = new Food(1,"Đá Me",100,"ngon");
//        Food f2 = new Food(2,"Pịa",110,"ngon");
//        Food f3 = new Food(3,"Bánh Mì",90,"ngon");
//        Food f4 = new Food(4,"Gà",50,"ngon");
//        Food f5 = new Food(5,"Hàu",150,"ngon");
//        List<Food> f = Arrays.asList(f1,f2,f3,f4,f5);
//        IOFile.writeToFile(IOFile.FOOD_PATH,f);
        List<Order> list = IOFile.readFromFile(IOFile.ORDER_PATH);
        for (Order o:list) {
            System.out.println(o);
        }
    }
}
