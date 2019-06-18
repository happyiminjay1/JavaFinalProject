package JavaFinalProject;
public class OperationException extends Exception{
		
	public OperationException()
	{
		super ("Invalid Operation.");
	}
	public OperationException(String message)		
	{
		super (message);
	}

}

