import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.Icon;


public class RestaurantMenuImage extends JButton {

    private BufferedImage image;
    private JButton orderButton;
    

    public RestaurantMenuImage(){
        image = null;
        orderButton = null;
    }
    public RestaurantMenuImage(String imagePath) {
        super();
        try {
            image = ImageIO.read(new File(imagePath));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        setPreferredSize(new Dimension(120, 120)); // set preferred size to make it a square
        setBorderPainted(false); // remove default border
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        RestaurantMenuImage button = new RestaurantMenuImage("014100046424.jpg");
        frame.getContentPane().add(button);
        frame.pack();
        frame.setVisible(true);
    }
}
