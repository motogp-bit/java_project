package necessities;

public class Errors {
    public static class customException extends Exception {
        public customException(String errorMessage){
            super(errorMessage);
        }
    }
}
