# MiniJava
Mini-Java is a subset of Java. MiniJava compiler implement a compiler for the Mini-java
programming language.

# Rules
```
Goal --> Source EOF
Source --> ClassDeclarations MainClass
MainClass --> class Identifier { public static void main() { VarDeclarations Statements}}
ClassDeclarations --> ClassDeclaration ClassDeclarations | lambda
ClassDeclaration --> class Identifier Extension { FieldDeclarations MethodDeclarations }
Extension --> extends Identifier | lambda
FieldDeclarations --> FieldDeclaration FieldDeclarations | lambda
FieldDeclaration --> static Type Identifier ;
VarDeclarations --> VarDeclaration VarDeclarations | lambda
VarDeclaration --> Type Identifier ;
MethodDeclarations --> MethodDeclaration MethodDeclarations | lambda
MethodDeclaration --> public static Type Identifier ( Parameters ) { VarDeclarations Statements return GenExpression ; }
Parameters --> Type Identifier Parameter | lambda
Parameter --> , Type Identifier Parameter | lambda
Type --> boolean | int
Statements --> Statements Statement | lambda
Statement --> { Statements } | if ( GenExpression ) Statement else Statement | while ( GenExpression ) Statement | System.out.println ( GenExpression ) ; | Identifier = GenExpression ;
GenExpression --> Expression | RelExpression
Expression --> Expression + Term | Expression - Term | Term
Term --> Term * Factor | Factor
Factor --> ( Expression ) | Identifier | Identifier . Identifier | Identifier . Identifier ( Arguments ) | true | false | Integer
RelExpression --> RelExpression && RelTerm | RelTerm
RelTerm --> Expression == Expression | Expression < Expression
Arguments --> GenExpression Argument | lambda
Argument --> , GenExpression Argument | lambda
Identifier --> <IDENTIFIER_LITERAL>
Integer --> <INTEGER_LITERAL>
```
























Report of the Project


Seperate Query From Modifer Refactoring:

The change was applied to the getNextParam function because it was calling the getNextParameter function which was both returning a value and modifying the index.
So we added another Query method for getting the value and in the getNextParameter we call that, and then seperately modify the index.


///////////////////////////////////



Self Encapsulated Field:

The change was applied to klasses variable which was accessed directly in the methods. and this is how it was refactored:

1- Create a getter (and optional setter) for the field. They should be either protected or public.

2- Find all direct invocations of the field and replace them with getter and setter calls.

So a getter and setter function was written for it and then the getter was called instead of calling directly in methods.


///////////////////////////////////


Extract Method:

Problem:
Extract Method is used to combat long methods that contain too much code, making them hard to understand and maintain. In Parser class, the startParse method is quite long and contains multiple responsibilities.

Solution:
Refactor the startParse method in the Parser class by extracting smaller methods for different responsibilities. which is cut into these methods:initializeParser, parseTokens, handleShiftAction, handleReduceAction.


///////////////////////////////////


Replace Temp with Query:

Problem:
This refactoring technique is used to eliminate temporary variables that are only used to hold intermediate results. These temporaries can be replaced with a method call that performs the same computation.
In lexicalAnalyzer class, the tokenPattern temporary variable can be replaced with a method call.


Solution:
So we wrote a buildTokenPattern method to handle this temporary variable.
