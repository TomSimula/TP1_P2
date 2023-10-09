package groupe.camembert.UI;

import groupe.camembert.Process.Analyzer;
import groupe.camembert.Process.Config;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class GUI implements ActionListener{
    private JFrame frame;
    private JPanel mainPanel, topPanel, bottomPanel, basicStatPanel, callButtonPanel;
    private JButton configButton, q8, q9, q10, q11, q12,loadButton, cancelButton;
    private JDialog configDialog;
    private JLabel projectPathLabel, jrePathLabel,nbClass, nbMethod, nbLine, nbPackage,
            avgMethodPerClass, avgLinePerClass, avgAttributePerClass, maxParameter;
    private JTextField projectPathField, jrePathField;
    private JTextArea resCallTextArea;
    private JScrollPane resCallScrollPane;
    private final Analyzer analyzer = new Analyzer();

    public GUI(String name){
        frame = new JFrame();
        frame.setTitle(name);

        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenDimension.width, screenDimension.height);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setPreferredSize(screenDimension);
        mainPanel.setLayout(new BorderLayout());

        topPanel = new JPanel();
        topPanel.setBackground(Color.RED);
        topPanel.setPreferredSize(new Dimension(screenDimension.width, screenDimension.height/3));
        mainPanel.add(topPanel,BorderLayout.PAGE_START);

        configButton = new JButton("Configuration");
        configButton.addActionListener(this);
        topPanel.add(configButton);

        configDialog = new JDialog(frame, "Configuration", true);
        configDialog.setSize(450, 150);
        configDialog.setLocationRelativeTo(null);
        configDialog.setLayout(new FlowLayout());

        projectPathLabel = new JLabel("JRE Path:");
        projectPathField = new JTextField(Config.projectSourcePath,30);
        jrePathLabel = new JLabel("JRE Path:");
        jrePathField = new JTextField(Config.jrePath,30);
        loadButton = new JButton("Load");
        loadButton.addActionListener(this);
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);

        configDialog.add(projectPathLabel);
        configDialog.add(projectPathField);
        configDialog.add(jrePathLabel);
        configDialog.add(jrePathField);
        configDialog.add(cancelButton);
        configDialog.add(loadButton);

        basicStatPanel = new JPanel();
        basicStatPanel.setBackground(Color.ORANGE);
        basicStatPanel.setPreferredSize(new Dimension(screenDimension.width, screenDimension.height/3));
        basicStatPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        topPanel.add(basicStatPanel);

        nbClass = new JLabel("Class:");
        nbClass.setFont(new Font("Arial", Font.BOLD, 20));
        nbClass.setOpaque(true);
        nbClass.setBackground(Color.YELLOW);
        nbClass.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(10, 5, 10, 5)));
        basicStatPanel.add(nbClass);

        nbMethod = new JLabel("Methods:");
        nbMethod.setFont(new Font("Arial", Font.BOLD, 20));
        nbMethod.setOpaque(true);
        nbMethod.setBackground(Color.YELLOW); 
        nbMethod.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(10, 5, 10, 5)));
        basicStatPanel.add(nbMethod);

        nbLine = new JLabel("Lines:");
        nbLine.setFont(new Font("Arial", Font.BOLD, 20));
        nbLine.setOpaque(true);
        nbLine.setBackground(Color.YELLOW); 
        nbLine.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(10, 5, 10, 5)));
        basicStatPanel.add(nbLine);

        nbPackage = new JLabel("Packages:");
        nbPackage.setFont(new Font("Arial", Font.BOLD, 20));
        nbPackage.setOpaque(true);
        nbPackage.setBackground(Color.YELLOW); 
        nbPackage.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(10, 5, 10, 5)));
        basicStatPanel.add(nbPackage);

        avgMethodPerClass = new JLabel("AVG method per class:");
        avgMethodPerClass.setFont(new Font("Arial", Font.BOLD, 20));
        avgMethodPerClass.setOpaque(true);
        avgMethodPerClass.setBackground(Color.YELLOW); 
        avgMethodPerClass.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(10, 5, 10, 5)));
        basicStatPanel.add(avgMethodPerClass);

        avgLinePerClass = new JLabel("AVG line per class:");
        avgLinePerClass.setFont(new Font("Arial", Font.BOLD, 20));
        avgLinePerClass.setOpaque(true);
        avgLinePerClass.setBackground(Color.YELLOW); 
        avgLinePerClass.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(10, 5, 10, 5)));
        basicStatPanel.add(avgLinePerClass);

        avgAttributePerClass = new JLabel("AVG attribute per class:");
        avgAttributePerClass.setFont(new Font("Arial", Font.BOLD, 20));
        avgAttributePerClass.setOpaque(true);
        avgAttributePerClass.setBackground(Color.YELLOW); 
        avgAttributePerClass.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(10, 5, 10, 5)));
        basicStatPanel.add(avgAttributePerClass);

        maxParameter = new JLabel("MAX parameter:");
        maxParameter.setFont(new Font("Arial", Font.BOLD, 20));
        maxParameter.setOpaque(true);
        maxParameter.setBackground(Color.YELLOW); 
        maxParameter.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(10, 5, 10, 5)));
        basicStatPanel.add(maxParameter);

        bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.GREEN);
        bottomPanel.setPreferredSize(new Dimension(screenDimension.width, screenDimension.height*2/3));
        bottomPanel.setLayout(new BorderLayout());
        mainPanel.add(bottomPanel, BorderLayout.CENTER);

        resCallTextArea = new JTextArea();
        resCallScrollPane = new JScrollPane(resCallTextArea);
        resCallTextArea.setBackground(Color.BLUE);
        resCallScrollPane.setPreferredSize(new Dimension(bottomPanel.getPreferredSize().width*2/3, bottomPanel.getPreferredSize().height));
        resCallScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        bottomPanel.add(resCallScrollPane, BorderLayout.LINE_START);

        callButtonPanel = new JPanel();
        callButtonPanel.setBackground(Color.PINK);
        callButtonPanel.setPreferredSize(new Dimension(bottomPanel.getPreferredSize().width/3, bottomPanel.getPreferredSize().height));
        callButtonPanel.setLayout(new GridLayout(3,1, 5, 5));
        bottomPanel.add(callButtonPanel, BorderLayout.CENTER);

        q8 = new JButton();
        q8.addActionListener(this);
        callButtonPanel.add(q8);

        q9 = new JButton();
        q9.addActionListener(this);
        callButtonPanel.add(q9);

        q10 = new JButton();
        q10.addActionListener(this);
        callButtonPanel.add(q10);

        q11 = new JButton();
        q11.addActionListener(this);
        callButtonPanel.add(q11);

        q12 = new JButton("Q12");
        q12.addActionListener(this);
        callButtonPanel.add(q12);

        frame.add(mainPanel);
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String res = "";
        List<TypeDeclaration> listTD;
        if (actionEvent.getSource() == configButton){
            configDialog.setVisible(true);
        } else if (actionEvent.getSource() == cancelButton){
            configDialog.setVisible(false);
        } else if (actionEvent.getSource() == loadButton){
            Config.setprojectSourcePath(projectPathField.getText());
            Config.setJrePath(jrePathField.getText());
            analyzer.updateAnalyzer();
            try {
                nbClass.setText("Class: "+analyzer.getNbClasses());
                nbMethod.setText("Methods: "+analyzer.getNbMethods());
                nbLine.setText("Lines: "+analyzer.getNbCodeLines());
                nbPackage.setText("Packages: "+analyzer.getNbPackages());
                avgMethodPerClass.setText("AVG method per class: "+analyzer.getMethodsAvgPerClass());
                avgLinePerClass.setText("AVG line per méthode: "+analyzer.getLinesAVGPerMethod());
                avgAttributePerClass.setText("AVG attribute per class: "+analyzer.getAttributeAvgPerClass());
                maxParameter.setText("MAX parameter: "+analyzer.getMaxParams());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            configDialog.setVisible(false);
        } else if (actionEvent.getSource() == q8){
            try {
                listTD = analyzer.getClassesWithMostMethods();
                for(TypeDeclaration type : listTD)
                    res += type.getName().getFullyQualifiedName() + ": " + type.getMethods().length + ("\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            resCallTextArea.setText(res);
            resCallTextArea.setCaretPosition(0);
        } else if (actionEvent.getSource() == q9){
            try {
                listTD = analyzer.getClassesWithMostAttributes();
                for(TypeDeclaration type : listTD)
                    res += type.getName().getFullyQualifiedName() + ": " + type.getFields().length + ("\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            resCallTextArea.setText(res);
            resCallTextArea.setCaretPosition(0);
        } else if (actionEvent.getSource() == q10){
            try {
                Set<TypeDeclaration> setClassQ10 = analyzer.getClassesWithMostAttributesAndMethods();
                for(TypeDeclaration type : setClassQ10)
                    res += type.getName().getFullyQualifiedName() + ("\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            resCallTextArea.setText(res);
            resCallTextArea.setCaretPosition(0);
        } else if (actionEvent.getSource() == q11){
            try {
                listTD = analyzer.getClassesWithMoreThanXMethods(5);
                for(TypeDeclaration type : listTD)
                    res += type.getName().getFullyQualifiedName() + ": " + type.getMethods().length + ("\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            resCallTextArea.setText(res);
            resCallTextArea.setCaretPosition(0);
        } else if (actionEvent.getSource() == q12){
            try {
                res = analyzer.getMethodsWithMostLines();
                System.out.println(res);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            resCallTextArea.setText(res);
            resCallTextArea.setCaretPosition(0);
        }
    }
}