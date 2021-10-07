package Main.stats;

public class ChangeableStats{
    public String level;
    public Integer hp;
    public Integer atk;
    public Integer def;
    public Integer artRes;
    public Integer dpCost;
    public Integer block;

    public ChangeableStats(String level, String hp, String atk, String def, String artRes, String dpCost, String block){
        this.hp = Integer.parseInt(hp);
        this.atk = Integer.parseInt(atk);
        this.def = Integer.parseInt(def);
        this.artRes = Integer.parseInt(artRes);
        this.dpCost = Integer.parseInt(dpCost);
        this.block = Integer.parseInt(block);
        this.level  = level;
    }

    public String toString(){
        String result="Hp: %d, Atk: %d, Def: %d, ArtRes: %d, DpCost: %d, Block: %d";
        //String newResult = String.format(result, hp.intValue(),atk.intValue(),def.intValue(),artRes.intValue(),dpCost.intValue(),block.intValue());
        return  String.format(result, hp,atk,def,artRes,dpCost,block);
    }

}
