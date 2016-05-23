import java.util.ArrayList;

/**
 * Created by samhollenbach on 5/19/16.
 */
public class HML_Block {

    String block_name;

    ArrayList<HML_Field> fields = new ArrayList<HML_Field>();

    ArrayList<HML_Block> sub_blocks = new ArrayList<HML_Block>();


    public HML_Block(String block_name) {
        this.block_name = block_name;
    }

    public void addField(HML_Field field){
        fields.add(field);
        HML_Main.allFields.add(field);
        field.setID(HML_Main.idCounter++);
    }

    public void addSubBlock(HML_Block block){
        sub_blocks.add(block);
    }

    public String getBlockName() {
        return block_name;
    }
}
