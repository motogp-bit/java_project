package necessities;

public class Errors {
    //Print custom error messages
    public static class customException extends Exception {
        public customException(String errorMessage){
            super(errorMessage);
        }
    }
}
