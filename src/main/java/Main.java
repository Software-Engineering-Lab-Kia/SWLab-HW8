import Facade.CompilerFacade;

public class Main {
    public static void main(String[] args) {
        CompilerFacade compilerFacade = new CompilerFacade();
        compilerFacade.compile("src/main/resources/code");
    }
}
