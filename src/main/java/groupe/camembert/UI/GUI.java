package groupe.camembert.UI;

import javax.swing.*;
import java.awt.*;

public class GUI {

    public GUI(String name){
        JFrame frame = new JFrame();
        frame.setTitle(name);

        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenDimension.width, screenDimension.height);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setPreferredSize(screenDimension);
        mainPanel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.RED);
        topPanel.setPreferredSize(new Dimension(screenDimension.width, screenDimension.height/3));
        mainPanel.add(topPanel,BorderLayout.PAGE_START);

        JButton configButton = new JButton("Configuration");
        topPanel.add(configButton);

        JPanel basicStatPanel = new JPanel();
        basicStatPanel.setBackground(Color.ORANGE);
        basicStatPanel.setPreferredSize(new Dimension(screenDimension.width, screenDimension.height/3));
        basicStatPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        topPanel.add(basicStatPanel);

        JLabel nbClass = new JLabel("Class:");
        nbClass.setFont(new Font("Arial", Font.BOLD, 20));
        nbClass.setOpaque(true);
        nbClass.setBackground(Color.YELLOW);
        nbClass.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(10, 5, 10, 5)));
        basicStatPanel.add(nbClass);

        JLabel nbMethod = new JLabel("Methods:");
        nbMethod.setFont(new Font("Arial", Font.BOLD, 20));
        nbMethod.setOpaque(true);
        nbMethod.setBackground(Color.YELLOW); 
        nbMethod.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(10, 5, 10, 5)));
        basicStatPanel.add(nbMethod);

        JLabel nbLine = new JLabel("Lines:");
        nbLine.setFont(new Font("Arial", Font.BOLD, 20));
        nbLine.setOpaque(true);
        nbLine.setBackground(Color.YELLOW); 
        nbLine.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(10, 5, 10, 5)));
        basicStatPanel.add(nbLine);

        JLabel nbPackage = new JLabel("Packages:");
        nbPackage.setFont(new Font("Arial", Font.BOLD, 20));
        nbPackage.setOpaque(true);
        nbPackage.setBackground(Color.YELLOW); 
        nbPackage.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(10, 5, 10, 5)));
        basicStatPanel.add(nbPackage);

        JLabel avgMethodPerClass = new JLabel("AVG method per class:");
        avgMethodPerClass.setFont(new Font("Arial", Font.BOLD, 20));
        avgMethodPerClass.setOpaque(true);
        avgMethodPerClass.setBackground(Color.YELLOW); 
        avgMethodPerClass.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(10, 5, 10, 5)));
        basicStatPanel.add(avgMethodPerClass);

        JLabel avgLinePerClass = new JLabel("AVG line per class:");
        avgLinePerClass.setFont(new Font("Arial", Font.BOLD, 20));
        avgLinePerClass.setOpaque(true);
        avgLinePerClass.setBackground(Color.YELLOW); 
        avgLinePerClass.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(10, 5, 10, 5)));
        basicStatPanel.add(avgLinePerClass);

        JLabel avgAttributePerClass = new JLabel("AVG attribute per class:");
        avgAttributePerClass.setFont(new Font("Arial", Font.BOLD, 20));
        avgAttributePerClass.setOpaque(true);
        avgAttributePerClass.setBackground(Color.YELLOW); 
        avgAttributePerClass.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(10, 5, 10, 5)));
        basicStatPanel.add(avgAttributePerClass);

        JLabel maxParameter = new JLabel("MAX parameter:");
        maxParameter.setFont(new Font("Arial", Font.BOLD, 20));
        maxParameter.setOpaque(true);
        maxParameter.setBackground(Color.YELLOW); 
        maxParameter.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(10, 5, 10, 5)));
        basicStatPanel.add(maxParameter);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.GREEN);
        bottomPanel.setPreferredSize(new Dimension(screenDimension.width, screenDimension.height*2/3));
        bottomPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.add(bottomPanel, BorderLayout.CENTER);

        JTextArea resCallTextArea = new JTextArea();
        resCallTextArea.setBackground(Color.BLUE);
        resCallTextArea.setPreferredSize(new Dimension(bottomPanel.getPreferredSize().width*2/3, bottomPanel.getPreferredSize().height));
        bottomPanel.add(resCallTextArea, BorderLayout.LINE_START);

        JPanel callButtonPanel = new JPanel();
        callButtonPanel.setBackground(Color.PINK);
        callButtonPanel.setPreferredSize(new Dimension(bottomPanel.getPreferredSize().width/3, bottomPanel.getPreferredSize().height));
        callButtonPanel.setLayout(new GridLayout(3,1, 5, 5));
        bottomPanel.add(callButtonPanel, BorderLayout.CENTER);

        JButton q8 = new JButton();
        callButtonPanel.add(q8);

        JButton q9 = new JButton();
        callButtonPanel.add(q9);

        JButton q10 = new JButton();
        callButtonPanel.add(q10);

        JButton q11 = new JButton();
        callButtonPanel.add(q11);

        JButton q12 = new JButton();
        callButtonPanel.add(q12);

        frame.add(mainPanel);
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    public static void run(String[] args) {
        new GUI("Camembert");

    }
}
