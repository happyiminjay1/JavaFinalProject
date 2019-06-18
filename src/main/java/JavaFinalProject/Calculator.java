package JavaFinalProject;
import javax.swing.*;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Stack;
public class Calculator extends JFrame implements ActionListener{

final int MAX_LENGTH_OF_SERIAL = 10;

boolean firstInputEntered = false;
boolean secondInputEntered = false;
boolean operatorEntered = false;
boolean displayResultCheck = false;
boolean checkDoubleEqualEnteredInFirst = false;
boolean ERROR_MODE = false;
//for calculating
String operator = null;
float firstInput = 0;
float secondInput = 0;
float result = 0;

String outputFileName = null;
boolean help;

private JPanel bodyPanel, btnPanel, serialPanel;
private JLabel serial;
private JButton buttons[];
GridBagConstraints gbc = new GridBagConstraints();
GridBagLayout gbl = new GridBagLayout();

Stack<Data> dataRecord = new Stack<Data>();
Utils utils;

 public Calculator(){
  
  //stackHistory.peak
  //push,pop,search
  bodyPanel = new JPanel();
  btnPanel = new JPanel();
  serialPanel = new JPanel();
  buttons = new JButton[21];
  serial = new JLabel("0", JLabel.RIGHT);

  //serial 설정

  serial.setBackground(Color.WHITE);
  serial.setOpaque(true);

  for (int i=0 ; i<=9 ; i++){
   buttons[i] = new JButton(String.valueOf(i));
  }

  buttons[10] = new JButton("00");
  buttons[11] = new JButton(".");
  buttons[12] = new JButton("+");
  buttons[13] = new JButton("-");
  buttons[14] = new JButton("*");
  buttons[15] = new JButton("/");
  buttons[16] = new JButton("^");
  buttons[17] = new JButton("C");
  buttons[18] = new JButton("=");
  buttons[19] = new JButton("file save");
  buttons[20] = new JButton("←");


  for(int i=0 ; i<buttons.length ; i++){
   if(i<=11){
    buttons[i].setForeground(Color.blue); //숫자 버튼들
   }
   else if(i>11 && i<=18){
    buttons[i].setForeground(Color.red); //연산자 버튼들
   }
  }

  //패널에 숫자버튼 및 연산자 버튼 배치
  gbc.fill = GridBagConstraints.BOTH;
  gbc.weightx = 0.1;
  gbc.weighty = 0.1;


  btnPanel.setLayout(gbl);
  serialPanel.setLayout(gbl);

  make(buttons[0],1,3,1,1);
  make(buttons[1],0,0,1,1);
  make(buttons[2],1,0,1,1);
  make(buttons[3],2,0,1,1);
  make(buttons[4],0,1,1,1);
  make(buttons[5],1,1,1,1);
  make(buttons[6],2,1,1,1);
  make(buttons[7],0,2,1,1);
  make(buttons[8],1,2,1,1);
  make(buttons[9],2,2,1,1);
  make(buttons[10],0,3,1,1);
  make(buttons[11],2,3,1,1);
  make(buttons[12],3,0,1,1);
  make(buttons[13],3,1,1,1);
  make(buttons[14],3,2,1,1);
  make(buttons[15],3,3,1,1);
  make(buttons[16],4,0,1,1);
  make(buttons[17],4,1,1,1);
  make(buttons[18],4,2,1,2);
  make(buttons[19],0,0,1,1);
  make(buttons[20],1,0,1,1);

  for(int i = 0; i<19; i++)
  {
	  btnPanel.add(buttons[i]);
  }

  for(int i = 19; i<=20 ; i++)
  {
	  serialPanel.add(buttons[i]);
  }


  setSize(500, 500); // 프레임 사이즈 지정

  // 각 컴포넌트를 프레임에 추가
  bodyPanel.setLayout(new BorderLayout());
  bodyPanel.add(serialPanel, BorderLayout.NORTH);
  bodyPanel.add(btnPanel, BorderLayout.SOUTH);

  getContentPane().add(serial, BorderLayout.NORTH);
  getContentPane().add(bodyPanel,BorderLayout.SOUTH);


  requestFocus();

  for(int i=0 ; i<buttons.length ; i++){
   buttons[i].addActionListener(this);
  }

  addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent e){
	     System.exit(0);
	    }
	   }
  );
 }

@Override
public void actionPerformed(ActionEvent e) {
	for(int i = 0; i<buttons.length; i++)
	{
		if(e.getSource()==buttons[i])
		{
			if(i<=10)
			{
				if(operatorEntered==true)
				{
					if(firstInputEntered==false) firstInput = result;
					secondInputEntered = true;
				}
				else
					firstInputEntered = true;
				if(displayResultCheck==true)
				{
					serial.setText(" ");
					displayResultCheck = false;
				}
				addIndexToDisplay(i);
			}
			else if(i==11)
				addPoint();
			else if(i>=12&&i<=16)
			{
				if(checkDoubleEqualEnteredInFirst==true)
				{
					firstInputEntered = true;
					firstInput = getSerialIntoFloat();
					serial.setText(" ");
					checkDoubleEqualEnteredInFirst = false;
					operatorEntered=true;
				}
				else if(operatorEntered==false)
				{
					operatorEntered=true;
					if(firstInputEntered == true)
						{
							firstInput = getSerialIntoFloat();
							serial.setText(" ");
						}
				}
				else
				{
					if(secondInputEntered==true)
					{
						if(firstInputEntered=false)
							firstInput = result;
						secondInput = getSerialIntoFloat();
						serial.setText(" ");
						calculate();
						if(!ERROR_MODE==true) 
						{
							displayResult(result);
						}
						else ERROR_MODE = false;
						displayResultCheck = true;
						secondInputEntered = false;
					}
				}
				if(i==12) operator = "+";
				else if(i==13) operator = "-";
				else if(i==14) operator = "*";
				else if(i==15) operator = "/";
				else if(i==16) operator = "^";
			}
			else if(i==17)
			{
				init();
				serial.setText("0");
			}
			else if(i==18)
			{
				if(operatorEntered==true)
				{
					if(firstInputEntered==true&&secondInputEntered==true) secondInput = getSerialIntoFloat();
					else if(firstInputEntered==true&&secondInputEntered==false)
						secondInput = firstInput;
					else if(firstInputEntered==false&&secondInputEntered==false)
					{
						secondInput = result;
						firstInput = result;
					}
					else if(firstInputEntered==false&&secondInputEntered==true)
					{
						secondInput = getSerialIntoFloat();
					}
				}
				else firstInput = result;
				calculate();
				if(!ERROR_MODE==true) 
				{
					displayResult(result);
				}
				else ERROR_MODE = false;
				displayResultCheck = true;
				halfInit();
			}
			else if(i==19)
				utils.writeDataIntoFile(dataRecord);
			else 
				backSpace();
				
		}
	}
}

public void make(JComponent c, int xpos, int ypos, int width, int height)
{
	gbc.gridx = xpos;
	gbc.gridy = ypos;
	gbc.gridwidth = width;
	gbc.gridheight = height;

	gbl.setConstraints(c,gbc);
}

public void addIndexToDisplay(int i)
{
	String str = serial.getText();
	if(str.indexOf("0") == 0){
		   str = str.substring(1);
	}
	if(i==10 && str.length()<MAX_LENGTH_OF_SERIAL-1)
	{
		str = str + "0" + "0";
	}
	else if(str.length()<MAX_LENGTH_OF_SERIAL)
		str = str + i;
	serial.setText(str);
}

public void addPoint()
{
	String str = serial.getText();
	if(str.indexOf(".")<0)
	{
		str = str + "." ;
		serial.setText(str);
	}
}

public void calculate()
{
	if(operator == "+")
		result = firstInput + secondInput;
	else if(operator == "-")
		result = firstInput - secondInput;
	else if(operator == "*")
		result = firstInput * secondInput;
	else if(operator == "/")
		try {
			if(secondInput==0) throw new OperationException("0 can not be devided");
			result = firstInput / secondInput;
		} catch (OperationException e) {
			System.out.println(e.getMessage());
			serial.setText("invalid operation!");
			init();
			ERROR_MODE = true;
		}
	else if(operator == null)
	{
		result = getSerialIntoFloat();
		checkDoubleEqualEnteredInFirst = true;
	}
	else
	{	
		try {
			if(secondInput%1!=0) throw new OperationException("power is only available to natural numbers");
			ArrayList<powerThread> powerRunners = new ArrayList<powerThread>();
			long howmanythread = 0;
			for(; howmanythread<(int)(secondInput/10); howmanythread++) {
				powerRunners.add(new powerThread(1,10,firstInput));
			}
			powerRunners.add(new powerThread(1,(int)secondInput-howmanythread*10,firstInput));

			ArrayList<Thread> threadsForPower = new ArrayList<Thread>();

			for(powerThread runner: powerRunners) {
				Thread thread = new Thread(runner);
				thread.start();
				threadsForPower.add(thread);
			}
			System.out.println(howmanythread);
			for(int i = 0; i<howmanythread+1 ; i++)
			{
				try {
					threadsForPower.get(i).join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			long grandTotal = 1;
			for(powerThread runner: powerRunners) {
				grandTotal *= runner.totalPower;
			}
			result = grandTotal;
		}
		catch (OperationException e) {
			System.out.println(e.getMessage());
			serial.setText("invalid operation!");
			init();
			ERROR_MODE = true;
		}
	}
	addDataRecord(firstInput,operator,secondInput,result);
		//make thread operation for '^';
}

public void init()
{
	firstInputEntered = false;
	secondInputEntered = false;
	operatorEntered = false;
	operator = null;
	firstInput = 0;
	secondInput = 0;
	result = 0;
}

public void halfInit()
{
	firstInputEntered = false;
	secondInputEntered = false;
	operatorEntered = false;
}

public float getSerialIntoFloat()
{
	return Float.parseFloat(serial.getText());
}

public void displayResult(float result)
{
	if(result%1!=0)
	serial.setText(Float.toString(result));
	else
	serial.setText(Integer.toString((int)result));
}

public void addDataRecord(float firstInput,String operator,float secondInput, float result)
{
	Data<Float> data = new Data<Float>(firstInput,operator,secondInput,result);
	dataRecord.push(data);
	/*Data<Float> dataGet = dataRecord.pop();
	System.out.println(dataGet.getFirstInput() + dataGet.getOperator() + dataGet.getSecondInput()+ "=" + dataGet.getResult());*/
}

public void backSpace()
{
	if(displayResultCheck==false)
	{
	serial.setText(serial.getText().substring(0,serial.getText().length()-1));
	if(serial.getText().length() < 1)
		serial.setText("0");
	}
}

public void run(String[] args) {
	 Options options = createOptions();
	 
	 if(parseOptions(options,args)) {
		 if(help) {
			 printHelp(options);
			 return;
		 } 
	 }
	 
	 utils = new Utils(outputFileName);
}

private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();
		
		try {
			CommandLine cmd = parser.parse(options, args);

			outputFileName = cmd.getOptionValue("o");
			help = cmd.hasOption("h");
			
		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
		
	}
private Options createOptions() {
		Options options = new Options();

		options.addOption(Option.builder("o").longOpt("output")
				.desc("Set an output file path for calculator")
				.hasArg()
				.argName("Output path")
				.required() 
				.build());
		
		options.addOption(Option.builder("h").longOpt("help")
				.argName("Help")
		        .desc("Show a Help page")
		        .build());
		
		return options;
	}
	
	private void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		String header = "Calculator";
		String footer ="";
		formatter.printHelp("Calculator", header, options, footer, true);
	}


}
