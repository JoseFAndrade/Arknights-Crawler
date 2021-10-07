package Main.stats;

public class StatBean {
    private String level;
    private Integer hp;
    private Integer atk;
    private Integer def;
    private Integer artRes;
    private Integer dpCost;
    private Integer block;
    private Integer redeployTime;
    private Double attackInterval;

    public String getLevel() {
        return level;
    }

    public Integer getHp() {
        return hp;
    }

    public Integer getAtk() {
        return atk;
    }

    public Integer getDef() {
        return def;
    }

    public Integer getArtRes() {
        return artRes;
    }

    public Integer getDpCost() {
        return dpCost;
    }

    public Integer getBlock() {
        return block;
    }

    public Integer getRedeployTime() {
        return redeployTime;
    }

    public Double  getAttackInterval() {
        return attackInterval;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public void setAtk(Integer atk) {
        this.atk = atk;
    }

    public void setDef(Integer def) {
        this.def = def;
    }

    public void setArtRes(Integer artRes) {
        this.artRes = artRes;
    }

    public void setDpCost(Integer dpCost) {
        this.dpCost = dpCost;
    }

    public void setBlock(Integer block) {
        this.block = block;
    }

    public void setRedeployTime(Integer redeployTime) {
        this.redeployTime = redeployTime;
    }

    public void setAttackInterval(Double attackInterval) {
        this.attackInterval = attackInterval;
    }
}
