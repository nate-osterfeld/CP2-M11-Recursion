import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class RecursiveListerFrame extends JFrame
{
    JPanel wholePanel, buttonPanel, taPanel;

    JButton quitButton, startButton;

    JTextArea fileTA;

    JScrollPane scrollPaneVariable;

    Font fontM = new Font(Font.MONOSPACED, Font.PLAIN, 20);

    JFileChooser chooseFile = new JFileChooser();
    File selectedFile;
    File workingDirectory = new File(System.getProperty("user.dir"));
    int level = 0;

    public RecursiveListerFrame()
    {
        wholePanel = new JPanel();
        wholePanel.setLayout(new BorderLayout());
        createTAPanel();
        wholePanel.add(taPanel, BorderLayout.CENTER);
        createButtonPanel();
        wholePanel.add(buttonPanel, BorderLayout.SOUTH);
        add(wholePanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(600,600);
    }

    public void createTAPanel()
    {
        taPanel = new JPanel();
        fileTA = new JTextArea(20,50);
        scrollPaneVariable = new JScrollPane(fileTA);
        taPanel.add(scrollPaneVariable);
        fileTA.setEditable(false);
        fileTA.setFont(fontM);
    }

    public void createButtonPanel()
    {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,2));
        startButton = new JButton("Choose file");
        quitButton = new JButton("Quit");
        quitButton.addActionListener((ActionEvent ae) -> System.exit(0));
        buttonPanel.add(startButton);
        buttonPanel.add(quitButton);
        quitButton.setFont(fontM);
        startButton.setFont(fontM);
        startButton.addActionListener((ActionEvent ae) ->
        {
            fileTA.setText("");
            chooseFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooseFile.setCurrentDirectory(workingDirectory);
            if(chooseFile.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                selectedFile = chooseFile.getSelectedFile();
                String basePath = selectedFile.getPath();
                processDirectory(basePath, selectedFile.getName(), level);
            }
            else {System.out.println("Please choose a file...");}
        });
    }

    public void processDirectory(String basePath, String directoryName, int level)
    {
        String spacesString = "";
        for(int spaceInt = 0; spaceInt <= (level*4); spaceInt++){spacesString += " ";}
        fileTA.append(spacesString + ">" + directoryName + "\n");
        //System.out.println(spacesString + ">" + directoryName);

        level++;
        File stringFile = new File(basePath);
        File[] listOfFiles = stringFile.listFiles();
        for(File file : listOfFiles)
        {
            if(file.isDirectory()) {processDirectory(basePath + "\\" + file.getName(), file.getName(), level);}
            else
            {
                String spacesStringTwo = "";
                for(int spaceInt = 0; spaceInt <= (level*4); spaceInt++){spacesStringTwo += " ";}
                fileTA.append(spacesStringTwo + " " + file.getName() + "\n");
                //System.out.println(spacesStringTwo + " " + file.getName());
            }
        }
    }
}