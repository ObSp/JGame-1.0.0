package JGamePackage.lib.CustomError;

public class CustomError {
    private String message;
    private int errorType;
    private String ansiColor;
    private int jumpOverElements;

    public static final int ERROR = 0;
    public static final int WARNING = 1;

    /** Constructs a CustomError based on the given parameters.
     * Note that any occurences of the regex "%s" will be replaced with the corresponding 
     * insertions when this.Throw(String[]) is called.
     * 
     * @param errorMessage
     * @param errorType
     * @param stackTraceElementsToJumpOver
     */
    public CustomError(String errorMessage, int errorType, int stackTraceElementsToJumpOver) {
        this.message = errorMessage;
        this.errorType = errorType;
        this.jumpOverElements = stackTraceElementsToJumpOver;

        if (this.errorType == CustomError.ERROR) {
            ansiColor = "\u001B[31m";
        } else if (this.errorType == CustomError.WARNING) {
            ansiColor = "\033[38;2;255;180;0m";
        }
    }

    public void Throw(String[] insertions) {
        for (String s : insertions) {
            message = message.replaceFirst("%s", s);
        }

        System.out.println((char) 27 +"[4m"+ ansiColor+message+"\033[0m");
        System.out.print(ansiColor);
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        for (int i = jumpOverElements+1; i < stackTrace.length; i++) {
            System.out.println("    at "+stackTrace[i]);
        }
        System.out.print("\033[0m");
        if (errorType == ERROR) {
            System.exit(0);
        }
    }

    public void Throw(){
        this.Throw(new String[0]);
    }
}
