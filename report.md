# Task distribution by P.O.

![kan](https://github.com/kiarashk8128/SWLab-HW8/assets/82291200/c3419927-7421-4255-8383-370ac969a288)

![kan](https://github.com/kiarashk8128/SWLab-HW8/assets/82291200/e0ef5c7c-4908-4d48-9a4e-66b3d1104294)


# مراحل بازآرایی

## دو مورد Facade

## One Instance of Polymorphism به جای شرط

## One Instance of Separate Query From Modifier

The change was applied to the getNextParam function because it was calling the getNextParameter function which was both returning a value and modifying the index.
So we added another Query method for getting the value and in the getNextParameter we call that, and then seperately modify the index.

## One Instance of Self Encapsulated Field


The change was applied to klasses variable which was accessed directly in the methods. and this is how it was refactored:

1- Create a getter (and optional setter) for the field. They should be either protected or public.

2- Find all direct invocations of the field and replace them with getter and setter calls.

So a getter and setter function was written for it and then the getter was called instead of calling directly in methods.

# Other Refactoring Techniques, Extract Method:

Problem:
Extract Method is used to combat long methods that contain too much code, making them hard to understand and maintain. In Parser class, the startParse method is quite long and contains multiple responsibilities.

Solution:
Refactor the startParse method in the Parser class by extracting smaller methods for different responsibilities. which is cut into these methods:initializeParser, parseTokens, handleShiftAction, handleReduceAction.

# Other Refactoring Techniques, Replace Temp with Query:


Problem:
This refactoring technique is used to eliminate temporary variables that are only used to hold intermediate results. These temporaries can be replaced with a method call that performs the same computation.
In lexicalAnalyzer class, the tokenPattern temporary variable can be replaced with a method call.


Solution:
So we wrote a buildTokenPattern method to handle this temporary variable.


## پلاگین formatter



# پرسش‌ها

## پرسش 1

## پرسش 2

## پرسش 3

## پرسش 4

## پرسش 5
