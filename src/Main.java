public class Main {
    public static void main(String[] args) {
        System.out.print("Введите текст ---> " );
        String s = CommonUtilites.inputString();
        System.out.println("Вы ввели: " + s);
        Menu.printMenu();
    }
}