import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by samhollenbach on 5/19/16.
 */
public class HML_Main extends JFrame implements ActionListener{

    int windowWidth;
    int windowHeight;

    HML_Output out;

    PrintWriter writer;
    BufferedReader br;

    String date;
    String workingDirectory;

    ArrayList<JTextField> inputs = new ArrayList<>();

    static ArrayList<HML_Field> allFields = new ArrayList<>();
    static int idCounter = 0;

    static int GLStringID, dateID, locusID;

    final JFileChooser fc = new JFileChooser();
    JButton fileChoose;
    JButton print;
    JLabel directory;

    JLabel background;

    public static void main(String[] args) {
        HML_Main hml = new HML_Main();
        hml.run();
    }

    public HML_Main(){

    }

    public void run(){
        initialize();
    }


    /**
     * Initializes the JFrame and the Choose Folder and Print Files buttons
     * Also forms the HML structure by calling new HML_Output()
     */
    void initialize(){

        //Change how layout works, right now its all paced manually
        //Create tree structure the way
        setLayout(new BorderLayout());

        setContentPane(new JLabel(new ImageIcon("images/HML_background.png")));

        setLayout(null);




        windowWidth = 1000;
        windowHeight = 650;
        setSize(windowWidth,windowHeight);
        setTitle("HML");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);





        //Color backgroundBlue = new Color(6,31,71);

        //getContentPane().setBackground(backgroundBlue);


        //File chooser button
        fileChoose = new JButton("Choose Folder");
        fileChoose.setBounds(740,10,110,40);
        fileChoose.addActionListener(this);
        add(fileChoose);

        //File chooser
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //fc.setCurrentDirectory(new java.io.File("."));
        fc.setDialogTitle("Choose Directory");
        fc.setAcceptAllFileFilterUsed(false);
        fc.setControlButtonsAreShown(true);

        //Directory label
        directory = new JLabel("No Directory Selected");
        directory.setBounds(725,100,200,30);
        directory.setForeground(Color.WHITE);
        add(directory);

        //Print files button
        print = new JButton("Print Files");
        print.setBounds(740,60,110,40);
        print.addActionListener(this);
        add(print);
        print.setVisible(true);

        /*JLabel logo = new JLabel(new ImageIcon("images/logo/UCSF_sig_white_RGB.png"));
        logo.setBounds(475,300,500,300);
        add(logo);*/


        out = new HML_Output();
        createTextFields();
        setVisible(true);
    }

    /**
     * Creates a new JTextField and JLabel for each field in the HML document to allow user input
     * Likely will reformat how this works because its not very customizable as it is
     */
    public void createTextFields(){

        int y = 0;
        int x = 100;

        //Create text box for each field
        for(HML_Field f : allFields){
            inputs.add(null);
            if(f.getID() == dateID || f.getID() == locusID || f.getID() == GLStringID){
                continue;
            }

            //String label;

            JTextField tf = new JTextField();

            tf.setSize(125,30);


            //Set locations for each text box
            switch (f.getFieldName()){
                case "version":
                    tf.setLocation(410,58);
                    break;
                case "project-name":
                    tf.setLocation(410,105);
                    break;
                case "root":
                    tf.setLocation(107,192);
                    break;
                case "extension":
                    tf.setLocation(107,238);
                    break;
                case "reporting-center-id":
                    tf.setLocation(307,192);
                    break;
                case "reporting-center-context":
                    tf.setLocation(307,238);
                    break;
                case "center-code":
                    tf.setLocation(507,192);
                    break;
                case "test-id":
                    tf.setLocation(707,192);
                    break;
                case "name":
                    tf.setLocation(707,238);
                    break;
                case "gene-family":
                    tf.setLocation(507,335);
                    break;
                case "uri":
                    tf.setLocation(107,400);
                    break;
                case "format":
                    tf.setLocation(107,445);
                    break;
                case "paired":
                    tf.setLocation(107,494);
                    break;
                case "pooled":
                    tf.setLocation(107,543);
                    break;
                case "availability":
                    tf.setLocation(107,592);
                    break;
                case "test-id-source":
                    tf.setLocation(307,440);
                    break;
                case "allele-db":
                    tf.setLocation(707,370);
                    break;
                case "allele-version":
                    tf.setLocation(707,430);

                default:
                    tf.setLocation(1000,1000);
                    tf.setVisible(false);
                    break;
            }
            tf.setBackground(new Color(222,222,222));


            inputs.set(f.getID(),tf);
            add(tf);


            //Set defaults for text boxes
            if(Objects.equals(f.getFieldName(), "gene-family")){
                tf.setText("KIR");
            }else if(Objects.equals(f.getFieldName(), "allele-db")){
                tf.setText("IPD-KIR");
            }


            //jl.setVisible(true);
            tf.setVisible(true);
            System.out.println(f.getFieldName() + " - " + f.getID());

        }


    }


    /**
     * Extracts genos files from Results directory and initializes sample ID, date, locus and glstring variables
     * for each line of each genos file.
     * -Date pulled from Results directory suffix
     * -Locus pulled from genos file name suffix
     * -Sample pulled from each lines prefix in genos file
     * -glstring pulled from rest of line in genos file
     * Calls writeFile with these parameters
     */
    public void processFiles() throws IOException{
        date = workingDirectory.substring(workingDirectory.indexOf("Results_")+8,workingDirectory.length());

        File dir = new File(workingDirectory+"/KIRcaller");
        File[] foundFiles = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith("genos_");
            }
        });

        int filesWritten = 0;

        for(File f : foundFiles){

            String locus = f.getName().substring(6,f.getName().length()-4);

            try{
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            String line = null;
            try {
                while ((line = br.readLine()) != null) {
                    String sample = line.substring(0,8);

                    //Currently using tab delimited
                    //Either switch to pipe delimited or Wesley will switch his output to piped
                    String gl = line.substring(9);

                    writeFile(sample,date,locus,gl);
                    filesWritten++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        JOptionPane.showMessageDialog(this,filesWritten + " HML files created");

    }

    /**
     * Initializes an HML file with sampleID and locus in the name
     * Inserts parameter data into HML structure and starts recursive call of printBlock
     * @param sampleID
     * @param date
     * @param locus
     * @param glstring
     */
    public void writeFile(String sampleID, String date, String locus, String glstring){

        String printFileName = "prints/HML_"+sampleID+"_"+locus+".xml";

        try {
            writer = new PrintWriter(printFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");


        for(HML_Field f : allFields){

            if(f.getID() == dateID){
                f.setField(date);
            }else if(f.getID() == locusID){
                f.setField(locus);
            }else if(f.getID() == GLStringID){
                f.setField(glstring);
            }else{
                f.setField(inputs.get(f.getID()).getText());
            }
        }
        out.setDate(date);
        out.setGLstring_input(glstring);


        printBlock(writer,out.getTopBlock(),0);

        writer.close();

    }


    /**
     * Initially takes in the top block (HML block) and recursively calls sub-blocks
     * Indentation incrememnts for each sub-block to form tabbed file structure
     * @param writer
     * @param block
     * @param indentation
     */
    public void printBlock(PrintWriter writer, HML_Block block, int indentation){

        boolean includeOptionalFields = false;

        String ind = "";
        for(int i = 0; i < indentation; i++){
            ind += "\t";
        }
        writer.print(ind+"<"+block.getBlockName());
        if(!block.fields.isEmpty()){

            for(HML_Field f : block.fields){
                if(!includeOptionalFields && !f.isRequiredField() && f.getFieldValue().isEmpty()){
                    continue;
                }
                if(f instanceof HML_Unlabeled_Field){
                    writer.print(">\n\t" + ind + f.getFieldValue() + "\n"+ind+"</"+block.getBlockName()+">\n");
                    return;
                }else{
                    writer.print("\n\t" + ind + f.getFieldName() + "=\"" + f.getFieldValue() + "\"");
                }
            }
        }
        if(!block.sub_blocks.isEmpty()){
            writer.print(">\n");
            for(HML_Block b : block.sub_blocks){
                printBlock(writer, b, indentation+1);
            }
            writer.print(ind+"</"+block.getBlockName()+">\n");
        }else{
            writer.print("/>\n");
        }
    }


    /**
     * Called when a button is pressed
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        //Handle file chooser activation
        if (e.getSource() == fileChoose) {
            int returnVal = fc.showOpenDialog(HML_Main.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                directory.setText(file.getName());
                workingDirectory = file.getAbsolutePath();
            } else {

            }
        }else if(e.getSource() == print){
            try {
                processFiles();
            } catch (IOException e1) {
                System.out.println("Could not process directory");
                e1.printStackTrace();
            }
        }
    }

}
