package bussiness.config;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOFile{
    public static final String CUSTOMER_PATH ="C:\\Users\\hung1\\OneDrive\\Desktop\\demoApi\\BTTongHop\\src\\bussiness\\data\\customer.txt";
    public static final String FOOD_PATH ="C:\\Users\\hung1\\OneDrive\\Desktop\\demoApi\\BTTongHop\\src\\bussiness\\data\\food.txt";
    public static final String ORDER_PATH ="C:\\Users\\hung1\\OneDrive\\Desktop\\demoApi\\BTTongHop\\src\\bussiness\\data\\order.txt";
    public static final String ORDERDETAIL_PATH ="C:\\Users\\hung1\\OneDrive\\Desktop\\demoApi\\BTTongHop\\src\\bussiness\\data\\orderdetail.txt";
    public static <T> List<T> readFromFile(String path){
        List<T> list = new ArrayList<>();
        File file = new File(path);
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis =new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            list = (List<T>) ois.readObject();
        }catch (FileNotFoundException e){
            return new ArrayList<>();
        }catch (EOFException e){

        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            if (ois!=null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (fis!=null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return list;
    }

    public static <T> void writeToFile(String path, List<T> list){
        File file  = new File(path);
        FileOutputStream fos = null;
        ObjectOutputStream oos =null;
        try{
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
//            oos.flush();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (oos!=null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if(fos!=null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
