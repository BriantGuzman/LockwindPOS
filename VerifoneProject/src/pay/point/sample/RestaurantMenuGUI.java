package pay.point.sample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


import java.awt.image.BufferedImage;
import javax.swing.Icon;

import java.io.File;
import javax.imageio.ImageIO;

public class RestaurantMenuGUI extends JFrame {
    
    private JPanel menuPanel;
    
    private JButton orderButton;
    private JLabel totalLabel;
    private double totalCost;
    private Icon icon;
    private JButton imageButton;

    private BufferedImage image;
    private String imagePath;
    private MenuItem menuItem;

    private SpringLayout panelLayout;

    private int positionX;
    private int positionY;

    public void buildImageButton(String imagePath) {
        try {

            imagePath = "014100046424.jpg";
            image = ImageIO.read(new File(imagePath));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        imageButton.setPreferredSize(new Dimension(120, 120)); // set preferred size to make it a square
        imageButton.setBorderPainted(false); // remove default border
        
        //imageButton.prepareImage(image,60,60);

        // imageButton.createImage(60, 60);

        //imageButton.getParent().;
        //drawImage(image, 0, 0, getWidth(), getHeight(), this);

    }

    /* 
    @Override
    protected void paintComponent(Graphics g) {
        imageButton..paintComponent(g);
        imageButton.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
*/

    public RestaurantMenuGUI() {
        image = null;
        orderButton = null;

        positionX = 0;
        positionY = 0;
	  
        RestaurantMenuGUI test = new RestaurantMenuGUI("directory.txt");

    }

    public RestaurantMenuGUI(String buttonDirectory) {

        super("Restaurant Menu");
        
        // Close operaetion action
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create a panel to hold the menu items
        menuPanel       = new JPanel();
        panelLayout    	= new SpringLayout();
        menuPanel		.setLayout(panelLayout);
        totalLabel = new JLabel("Total: $0.00");

        // menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        // Create a button to show the item
        this.createButton();
        
        this.setLayoutGrid();
        this.addItems();        
        this.completeBuild();

        // Create a label to display the total cost of the order
        //totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add the menu panel, order button, and total label to the JFrame

        
        

        
    }
    
    public void createButton()
    {
        //icon = new ImageIcon
        
        icon = new ImageIcon("014100046424-60.jpg");
        

        imageButton = new JButton(icon);
        imageButton.setPreferredSize(new Dimension(60,60));
        imageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Item Selected");
                JOptionPane.showMessageDialog(null,"hello world");
            }
        });
        imageButton.setEnabled(false);
        

        // Create a button to submit the order

        orderButton = new JButton("Place Order");
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeOrder();
            }
        });
        orderButton.setEnabled(false);
        
    }
    public void addItems(){
                        // Add some example menu items to the panel        
                        addItem("Hamburger", 5.99);
                        addItem("Cheeseburger", 6.49);
                        addItem("Hot Dog", 3.99);
                        addItem("French Fries", 2.99);
                        addItem("Onion Rings", 3.49);
                        addItem("Soda", 1.99);
    }
    private void addItem(String name, double price) {
        


        JLabel itemLabel = new JLabel(name + " - $" + price);
        itemLabel.setFont(new Font("Arial", Font.BOLD, 18));


        positionX += 30;
        positionY = 30;
        
        panelLayout.putConstraint(panelLayout.NORTH, itemLabel,positionX+100,panelLayout.NORTH, menuPanel);
        panelLayout.putConstraint(panelLayout.WEST, itemLabel,positionY+100,panelLayout.WEST, menuPanel);

//        itemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        menuPanel.add(itemLabel);
        
        JCheckBox checkBox = new JCheckBox();
        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTotal(price, checkBox.isSelected());
            }
        });
        checkBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelLayout.putConstraint(panelLayout.NORTH, checkBox,positionX+100,panelLayout.NORTH, menuPanel);
        panelLayout.putConstraint(panelLayout.WEST, checkBox,positionY,panelLayout.WEST, menuPanel);
        menuPanel.add(checkBox);
    }
    
    private void updateTotal(double price, boolean checked) {
        if (checked) {
            totalCost += price;
        } else {
            totalCost -= price;
        }
        totalLabel.setText("Total: $" + String.format("%.2f", totalCost));
        orderButton.setEnabled(totalCost > 0);
    }
    
public void setLayoutGrid(){
    
    panelLayout.putConstraint(panelLayout.NORTH, imageButton,20,panelLayout.NORTH, menuPanel);
    panelLayout.putConstraint(panelLayout.WEST, imageButton,90,panelLayout.WEST, menuPanel);
 
    panelLayout.putConstraint(panelLayout.SOUTH, totalLabel,-5,panelLayout.SOUTH, menuPanel);
    panelLayout.putConstraint(panelLayout.WEST, totalLabel,60,panelLayout.WEST, menuPanel);
    
    panelLayout.putConstraint(panelLayout.SOUTH, orderButton,0,panelLayout.SOUTH, menuPanel);
    panelLayout.putConstraint(panelLayout.WEST, orderButton,150,panelLayout.WEST, menuPanel);
    
}





    private void placeOrder() {
        JOptionPane.showMessageDialog(this, "Your order has been placed! Total: $" + String.format("%.2f", totalCost));
        totalCost = 0;
        totalLabel.setText("Total: $0.00");
        orderButton.setEnabled(false);
        for (Component c : menuPanel.getComponents()) {
            if (c instanceof JCheckBox) {
                ((JCheckBox) c).setSelected(false);
            }
        }
    }


    public void completeBuild()
    {
        menuPanel.add(totalLabel);
        menuPanel.add(imageButton);
        menuPanel.add(orderButton);
        
        this.setLayout(new BorderLayout());
        add(menuPanel, BorderLayout.CENTER);
        
        //add(totalLabel, BorderLayout.NORTH);
        //add(imageButton, BorderLayout.EAST);
        //add(orderButton, BorderLayout.SOUTH);
        
        // Set the size and make the JFrame visible
        setSize(640, 360);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new RestaurantMenuGUI();
    }
}