package parser;

public class Action {
    public Act action;
    //if action = shift : number is state
    //if action = reduce : number is number of rule

    public Action(Act action) {
        this.action = action;
    }

    public String toString() {
        return action.toString();
    }
}


//enum act {
//    shift, reduce, accept
//}

class Act {
    public int number;

    public Act(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return super.toString() + number;
    }
}


class Accept extends Act{
    public Accept(int number) {
        super(number);
    }

    @Override
    public String toString() {
        return "acc";
    }
}


class Shift extends Act{
    public Shift(int number) {
        super(number);
    }

    @Override
    public String toString() {
        return "s" + number;
    }
}


class Reduce extends Act{
    public Reduce(int number) {
        super(number);
    }

    @Override
    public String toString() {
        return "r" + number;
    }
}