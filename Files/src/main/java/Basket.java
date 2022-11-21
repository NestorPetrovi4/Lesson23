import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class Basket {
    private String[] food;
    private int[] price;
    private int[] baskets;
    private int sumFood;
    private ClientLog clientLog = new ClientLog();

    public Basket(int[] price, String[] food) {
        this.food = food;
        this.price = price;
        baskets = new int[food.length];
    }

    public void addToCart(int productNum, int amount) {
        baskets[productNum] += amount;
        sumFood += amount * price[productNum];
        File basketTxt = new File("Basket.json");
        saveTxt(basketTxt);
        clientLog.log(productNum, amount);
    }

    public int[] getPrice() {
        return price;
    }

    public String[] getFood() {
        return food;
    }

    public int getSumFood() {
        return sumFood;
    }

    public void printCart(StringBuilder listFood) {
        if (sumFood == 0) {
            System.out.println("Ваша корзина пуста");
        } else {
            System.out.println("Ваш заказ: \n");
            listFood.setLength(0);
            for (int i = 0; i < baskets.length; i++) {
                if (!(baskets[i] == 0)) {
                    listFood.append(food[i] + " " + baskets[i] + " шт. * " + price[i] + " руб = " + (baskets[i] * price[i]) + " руб. \n");
                }
            }
            listFood.append("Итоговая сумма покупки = " + sumFood + " руб");
            System.out.println(listFood.toString());
        }
        clientLog.exportAsCSV(new File("log.csv"));
    }

    public void saveTxt(File textFile) {
        try (FileOutputStream fos = new FileOutputStream(textFile)) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            String stringArray = gson.toJson(this);
            // перевод строки в массив байтов
            byte[] bytes = stringArray.getBytes();
            // запись байтов в файл
            fos.write(bytes, 0, bytes.length);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Basket loadFromTxtFile(File textFile) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String textJson = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
            textJson = reader.readLine();
            reader.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        Basket basket = gson.fromJson(textJson, Basket.class);
        return basket;
    }
}
