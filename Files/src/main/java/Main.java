import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        File basketTxt = new File("Basket.txt");
        Basket basket = (basketTxt.exists()) ? Basket.loadFromTxtFile(basketTxt) : new Basket(new int[]{59, 150, 243, 30, 580},
                new String[]{"Хлеб", "Масло", "Чай", "Вода", "Колбаса"});

        System.out.println("Список товаров доступных для добавления в корзину:");
        StringBuilder listFood = new StringBuilder();
        String[] food = basket.getFood();
        int[] price = basket.getPrice();
        for (int i = 0; i < food.length; i++) {
            listFood.append((i + 1) + ". " + food[i] + " " + price[i] + " руб/шт \n");
        }
        System.out.println(listFood.toString());
        while (true) {
            System.out.println("Выберите товар путем ввода его номера и количества. Для завершения покупок введите end");
            String inputStr = scanner.nextLine();
            if (inputStr.equals("end")) break;
            else if (inputStr == "") continue;
            String[] parts = inputStr.split(" ");
            if (parts.length < 2) {
                System.out.println("Вы некорректно ввели номер товара и его количество!!");
                continue;
            }
            int numFood = Integer.parseInt(parts[0]) - 1;
            int countFood = Integer.parseInt(parts[1]);
            if (numFood < 0 || numFood > (food.length - 1)) {
                System.out.println("Такого номера товара нет в предложенном перечне");
                continue;
            }
            basket.addToCart(numFood, countFood);
        }
        basket.printCart(listFood);
    }
}
