import java.io.PrintWriter;

/**
 * Created by samhollenbach on 5/19/16.
 */
public class HML_Output {

    //Top block
    HML_Block hml;

    String dateString;
    String glstring_input;

    public HML_Output(){
        formStructure();
    }



    public void formStructure(){

        //hml block -- TOP
        hml = new HML_Block("hml");
        HML_Field version = new HML_Field("version", true);
        HML_Field project_name = new HML_Field("project-name",false);
        hml.addField(version);
        hml.addField(project_name);



        //hmlid block
        HML_Block hmlid = new HML_Block("hmlid");
        hml.addSubBlock(hmlid);
        HML_Field root = new HML_Field("root", true);
        HML_Field extension = new HML_Field("extension",false);
        hmlid.addField(root);
        hmlid.addField(extension);



        //reporting-center block
        HML_Block reporting_center = new HML_Block("reporting-center");
        hml.addSubBlock(reporting_center);
        HML_Field reporting_center_id = new HML_Field("reporting-center-id", true);
        HML_Field reporting_center_context = new HML_Field("reporting-center-context", false);
        reporting_center.addField(reporting_center_id);
        reporting_center.addField(reporting_center_context);

        //sample block
        HML_Block sample = new HML_Block("sample");
        hml.addSubBlock(sample);
        HML_Field id = new HML_Field("id",true);
        HML_Field center_code = new HML_Field("center-code",false);
        sample.addField(id);
        sample.addField(center_code);

        //collection-method -- sample sub-block
        HML_Block collection_method = new HML_Block("collection-method");
        sample.addSubBlock(collection_method);

        //typing -- sample sub-block
        HML_Block typing = new HML_Block("typing");
        sample.addSubBlock(typing);
        HML_Field gene_family = new HML_Field("gene-family",true);
        HML_Field date = new HML_Field("date",true);
        typing.addField(gene_family);
        typing.addField(date);
        HML_Main.dateID = date.getID();

        //Typing Method, Allele Assignment, and Consensus Sequence blocks -- typing sub-blocks
        //Moved to separate classes for readability and customization
        typing.addSubBlock(new Typing_Method());
        typing.addSubBlock(new Allele_Assignment());
        typing.addSubBlock(new Consensus_Sequence());


        //typing-test-names
        HML_Block typing_test_names = new HML_Block("typing-test-names");
        hml.addSubBlock(typing_test_names);
        HML_Field test_id = new HML_Field("test-id",true);
        typing_test_names.addField(test_id);
        //typing-test-name -- typing-test-names sub-block
        HML_Block typing_test_name = new HML_Block("typing-test-name");
        typing_test_names.addSubBlock(typing_test_name);
        HML_Field name = new HML_Field("name",true);
        typing_test_name.addField(name);

    }

    public void setDate(String date) {
        this.dateString = date;
    }

    public void setGLstring_input(String glstring_input) {
        this.glstring_input = glstring_input;
    }

    public HML_Block getTopBlock(){
        return hml;
    }













}
