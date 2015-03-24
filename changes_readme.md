
Changes Done.

* AccountType and TransactionType enums are introduced
* Account is made abstract class and concrete classes for different type of accounts are introduced
* case statement for interest calculation is replaced with overriden function in concrete classes
* Additional features per instructions are implemented
* Statement creation is cleaned up and uses StringBuilder
* currentBalance is calculated and stored in account - this will give a better performance as number of transaction increases
* a Money data type is created to handle the amount in the accounts as money should not be negative.
* test cases added to improve coverage


Assumptions/ Not supported

Given the time constraint, Some assumptions have been made and some simplistic approach has been taken.
Following is a list of assumptions made.

* a new account identifier is used for account but the identifier generator is out of scope.
* a customer can have multiple accounts of same type
* interest is accrued daily but compounding is not implemented
* its assumed that is is single threaded. If we need to make it threadsafe - additional changes are required for thread safety



