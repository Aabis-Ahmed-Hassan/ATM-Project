import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.time.LocalDateTime;

class ATM {
    
    double balance;
    private String pin; //making PIN code as String so that we can store it in file. 

    ATM(String PIN) {
        correctPIN(PIN); /*it will check whether the input PIN code matches the saved PIN code (file) or not. If it matches, then the 
        user will be moved to the menu method. */ 
    } //ATM Constructor End Bracket
    
    public void setPIN(String pin) {

        this.pin = pin;
        try {
            BufferedWriter b1 = new BufferedWriter(new FileWriter("PIN.txt"));
            b1.write(String.valueOf(pin));
            b1.close();
            System.out.println();
            System.out.println("Your PIN has updated successfully. ");
            System.out.println();
        } //try End Bracket 
        
        catch (IOException e ) {
            System.out.println();
            System.out.println("Unable to save your pin code. ");
            System.out.println();
            e.printStackTrace();
        } //catch End Bracket

    } //method setPIN End Bracket

    public String getPIN() {
          String pinFromFile = "1"; /*pinFromFile is initialized with value 123 to avoid error in if statement. 
        If we put pinFromFile in the try_catch method and the file is not found, then an error will occur because String pinFromFile will 
        have no value. It will not even be initialized because the try method will not be executed*/
        
            ifPINFileNotFound();

            try {
                FileReader f1 = new FileReader("PIN.txt");
            Scanner readFile = new Scanner(f1);
            pinFromFile = readFile.nextLine();
            readFile.close();
        } //try End Bracket 
        catch(IOException e) {
            e.printStackTrace();
        } //catch End Bracket



        return pinFromFile;
    } //Getter getPIN End Bracket

    public void menu() {
      
        loop:
        while(true) {
            
            System.out.println();
            System.out.println();
            System.out.println("Press 1 to Deposit Money. ");
            System.out.println("Press 2 to Withdraw Money. ");
            System.out.println("Press 3 to Show Balance. ");
            System.out.println("Press 4 for Transactions History. ");
            System.out.println("Press 5 to Change PIN. ");
            System.out.println("Press 6 to Exit. ");
            System.out.println();

            Scanner sc = new Scanner(System.in);
            System.out.print("Your Choice: ");
            String choice = sc.nextLine(); /*String is used instead of int because in this way, program will not be terminated if the user inputs
            not-integars like '@'*/
            
            switch(choice) {
            case "1": {
                System.out.println();
                System.out.print("Enter the amount to deposit: ");
                double addBalance = sc.nextDouble();
                depositMoney(addBalance);
                break;
            } //case "1" end bracket
            
            case "2": {
                System.out.println();
                System.out.print("Enter the amount ot withdraw: ");
                double withdrawCommand = sc.nextDouble();
                withdrawMoney(withdrawCommand);
                break;
            } //case 2 End Bracket

            case "3": {
                showBalance();
                break;
            } //case 3 End Bracket
            
            case "4": {
                transactionHistory();
                break;
            } //case 4 End Bracket

            case "5": {
                System.out.println();
                System.out.print("Enter new PIN: ");
                String newPIN = sc.nextLine();
                setPIN(newPIN);
                break;
            } //case 6 End Bracket

            default: {
                break loop;
            } //default End Bracket

        } //switch statement end bracket

        } //while loop end bracket

    } //menu method end bracket

    public void depositMoney(double depositMoney) {
 
        /*this method will firstly read the previous balance from balance.txt and then deposit the new money.
        After that, it will update the new balance in balance.txt */

        ifBalanceFileNotFound(); //if Balance.txt is not found, it will create it with 0 in it. 
        String balanceFromFileString = "";
        double balanceFromFileDouble;
       
        try {

            FileReader f1 = new FileReader("Balance.txt");
            Scanner sc = new Scanner(f1);
          
            balanceFromFileString = sc.nextLine(); //Balance taken from the file in a String
            balanceFromFileDouble = Double.parseDouble(balanceFromFileString); //converting String to double
            balanceFromFileDouble += depositMoney; //Depositing Money
            balanceFromFileString = String.valueOf(balanceFromFileDouble); //converting the updated balance to string so we can save it on file (in String form)
            System.out.println("Amout has been deposited successfully!");
            System.out.println("New balance is " + balanceFromFileDouble);

            LocalDateTime time = LocalDateTime.now();
            String transactionDetails = time + ": " + depositMoney + " was deposited. New balance: " + balanceFromFileDouble;
            saveTransactionHistoryInFile(transactionDetails);

        } //try End Bracket
        
        catch(FileNotFoundException e ) {
            System.out.println("Unable to deposit money. ");
            e.printStackTrace();
        } //catch End Bracket
        System.out.println();
        System.out.println();


        try {
            FileWriter fw1 = new  FileWriter("Balance.txt");
            fw1.write(balanceFromFileString);
            fw1.close();
        } //try End Bracket
        
        catch (IOException e) {
            e.printStackTrace();
        } //catch End Bracket

    } //depositMoney End Bracket

    public void withdrawMoney(double withDrawMoney) {

        ifBalanceFileNotFound(); //it will create a balance file with 0 in it so that the program will not make error. 

        double balance=0;
        String balanceFromFile;

        try { //It will get the current balance of the user from the file. 
            FileReader f1 = new FileReader("Balance.txt");
            Scanner sc = new Scanner(f1);
            balanceFromFile = sc.nextLine();
            balance = Double.parseDouble(balanceFromFile);
        } //try End Bracket
        
        catch(FileNotFoundException e) {
            System.out.println("Unable to check your balance. So, we're considering that your balance is 0. ");
            e.printStackTrace();
        } //catch End Bracket

        String balanceAfterWithdraw;
        balanceAfterWithdraw = String.valueOf(balance);

        if(balance>=withDrawMoney) {
            balance = balance - withDrawMoney;
            System.out.println();
            System.out.println(withDrawMoney + " has been withdrawn successfully.");
            System.out.println("Your remaining balance is " + balance);
            System.out.println();
            LocalDateTime time = LocalDateTime.now();
            String transactionDetails = time + ": " + withDrawMoney + " was withdrawn. New balance: " + balance;
            balanceAfterWithdraw = String.valueOf(balance);
            saveTransactionHistoryInFile(transactionDetails);
            
            try {
                FileWriter fw1 = new FileWriter("Balance.txt");
                fw1.write(balanceAfterWithdraw);
                fw1.close();
            } //try End Bracket 
            
            catch (Exception e) {
                e.printStackTrace();
            } //catch End Bracket
        } //if statement End Bracket
        else {
            System.out.println();
            System.out.println("Unable to withdraw money because of low balance. ");
            System.out.println();
        } //else statement End Bracket
    } //withdrawMoney End Bracket

    public void showBalance() {

        String balanceFromFile;
        ifBalanceFileNotFound(); //if Balance.txt is not found, it will create a the file with 0 in it. 
        
        try {
            FileReader f1 = new FileReader("Balance.txt");
            Scanner sc = new Scanner(f1);
            balanceFromFile = sc.nextLine();
            System.out.println("Your balance is " + balanceFromFile);
        } //try End Bracket 
        
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("An error occured while checking the balance. ");
        } //catch End Bracket

        System.out.println();
        System.out.println();
    } //showBalance End Bracket

    public void transactionHistory() {


        try {
            
            BufferedReader b1 = new BufferedReader(new FileReader("Transaction_History.txt"));
            String line;
          while((line=b1.readLine())!=null) {
                System.out.println(line);
            }
            b1.close();

          
            
        } //try End Bracket 
        
        catch (IOException e) {
            e.printStackTrace();
        } //catch End Bracket

    } //transactionHistory End Bracket

    public void ownerDetails() {

    } //ownerDetails End Bracket

    public void ifBalanceFileNotFound() { //if there is no file storing the balance, it will create a balance file with 0 balance in it to avoid erros. 
        

        try {
            FileReader f1 = new FileReader("Balance.txt");
        } //Outer try End Bracket

        catch (FileNotFoundException e) {

            try {
                FileWriter fw1 = new FileWriter("Balance.txt");
                fw1.write("0");
                fw1.close();        
            } //Inner try End Bracket


            catch (IOException f) {
                f.printStackTrace();
            } //Inner catch End Bracket

        } // Outer catch End Bracket

    } //ifBalanceFileNotFound End Bracket

    public void ifPINFileNotFound() { //If there is no file containing PIN Code, it will create a new file that stores 1234 and it will act as PIN Code. 
        try {
            FileReader f1 = new FileReader("PIN.txt");
        } catch (Exception e) {
            try {
                FileWriter fw1 = new FileWriter("PIN.txt");
                fw1.write("1234");
                fw1.close();
            } catch (IOException f) {
                f.printStackTrace();
            }
        }
    }

    public void saveTransactionHistoryInFile(String transactionDetail) {

        try {
            FileWriter f1 = new FileWriter("Transaction_History.txt",true);
            f1.write(transactionDetail + "\n");
            f1.close();
        } //try End Bracket

        catch (IOException e) {
            e.printStackTrace();
        } //catch End Bracket

    } //saveTransactionHistoryInFile End Bracket

    public void correctPIN(String inputPINByUser) {
      
        //If the input PIN code matches the saved pin code, the user will be moved to menu method.
        if(inputPINByUser.equals(getPIN())) {  //getPIN() takes the PIN Code from the saved file. 
            menu();
        }  //if End Bracket


        else {
            System.out.println();
            System.out.println("Incorrect PIN. You're blocked. ");
            System.out.println();
        } //else End Bracket
        
    } //getPIN End Bracket 



} //ATM Class End Bracket

public class Main{
    public static void main(String[] args) {

        System.out.println("By default, the PIN Code is 1234");
        System.out.println();

        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your PIN number: ");
        String inputPINByUser = scan.nextLine(); //User has to enter the PIN Code of his account. Failing to provide the PIN code will result in a ban. 
        ATM a1 = new ATM(inputPINByUser); 
        System.out.println();
        System.out.println("Termination Successful!");
    } //main function End Bracket
} //Main class End Bracket
