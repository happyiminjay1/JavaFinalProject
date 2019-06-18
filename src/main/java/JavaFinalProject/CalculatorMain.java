package JavaFinalProject;
public class CalculatorMain {

	 public static void main(String args[]){
		  Calculator cal = new Calculator();
		  cal.run(args);
		  cal.setTitle("Calculator");
		  cal.pack();
		  cal.setVisible(true);
		  System.out.println("start!");
		 }
}
