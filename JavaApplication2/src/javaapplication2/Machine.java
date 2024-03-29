/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.util.ArrayList;

/**
 *
 * @author rand_
 * Singleton  pattern design to implement only one machine 
 *  I've put a variable to detect which window is used 
 * assume  the used slot  will be activated automatically if the user pay from it 
 * to allow me show that I put a function 
 */
public class Machine {
    public static String VALID_PURCHASE= "Your item is ready to select, and your reminder balance is ";
    public static String NO_ENOGH_BALANCE=" No enough balance!, please insert using the slots";
    public static String NOT_ACTIVE_QUEUE="This queue is not active at the moment";
    public static String EMPTY_SELECTED_PATTERN="The selected row is empty ";
    public static String NO_MATCH_PATTERN_ID ="There's no match with the selected ID, please insert new one !";
    public static String ERROR_HAPPEN="Something went wrong, please try again ";
   private static Machine machine = null ;
   ArrayList<QueueOfSameItem> QueuesOfItems;
   Monitor monitor ; 
   Keypad keypad ; 
   double balance ;  //// this will be calculated in cent 
   boolean isActiveCoinSlot= false ; 
   boolean isActiveNotesSlot=false; 
   boolean isActiveCardSlot = false ; 

   private Machine() {
        QueuesOfItems = new ArrayList<QueueOfSameItem>();
        monitor = new Monitor () ;
        keypad = new Keypad();
    }
   public static Machine getInstance(){
       if (machine == null){
       return new Machine();
       }
       else return machine ; 
   }
    public ArrayList<QueueOfSameItem> getQueuesOfItems() {
        return QueuesOfItems;
    }
    public void addQueueOfItems(QueueOfSameItem queueOfItems) {
        QueuesOfItems.add(queueOfItems);
    }
    public Monitor getMonitor() {
        return monitor;
    }
    public Keypad getKeypad() {
        return keypad;
    }
    
    public String getFromSelectedQueue(String id ){
    Item readyToServe = null ; 
    for (QueueOfSameItem q : getQueuesOfItems()){
        if (q.getIdQueue().equals(id)){
          if (q.getSize()>=1)
          {   // There's one item or more, so we can buy 
         if (q.isActive==true){ // check if it's allowed 
         readyToServe = q.getItem(q.getSize()-1);// store the last inserted element 
         if(balance >= readyToServe.getPrice() ){ // check if there's enough money 
         // remove it from the queue and extract the price from the balance 
          q.remove(readyToServe);
          balance-=readyToServe.getPrice();
          getMonitor().setMessage(VALID_PURCHASE+balance);
          getMonitor().getMessage(); //// show the message on the screen 
          return VALID_PURCHASE;
        } //balance if statement
         else 
         {
            getMonitor().setMessage(NO_ENOGH_BALANCE);
            getMonitor().getMessage();
            return NO_ENOGH_BALANCE;
         }
         }//// Active if statement 
         else {
         getMonitor().setMessage(NOT_ACTIVE_QUEUE);
         getMonitor().getMessage();
         return NOT_ACTIVE_QUEUE;
         } 
         } // if there's size if statement 
          else {
          getMonitor().setMessage(EMPTY_SELECTED_PATTERN);
          getMonitor().getMessage();
          return EMPTY_SELECTED_PATTERN;
          }
        } // if there's a match id 
        else {
            getMonitor().setMessage(NO_MATCH_PATTERN_ID);
            getMonitor().getMessage();
            return NO_MATCH_PATTERN_ID;
        }
    }
            return ERROR_HAPPEN;
         
    }
    
    
    //--- Money Issue 
    
   public void addBalance(double amount){
       // chaeck which window is used ? 
       /// 1$ = 100 c and we ensert it as a coin 
       if (isActiveCoinSlot == true){
           if (amount==10 || amount ==20 || amount == 50 || amount ==100  ){
           balance+=amount ;
           }
           
           
       }
       else 
       if (isActiveCardSlot == true ){
           balance+=amount ; //// add the amount also in cent 
           
       }
       else 
       if ( isActiveNotesSlot == true ){
       if ( amount == 2000 || amount ==5000 ) /// 20$ or 50$ = > 2000c or 5000c 
       balance+=amount ;
       
       }
       
   /*
       
       •	Money Slots: the machine accepts money of the following types:
o	CoinSlot: There are four denominations: • 10c • 20c • 50c • $1 
o	CardSlot : all cards accepted
o	Notes Slot :20$ and 50$ only
o	Machine only accepts USD currency

       
       */
       /// validation 
       
   }

    public double getBalance() {
        return balance;
    }
   
    
    public void addCoin(double amount){
    isActiveCoinSlot = true ;
    addBalance(amount);
    }
    public void addCard(double amount){
    /// Assume the amount will be readed directly from the card 
    isActiveCardSlot = true; 
    addBalance(amount);
    };
    public void addNotes(double amount){
    isActiveNotesSlot=true; 
    addBalance(amount);
    };
   
    
       
}
