package bussiness.service.impl;

import bussiness.config.IOFile;
import bussiness.entity.Customer;
import bussiness.entity.Food;
import bussiness.service.IFoodService;

import java.util.List;
import java.util.Optional;

public class FoodService implements IFoodService {
    private List<Food> foods;

    public FoodService() {
        foods = IOFile.readFromFile(IOFile.FOOD_PATH);
    }

    @Override
    public List<Food> findAll() {
        return foods;
    }

    @Override
    public boolean save(Food food) {
        if (findById(food.getId())!=null){
            // đã tồn tại -> chỉnh sửa
            foods.set(foods.indexOf(findById(food.getId())),food);
        }else {
            // thêm mới
            foods.add(food);
        }
        // thay đổi dữ liệu
        // lưu vào file
        IOFile.writeToFile(IOFile.FOOD_PATH,foods);
        return true;
    }

    @Override
    public Food findById(Integer id) {
        Optional<Food> optional =foods.stream()
                .filter(c->c.getId()==id).findFirst();
        return optional.orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        foods.remove(findById(id));
        IOFile.writeToFile(IOFile.FOOD_PATH,foods);
    }
}
