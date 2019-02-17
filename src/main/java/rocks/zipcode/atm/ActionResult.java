package rocks.zipcode.atm;

/**
 * @author ZipCodeWilmington
 */
public class ActionResult<T> {

    private T data;
    private String errorMessage;

    /**
     * ActionResult is a constructor for an ActionResult that takes a data parameter
     * @param data - data to initialized in the ActionResult for a successful ActionResult
     */
    private ActionResult(T data) {
        this.data = data;
    }

    /**
     * ActionResult is a constructor for and ActionResult that takes a String error parameter
     * @param errorMessage - a String that is the error message for a failed ActionResult
     */
    private ActionResult(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * getData is a getter for the ActionResult's data field
     * @return - Data in the ActionResult's data field
     */
    public T getData() {
        return data;
    }

    /**
     * getErrorMessage is a getter for the ActionResult's errorMessage
     * @return a String that is the error message for the ActionResult
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * isSuccess is a method checks if the ActionResult's data is not null, and if it is not, the action is successful
     * @return a boolean that is true if data is not null
     */
    public boolean isSuccess() {
        return data != null;
    }

    /**
     * success is a method that returns a new ActionResult with the new data
     * @param data - data to be initialized in the new ActionResult
     * @return a new ActionResult with the new data
     */
    public static <E> ActionResult<E> success(E data) {
        return new ActionResult<E>(data);
    }

    /**
     * fail is a method that returns an ActionResult with an error message
     * @param errorMessage - the String that is the error message for the ActionResult
     * @return an ActionResult with the new error message
     */
    public static <E> ActionResult<E> fail(String errorMessage) {
        return new ActionResult<E>(errorMessage);
    }



}
