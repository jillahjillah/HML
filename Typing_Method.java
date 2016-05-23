/**
 * Created by samhollenbach on 5/19/16.
 */
public class Typing_Method extends HML_Block {


    public Typing_Method() {
        super("typing-method");

        formBlockStructure();
    }

    public void formBlockStructure(){



        /**
         * Not being used
         *
         HML_Block sso = new HML_Block("sso");


         HML_Block ssp = new HML_Block("ssp");


         HML_Block sbt_sanger = new HML_Block("sbt-sanger");*/


        HML_Block sbt_ngs = new HML_Block("sbt-ngs");
        this.addSubBlock(sbt_ngs);
        HML_Field locus = new HML_Field("locus", false);
        HML_Field test_id = new HML_Field("test-id", false);
        HML_Field test_id_source = new HML_Field("test-id-source", false);
        sbt_ngs.addField(locus);
        sbt_ngs.addField(test_id);
        sbt_ngs.addField(test_id_source);
        HML_Main.locusID = locus.getID();

        HML_Block raw_reads = new HML_Block("raw-reads");
        HML_Field uri = new HML_Field("uri", true);
        HML_Field format = new HML_Field("format", true);
        HML_Field paired = new HML_Field("paired", true);
        HML_Field pooled = new HML_Field("pooled", true);
        HML_Field availability = new HML_Field("availability", false);
        HML_Field adapter_trimmed = new HML_Field("adapter-trimmed", false);
        HML_Field quality_trimmed = new HML_Field("quality-trimmed", false);
        raw_reads.addField(uri);
        raw_reads.addField(format);
        raw_reads.addField(paired);
        raw_reads.addField(pooled);
        raw_reads.addField(availability);
        raw_reads.addField(adapter_trimmed);
        raw_reads.addField(quality_trimmed);

    }



}
