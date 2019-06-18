package JavaFinalProject;
public class Data<T> {
    private T firstInput;
    private T secondInput;
    private T result;
    private String operator;
    Data(T firstInput,String operator,T secondInput, T result)
	{
		this.firstInput = firstInput;
		this.operator = operator;
		this.secondInput = secondInput;
		this.result = result;
	}

    public T getFirstInput() {
		return firstInput;
	}
    
	public T getSecondInput() {
		return secondInput;
	}

	public String getOperator() {
		return operator;
	}

	public T getResult() {
		return result;
	}
}

//  Data<Float> data = new Data<Float>(0F,null,0F);
//Stack<Data> dataRecord = new Stack<Data>();