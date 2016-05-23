/**
 * Created by samhollenbach on 5/19/16.
 */
public class Allele_Assignment extends HML_Block {

    String glstring_input = null;


    public Allele_Assignment() {
        super("allele-assignment");

        formBlockStructure();

    }

    public void formBlockStructure(){

        HML_Field date = new HML_Field("date",true);
        HML_Field allele_db = new HML_Field("allele-db",false);
        HML_Field allele_version = new HML_Field("allele-version",false);
        this.addField(date);
        this.addField(allele_db);
        this.addField(allele_version);


        /**
         * Currently not being used

        HML_Block haploid = new HML_Block("haploid");

        */

        HML_Block glstring = new HML_Block("glstring");
        this.addSubBlock(glstring);
        HML_Field uri = new HML_Field("uri",false);
        glstring.addField(uri);
        HML_Unlabeled_Field glstring_input = new HML_Unlabeled_Field();
        glstring.addField(glstring_input);
        HML_Main.GLStringID = glstring_input.getID();



    }




}
