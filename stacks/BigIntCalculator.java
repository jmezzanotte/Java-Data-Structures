
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.text.NumberFormat;

/**
 * Application for GStack in Project 4. Uses GStack and StackException. 
 * The logic for implementing the stacks to add large integers is contained 
 * in the method addBigIntegers.
 * @author JohnMezzanotte
 * Contact: jmezzan1@my.smccd.edu
 * BigIntCalculator.java
 * CIS 256 JAVA
 * Date: 3-22-2016
 */
public class BigIntCalculator extends JFrame {
   
    private JPanel parentPanel, inputPanel, inputTextPanel, fieldPanelOne, 
            fieldPanelTwo, inputButtonPanel, digitCountPanel;
    private JPanel outputParentPanel;
    private JLabel labelX, labelY, numberOfDigits;
    private JTextArea output;
    private JButton calculateBtn, clearBtn;
    private JTextArea inputFieldOne, inputFieldTwo;
    private Container myCP;
    
    private final int MAIN_FRAME_HEIGHT = 600; 
    private final int MAIN_FRAME_WIDTH = 700; 
    private final int OUTPUT_HEIGHT = 150; 
    private final int OUTPUT_WIDTH = 600; 
    private final int FIELD_HEIGHT = 75; 
    private final int FIELD_WIDTH = 400; 
    private final String DIGITS_START_VAL = "Digits: ";
    
    public BigIntCalculator(){
        
        super("Big Integer Calculator");
       
        setSize(MAIN_FRAME_WIDTH,MAIN_FRAME_HEIGHT);
        setResizable(false); 
        
        myCP = getContentPane();
        
        parentPanel = new JPanel(); 
        parentPanel.setBackground(Color.LIGHT_GRAY);
        parentPanel.setBorder(new EmptyBorder(20,20,20,20));
        parentPanel.setLayout(new BoxLayout(parentPanel,BoxLayout.Y_AXIS));
       
        outputParentPanel = new JPanel();
        outputParentPanel.setBorder(borderWithPadding(10,20,20, 20));
        outputParentPanel.setLayout(new BoxLayout(outputParentPanel,BoxLayout.Y_AXIS));
        
       
        // Setup output window
        output = new JTextArea(); 
        output.setPreferredSize(new Dimension(OUTPUT_WIDTH,OUTPUT_HEIGHT));
        output.setLineWrap(true);
        output.setOpaque(true);
        output.setBackground(Color.WHITE);
        output.setBorder(borderWithPadding(10,10,10,10));
        output.setEditable(false);
        
        digitCountPanel= new JPanel(); 
        digitCountPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        numberOfDigits = new JLabel(DIGITS_START_VAL); 
        numberOfDigits.setBackground(Color.DARK_GRAY); 
        numberOfDigits.setOpaque(true);
        numberOfDigits.setPreferredSize(new Dimension(300,20)); 
        numberOfDigits.setBorder(borderWithPadding(10,10,10,10));
        numberOfDigits.setForeground(Color.WHITE);
        
        digitCountPanel.add(numberOfDigits);
        
        // Setup parent input panel
        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createEtchedBorder());
        inputPanel.add(Box.createRigidArea(new Dimension(10,20)));
        
        // Setup panel for text boxes
        inputTextPanel = new JPanel(); 
        inputTextPanel.setLayout(new BoxLayout(inputTextPanel, BoxLayout.Y_AXIS));
        
        fieldPanelOne = new JPanel(); 
        fieldPanelOne.setLayout(new FlowLayout());
        fieldPanelTwo = new JPanel(); 
        fieldPanelTwo.setLayout(new FlowLayout());
       
        // Create TextBoxes for data entry
        inputFieldOne = new JTextArea();
        inputFieldTwo = new JTextArea(); 
        inputFieldSetup(inputFieldOne, FIELD_WIDTH, FIELD_HEIGHT);
        inputFieldSetup(inputFieldTwo, FIELD_WIDTH, FIELD_HEIGHT);
       
        
        // Create a label for some GUI Styling
        labelX = new JLabel("X");
        labelX.setBorder(borderWithPadding(10,10,10,10));
        
        labelY = new JLabel("Y"); 
        labelY.setBorder(borderWithPadding(10,10,10,10));
        
        fieldPanelOne.add(labelX);
        fieldPanelOne.add(inputFieldOne); 
        fieldPanelTwo.add(labelY); 
        fieldPanelTwo.add(inputFieldTwo);
       
        // Setup panel for buttons
        inputButtonPanel = new JPanel(); 
        inputButtonPanel.setLayout(new BoxLayout(inputButtonPanel, 
                BoxLayout.X_AXIS));
        inputButtonPanel.setBorder(new EmptyBorder(0,10,10,10));
        
        // Create buttons
        calculateBtn = new JButton("X + Y");
        calculateBtn.setBorder(borderWithPadding(10,10,10,10));
        calculateBtn.addActionListener(new ButtonClick());
        
        clearBtn = new JButton("  CLEAR  ");
        clearBtn.setBorder(borderWithPadding(10,10,10,10));
        clearBtn.addActionListener(new ButtonClick());
        
        // Add Components to content pane
        outputParentPanel.add(Box.createRigidArea(new Dimension(55, 15)));
        outputParentPanel.add(output);
        outputParentPanel.add(digitCountPanel);

        inputTextPanel.add(fieldPanelOne);
        inputTextPanel.add(fieldPanelTwo);
        inputButtonPanel.add(calculateBtn);
        inputButtonPanel.add(clearBtn);
        
        inputPanel.add(inputTextPanel); 
        inputPanel.add(inputButtonPanel);
        
        parentPanel.add(outputParentPanel);
        parentPanel.add(inputPanel);
        myCP.add(parentPanel);

        setVisible(true);
    }
    
    /**
     * This is a helper method for setting panel and component borders 
     * @param top 
     * @param left
     * @param bottom
     * @param right
     * @return returns the border object with specifications set
     */
    private CompoundBorder borderWithPadding(int top, int left, 
            int bottom, int right){
        EmptyBorder padding = new EmptyBorder(top, left, bottom, right); 
        EtchedBorder etched = new EtchedBorder();
        
        return  new CompoundBorder(etched, padding);
        
    } 
    
    /**
     * Helper method to setup JTextAreas that will capture user input
     * @param field 
     */
    private void inputFieldSetup(JTextArea field, int width, int height){
        
        field.setLineWrap(true); 
        field.setOpaque(true);
        field.setBackground(Color.WHITE);
        field.setPreferredSize(new Dimension(width,height));
        field.setBorder(borderWithPadding(10,10,10,10));
 
    
    }
   
    private class ButtonClick implements ActionListener{
        
        private GStack<Character> stack1; 
        private GStack<Character> stack2; 
        private GStack<Integer> sumStack; 
        private int columnResult;
        
        public ButtonClick(){
            stack1 = new GStack<>();
            stack2 = new GStack<>();
            sumStack = new GStack<>();
            columnResult = 0; 
        }
        
        @Override
        public void actionPerformed(ActionEvent event){
            
            // Determine which button triggered the button Click event
            if(event.getSource() == clearBtn){
                output.setText("");
                inputFieldOne.setText("");
                inputFieldTwo.setText("");
                numberOfDigits.setText(DIGITS_START_VAL);
            }else if(event.getSource() == calculateBtn){
                addBigIntegers();
            }
        } // end actionPerformed
        
        /**
         * This method contains the logic for adding big integers. Uses three
         * stacks to implement the addition. 
         */
        private void addBigIntegers(){
                    
            String textBoxOne = inputFieldOne.getText();
            String textBoxTwo = inputFieldTwo.getText();
            columnResult = 0; // ensures that columnResult is always reset to 0

            try{
                for(int i = 0; i < textBoxOne.length(); i++){
                    
                    if(Character.isDigit(textBoxOne.charAt(i))){
                        stack1.push(textBoxOne.charAt(i));
                        // Clear the output window
                        output.setForeground(Color.BLACK); 
                        output.setText("");
                    }else{
                        throw new IllegalArgumentException("Input in box 1 " + 
                            "must be an integer.");
                    }// end if 
                } // end for

                for(int i = 0; i < textBoxTwo.length(); i++){
                    if(Character.isDigit(textBoxTwo.charAt(i))){
                        stack2.push(textBoxTwo.charAt(i));
                    }else{
                        throw new IllegalArgumentException("Input in box 2 " + 
                               "must be an integer");
                    }// end if
                } // end for

                while(!stack1.isEmpty() || !stack2.isEmpty()){

                    int digit1 = 0; 
                    int digit2 = 0;
                    
                    if(!stack1.isEmpty()){
                        digit1 = Character.getNumericValue(stack1.pop());
                    } // end if 

                    if(!stack2.isEmpty()){
                        digit2 = Character.getNumericValue(stack2.pop());
                    } // end if 

                    columnResult += digit1 + digit2;

                    sumStack.push(columnResult % 10);

                    if(columnResult > 9){
                        columnResult = findTensSpot(columnResult);
                    }else{
                        columnResult = 0;
                    } // end if          
                } // end while

                if(columnResult != 0){
                    sumStack.push(columnResult);
                }

                String sum = "";

                while(!sumStack.isEmpty()){
                    sum += Integer.toString(sumStack.pop());
                }
                int digitCount = sum.length();
                output.setText(sum);
                numberOfDigits.setText(DIGITS_START_VAL + Integer.toString(digitCount));

            }catch (StackException e){
                System.out.println(e.getMessage());
                output.setText(e.getMessage());
                output.setForeground(Color.RED);
                numberOfDigits.setText(DIGITS_START_VAL + "Error");
            }catch (IllegalArgumentException e){
                e.printStackTrace();
                output.setText(e.getMessage());
                output.setForeground(Color.RED);
                numberOfDigits.setText(DIGITS_START_VAL + "Error");
                
            }finally{
                // Clear the stacks after each run. I was encountering 
                // Weird output after catching errors. Clearing the stacks 
                // resolved the issue.
                stack1.popAll();
                stack2.popAll();
                sumStack.popAll();
            }// End Try
        } // End AddBigIntegers
        
        /**
         * Helper method the execute the formula for isolating the number in 
         * the ten's place of a given number.
         * @param num
         * @return returns the value that is in the ten's spot of a number
         */
        private int findTensSpot(int num){
            return ((num % 100) - (num % 10)) / 10;
        }
        
    } // end ButtonClick
    
    public static void main(String[] args){
        
        BigIntCalculator myApp = new BigIntCalculator();
        myApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    } // end main
    
} // End BigIntCalculator
