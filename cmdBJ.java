//TODO
//1. Добавить цель игры - опустошить банк казино
//2. Добавить возможность продолжить в том же файле
//2.5 Так же удалять уже использованные в игре карты из банка карт
//3. Поработать со ставками
//4. Добавить функцию удвоения последней ставки
//5. Добавить сейв (Балансы игрока и банка, колличество побед/поражений, добаление в банк денег после каждой 10 игры) в отдельный файл после каждой игры
//6. Поработать с Exceptions. e.g. При назначении ставки если ввести букву вылетит ошибка
//....
//∞. Реализовать нормальный графический интерфейс

import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Main {
    //Массив со всеми картами
//    static String[] cards = {"♠ Two 2", "♠ Three 3", "♠ Four 4", "♠ Five 5", "♠ Six 6", "♠ Seven 7", "♠ Eight 8", "♠ Nine 9", "♠ Ten 10", "♠ Jack 10", "♠ Queen 10", "♠ King 10", "♠ Ace 11", "♥ Two 2", "♥ Three 3", "♥ Four 4", "♥ Five 5", "♥ Six 6", "♥ Seven 7", "♥ Eight 8", "♥ Nine 9", "♥ Ten 10", "♥ Jack 10", "♥ Queen 10", "♥ King 10", "♥ Ace 11", "♣ Two 2", "♣ Three 3", "♣ Four 4", "♣ Five 5", "♣ Six 6", "♣ Seven 7", "♣ Eight 8", "♣ Nine 9", "♣ Ten 10", "♣ Jack 10", "♣ Queen 10", "♣ King 10", "♣ Ace 11", "♦ Two 2", "♦ Three 3", "♦ Four 4", "♦ Five 5", "♦ Six 6", "♦ Seven 7", "♦ Eight 8", "♦ Nine 9", "♦ Ten 10", "♦ Jack 10", "♦ Queen 10", "♦ King 10", "♦ Ace 11"};
    //--!--Поменял на ArrayList чтобы удобнее было удалять из колоды карты игроков, чтобы можно было начать игру заново, нужно будет добавить клона и с каждой новой игрой дублировать его.
    static ArrayList<String> cards = new ArrayList<>(Arrays.asList("♠ Two 2", "♠ Three 3", "♠ Four 4", "♠ Five 5", "♠ Six 6", "♠ Seven 7", "♠ Eight 8", "♠ Nine 9", "♠ Ten 10", "♠ Jack 10", "♠ Queen 10", "♠ King 10", "♠ Ace 11", "♥ Two 2", "♥ Three 3", "♥ Four 4", "♥ Five 5", "♥ Six 6", "♥ Seven 7", "♥ Eight 8", "♥ Nine 9", "♥ Ten 10", "♥ Jack 10", "♥ Queen 10", "♥ King 10", "♥ Ace 11", "♣ Two 2", "♣ Three 3", "♣ Four 4", "♣ Five 5", "♣ Six 6", "♣ Seven 7", "♣ Eight 8", "♣ Nine 9", "♣ Ten 10", "♣ Jack 10", "♣ Queen 10", "♣ King 10", "♣ Ace 11", "♦ Two 2", "♦ Three 3", "♦ Four 4", "♦ Five 5", "♦ Six 6", "♦ Seven 7", "♦ Eight 8", "♦ Nine 9", "♦ Ten 10", "♦ Jack 10", "♦ Queen 10", "♦ King 10", "♦ Ace 11"));

    //Переменные
    static int bet = 0; //Ставка
    static String gameon; //Переключатель On/Off
    static int balance = 1000; //Баланс игрока
    static ArrayList<Integer> player_hand = new ArrayList<>();
    static int new_card;
    static ArrayList<Integer> dealer_hand = new ArrayList<>(); // Рука дилера

    static Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {


//Начальный экран - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        System.out.println("Добро пожаловать в cmdBlackJack! В настоящий момент предлагается только упрощенная версия игры, но мы продолжаем работать над добавлением функционала.");
        System.out.println("Хотите ли вы инициализировать начало игры? (Y - да, N - нет)");

        //input
        gameon = sc.nextLine();

        //on\off для начального экрана
        if (gameon.equals("Y") || gameon.equals("y")){
            System.out.println("Отлично! Правила объянять не стану, приступим к игре.");
        } else if (gameon.equals("N") || gameon.equals("n")){
            System.out.println("Well... kinda sorry to hear that from you, but whatever. Have a nice time not playing my game :(");
            return;
        } else {
            System.out.println("Sorry, didn't recognize that. Let's proceed anyway.");
        }

//Начало новой игры, ведет к методу в котором обозначен порядок вызова других методов
        new_game();

    }





//Методы - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

//Порядок вызова методов (чтобы игру можно было продолжить без перезапуска программы)
    static void new_game(){
        bet();
        player_init();
        //выдача карт дилеру
        turn();
        //докинуть дилеру карты
    }

    static void turn(){
        player_show_cards(player_hand);
        player_show_score(player_hand);
        add_card();
    }

//Новая ставка
    static void bet(){
    //Ваш баланс
        System.out.println("Ваш баланс на данный момент составляет: "+balance);

    //Назначение ставки
        while(bet<1){
            bet = new_bet();
        }

    //Проверка ставки относительно баланса игрока
        while(bet>balance) {
            System.out.println("На Вашем счету нет такой суммы, пожалуйста сделайте ставку ещё раз.");
            bet = new_bet();
        }

    //Снятие размера ставки с баланса
        balance -= bet;
        System.out.println("Вы сделали ставку размером в "+bet+", на Вашем счету осталось "+balance);

    }

    //Назначение ставки
    static int new_bet(){
        //Есть ставки 1, 5, 10, 50, 100, 500, 1000                                                                      ! Добавить цикл и массив со ставками
//Добавить больше гибкости к выбору ставки (100+50+10=160)                                                              !
//Нужно добавить удвоение ставки и дополнительные значения для ставок (10000, 50000)                                    !
        Scanner sc = new Scanner(System.in);
        System.out.println("Сделайте свою ставку (1, 5, 10, 50, 100, 500, 1000)");
        int x = sc.nextInt();
        switch(x) {
            case 1:
                x = 1;
                break;
            case 5:
                x = 5;
                break;
            case 10:
                x = 10;
                break;
            case 50:
                x = 50;
                break;
            case 100:
                x = 100;
                break;
            case 500:
                x = 500;
                break;
            case 1000:
                x = 1000;
                break;
            default:
                x = 0;
                System.out.println("Не знаю что является причиной нарушения правила, но прошу не повторять этого.");
                break;
        }
        return x;
    }


//Раздача карт - обновить чтобы раздавал по одной для вытаскивания доп карт                                             ! Добавить фильтр, чтобы одна карта не светилась дважды                                                                    !!!!!
    static int shuffle(){

        //Переменные
        int result;

        //Вытаскивает рандомную карту (Исправить - карты могут повторяться)
        Random random = new Random();
        //                                                                                                              ! Сделать исключение для двух тузов
        result = random.nextInt(51);
        //                                                                                                              ! Пофиксить ошибку с выходом за 52 карты

//        System.out.println(Arrays.toString(result));    //Проверить результат + toString
        return result;

    }

//Раздача карт игроку
    static void player_init(){

        //Раздача карт
        for (int i = 0; i<2; i++){
            new_card = shuffle();
            player_hand.add(new_card);
        }
    }

//Раздача карт дилеру
    static void dealer_init(){

        //Раздача карт
        for (int i = 0; i<2; i++){
            new_card = shuffle();
            dealer_hand.add(new_card);
        }
    }


    //Вывести карты на экран
    static void player_show_cards(ArrayList<Integer> hand){
        for(int i=0;i<hand.size();++i){
            System.out.print(cards.get((hand.get(i))));
            if(i<hand.size()-1){
                System.out.print(" | ");
            }
        }
        System.out.println("");
    }

//Выводит колличество баллов
    static void player_show_score(ArrayList<Integer> hand){
        int score = 0;

        for(int i=0;i<hand.size();++i){
            score += new Scanner(cards.get((hand.get(i)))).useDelimiter("[^\\d]+").nextInt();
        }
        System.out.println("Ваш счёт: "+score);

    }

//Справшивает вытягивать ли карту
    static void add_card(){
/*        int quit = 0;
        if (quit == 1){

        }*/
        for (int i=0;i<10;i++){
            System.out.println("Хотите ли Вы вытянуть ещё одну карту? (Y/N)");
            String qq = sc.nextLine();
            if (qq.equals("Y") || qq.equals("y")){
                System.out.println("Отлично, теперь у Вас на руках:");
                new_card = shuffle();
                player_hand.add(new_card);
                turn();
                break;
            } else if (qq.equals("N") || qq.equals("n")){
                System.out.println("");
                break;
            } else {
                System.out.println("Пожалуйста введите Y или N в зависимости от Вашего решения.");
            }
        }

