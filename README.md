NOTE: The README.md file made with the help of AI.  

This Java program implements a simple Automated Teller Machine (ATM) system. The system allows users to perform various banking operations, including depositing money, withdrawing money, checking account balance, viewing transaction history, and changing the PIN.

How it Works
The code consists of a single ATM class representing the ATM functionality and a Main class for initiating the program. Here's a brief overview of how the code works:

File Handling
PIN Storage: The user's PIN is stored in a file named "PIN.txt". If this file doesn't exist, it is created with a default PIN of "1234".

Balance Storage: The account balance is stored in a file named "Balance.txt". If this file doesn't exist, it is created with an initial balance of "0".

Transaction History: The details of each transaction are stored in a file named "Transaction_History.txt". This file is updated with each deposit or withdrawal operation.

Program Flow
The program starts by prompting the user to enter their PIN.
The entered PIN is checked against the stored PIN. If it matches, the user is granted access; otherwise, they are blocked.
Upon successful authentication, the user is presented with a menu offering options like depositing money, withdrawing money, checking balance, viewing transaction history, changing the PIN, and exiting the program.
Each menu option corresponds to a specific method in the ATM class.
File handling is used to read and update the PIN, account balance, and transaction history.

Usage
Enter your PIN when prompted to access the ATM system.
Choose from the available options in the menu to perform banking operations.
The program updates the relevant files with each transaction and displays the results.
Contributing
Contributions, bug reports, and feature requests are welcome. Feel free to open an issue or submit a pull request.

License
This project is licensed under the MIT License. See the LICENSE file for details.




