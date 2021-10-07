package Main.database;

import Main.skill.SkillBean;
import Main.skill.SkillMasteryMaterialCostBean;
import Main.skill.SkillMaterialCostBean;
import Main.stats.StatBean;
import Main.unit.EliteMaterialBean;
import Main.unit.PotentialBean;
import Main.unit.TalentBean;
import Main.unit.Unit;
import Main.util.MaterialFactory;

import java.sql.*;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static Main.util.Util.rangeToString;


public class Database {
    static String port = "3306";
    static String name = "root";
    static String password = "";
    private static Connection conn = null;

    public static Boolean checkName(String unitName){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conn = null;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/arknights", name,null);

            String query = "SELECT * FROM UNIT WHERE name = (?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,unitName);
            ResultSet resultSet = stmt.executeQuery();
            boolean found = false;
            if(resultSet.next())
                found = true;
            conn.close();
            return found;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    public static void addUnit(Unit unit){
        try {

            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conn = null;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/arknights", name,null);
            conn.setAutoCommit(false);
            String query =
                    "INSERT INTO UNIT (name,rarity,position ,attack_type,tags,class_type,archetype," +
                    "trait,attack_range)" +
                    "VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,unit.getName());
            stmt.setInt(2,unit.getRarity());
            stmt.setString(3,unit.getPosition());
            stmt.setString(4,unit.getAttackType());
            stmt.setString(5,unit.getTags().toString());
            stmt.setString(6,unit.getClassType());
            stmt.setString(7, Objects.toString(unit.getArchetype().toString(), "null"));
            stmt.setString(8,unit.getTrait());
            stmt.setString(9,"empty for now");
            stmt.executeUpdate();
            //conn.close();


            stats(unit);
            addSkillInfo(unit);
            addTalents(unit);
            addTrustStats(unit);
            addPotentials(unit);
            addSkillMaterialCost(unit);
            addSkillMasteryCost(unit);
            addUnitRarity(unit);
            addUnitImages(unit);
            conn.commit();
            conn.close();
            //System.out.println(addMaterialOrder(unit.getName()));
        } catch (Exception e) {
            e.printStackTrace();

            if (conn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }

        }
    }

    public static void addUnitImages(Unit unit){
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //conn = null;
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/arknights", name,null);

                String query = "INSERT INTO unit_images (identifier,unit_name,image_url) " +
                        "VALUES (?,?,?)";

                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1,1);
                stmt.setString(2,unit.getName());
                stmt.setString(3,unit.getUnitImage());

                stmt.executeUpdate();
            //conn.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



    public static void addUnitRarity(Unit unit){
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //Connection conn = null;
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/arknights", name,null);
            List<EliteMaterialBean> eliteMaterialBeanList = unit.getEliteBeansList();
            for(int i = 0; i < eliteMaterialBeanList.size(); i++){
                int materialOrderID = addMaterialOrder(unit.getName());
                EliteMaterialBean eliteMaterialBean = eliteMaterialBeanList.get(i);

                String query = "INSERT INTO ELITE_PROMOTION (material_order,unit_name,elite_level,from_icon,to_icon) VALUES (?,?,?,?,?)";

                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1,materialOrderID);
                stmt.setString(2,unit.getName());
                stmt.setInt(3,i+1);
                stmt.setString(4,eliteMaterialBean.getFromIcon());
                stmt.setString(5,eliteMaterialBean.getToIcon());
                stmt.executeUpdate();

                addMaterialProduct(eliteMaterialBean.getMaterialFactoryManager().getMaterials(),unit.getName(),materialOrderID);
            }


            //conn.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static int addMaterialOrder(String unitName){
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //Connection conn = null;
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/arknights", name,null);
            String query = "INSERT INTO MATERIAL_ORDER (id,unit_name) VALUES (NULL,?)";
            PreparedStatement stmt = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,unitName);
            stmt.executeUpdate();
            ResultSet resultSet = stmt.getGeneratedKeys();
            int materialOrderID = -1;
            while(resultSet.next()){
                materialOrderID = resultSet.getInt(1);
            }
            //conn.close();
            return materialOrderID;
        }
        catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public static void addSkillMasteryCost(Unit unit){


        try{
            //Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //Connection conn = null;
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/arknights", name,null);

            //As we go through each Bean we need to insert:
            //  new material order
            //  new skill mastery material order and the description        <needs material order>
            //  finally add multiple entries into material_product  <needs material order>

            List<List<SkillMasteryMaterialCostBean>> masteryList = unit.getListOfSkillMasteryCostBean();
            for(int skillMasteryIndex = 0; skillMasteryIndex < masteryList.size(); skillMasteryIndex++){
                List<SkillMasteryMaterialCostBean> MasteryBeanList = masteryList.get(skillMasteryIndex);
                for(int level = 0; level < MasteryBeanList.size(); level++){
                    int materialOrderID = addMaterialOrder(unit.getName());
                    SkillMasteryMaterialCostBean skillMasteryBean = MasteryBeanList.get(level);
                    String query = "INSERT INTO SKILL_MASTERY_MATERIAL_ORDER (material_order,unit_name,mastery_level,level_requisite,rarity_requisite_url," +
                            "image_url,upgrade_time,skill) VALUES (?,?,?,?,?,?,?,?)";

                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setInt(1,materialOrderID);
                    stmt.setString(2, unit.getName());
                    stmt.setInt(3,level);
                    stmt.setString(4,skillMasteryBean.getLevelRequisite());
                    stmt.setString(5,skillMasteryBean.getRarityRequisiteUrl());
                    stmt.setString(6,skillMasteryBean.getImageUrlLevel());
                    stmt.setString(7,skillMasteryBean.getTimeToUpgrade());
                    stmt.setInt(8,skillMasteryIndex);
                    stmt.executeUpdate();

                    addMaterialProduct(skillMasteryBean.getMaterialFactoryManager().getMaterials(),unit.getName(),materialOrderID);
                }
            }
           // conn.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void addSkillMaterialCost(Unit unit){
            try{
                //Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                //Connection conn = null;
                //conn = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/arknights", name,null);

                List<SkillMaterialCostBean> skillMaterialCostBeans = unit.getSkillCostBeansList();
                //As we go through each Bean we need to insert:
                //  new material order
                //  new skill material order and the description        <needs material order>
                //  finally add multiple entries into material_product  <needs material order>
                for(SkillMaterialCostBean eachBean: skillMaterialCostBeans){
                    int materialOrderID = addMaterialOrder(unit.getName());
                    String skillMaterialQuery = "INSERT INTO SKILL_MATERIAL_ORDER (material_order,unit_name,description,requisite_level,requisite_rarity_url) " +
                            "VALUES (?,?,?,?,?)";
                    PreparedStatement stmt = conn.prepareStatement(skillMaterialQuery);
                    stmt.setInt(1, materialOrderID);
                    stmt.setString(2,unit.getName());
                    stmt.setString(3,eachBean.getLevelUpgrade());
                    stmt.setString(4,eachBean.getRequisiteLevel());
                    stmt.setString(5,eachBean.getRequisiteRarityURL());
                    stmt.executeUpdate();

                    addMaterialProduct(eachBean.getMaterialFactoryManager().getMaterials(),unit.getName(),materialOrderID);
                }
                //conn.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
    }

    public static void addMaterialProduct(Collection<MaterialFactory> materialFactoryList, String unitName, int materialOrderID){
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //Connection conn = null;
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/arknights", name,null);

            for(MaterialFactory material : materialFactoryList){
                if(!checkMaterial(material.getMaterialName().trim()))
                    addMaterial(material.getMaterialName().trim(),material.getRarity(),material.getImageURL());

                String query = "INSERT INTO material_product (material_order,material_name,unit_name,quantity) VALUES (?,?,?,?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1,materialOrderID);
                stmt.setString(2,material.getMaterialName());
                stmt.setString(3,unitName);
                stmt.setInt(4,material.getCount());
                stmt.executeUpdate();
            }

            //conn.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void addMaterial(String materialName,int rarity, String imageUrl){
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //Connection conn = null;
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/arknights", name,null);

            String query = "INSERT INTO MATERIALS (name, rarity, imageUrl) VALUES (?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,materialName);
            stmt.setInt(2,rarity);
            stmt.setString(3,imageUrl);
            stmt.executeUpdate();
            //conn.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean checkMaterial(String materialName){
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //Connection conn = null;
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/arknights", name,null);

            String query = "SELECT * FROM MATERIALS WHERE name = (?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,materialName);
            ResultSet resultSet = stmt.executeQuery();
            boolean found = false;
            if(resultSet.next())
                found = true;
            //conn.close();
            return found;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static void addPotentials(Unit unit){
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //Connection conn = null;
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/arknights", name,null);
            PotentialBean potentialBean = unit.getPotentialBean();
            for(int i = 0; i < potentialBean.size(); i++){
                String query = "INSERT INTO POTENTIALS (description,imagesUrl,unit_name,level)" +
                        "VALUES (?,?,?,?)";
                List<String> descriptions = potentialBean.getDescriptions();
                List<String> imageUrlList = potentialBean.getImagesURL();
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1,descriptions.get(i));
                stmt.setString(2,imageUrlList.get(i));
                stmt.setString(3,unit.getName());
                stmt.setInt(4,i+1);
                stmt.executeUpdate();
            }
           // conn.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



    public static void addTalents(Unit unit){
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //Connection conn = null;
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/arknights", name,null);
            List<List<TalentBean>> listOfTalentBeans = unit.getListOfTalentBeans();
            for(int talent = 0; talent < listOfTalentBeans.size(); talent++){
                List<TalentBean> talentSkillBeanList = listOfTalentBeans.get(talent);
                for(int talentLevel = 0; talentLevel < talentSkillBeanList.size(); talentLevel++ ){
                    String query = "INSERT INTO TALENTS (talent_level,description,rarity_url,potential_url,operator_level,unit_name,name)"+
                            "VALUES (?,?,?,?,?,?,?)";
                    TalentBean talentBean = talentSkillBeanList.get(talentLevel);
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setInt(1,talentLevel+1);
                    stmt.setString(2,talentBean.getTalentDescription());
                    stmt.setString(3,talentBean.getRarityURL());
                    stmt.setString(4,talentBean.getPotentialURL());
                    stmt.setString(5,talentBean.getOperatorLevel());
                    stmt.setString(6,unit.getName());
                    stmt.setString(7,talentBean.getTalentName());
                    //add in the values here
                    stmt.executeUpdate();
                }
            }
            //conn.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void addTrustStats(Unit unit){
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //Connection conn = null;
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/arknights", name,null);
            List<String> trustStats = unit.getTrustStats();
            for(String each: trustStats){
                String query = "INSERT INTO TRUST_EXTRA_STATS (unit_name,description)" +
                        "VALUES (?,?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1,unit.getName());
                stmt.setString(2,each);
                stmt.executeUpdate();
            }
            //conn.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void stats(Unit unit){
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //Connection conn = null;
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/arknights", name,null);
            List<StatBean> statBeanList = unit.getStatBeansList();
            for(StatBean each: statBeanList){
                String query = "INSERT INTO STATS (level,redeploy_time,attack_interval,hp,atk,def,art_res,dp_cost,block,unit_name)" +
                        "VALUES (?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1,each.getLevel());
                stmt.setInt(2,each.getRedeployTime());
                stmt.setDouble(3,each.getAttackInterval());
                stmt.setInt(4,each.getHp());
                stmt.setInt(5,each.getAtk());
                stmt.setInt(6,each.getDef());
                stmt.setInt(7,each.getArtRes());
                stmt.setInt(8,each.getDpCost());
                stmt.setInt(9,each.getBlock());
                stmt.setString(10,unit.getName());
                stmt.executeUpdate();
            }
            //conn.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void addSkillInfo(Unit unit){
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //Connection conn = null;
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/arknights", name,null);

            //for each skill in skill manager
            //loop through the length of a max array to get the info
            //think of a way to make it look nice
            //maybe create a temporary holder class where temporary stats of skill can go into and this java file will be able to see that instead of getting
            //the whole skill class
            //look into possibly implementing a for loop or something 
            List<List<SkillBean>> listOfSkillBeans = unit.getListOfSkillBeans();
            for(int skill = 0; skill < listOfSkillBeans.size(); skill++){
                List<SkillBean> skillBeanList = listOfSkillBeans.get(skill);
                for(int level = 0; level < listOfSkillBeans.get(skill).size(); level++){
                    SkillBean skillBean = skillBeanList.get(level);
                    //start to create the query here
                    String query = "INSERT INTO SKILL (unit_name,name,level,initial_sp,skill_utilization,description,sp_charge_type," +
                            "sp_skill_activation,skill_tiles,sp_cost) " +
                            "VALUES (?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, unit.getName());
                    stmt.setString(2, skillBean.getName());
                    stmt.setInt(3,skillBean.getLevel());
                    stmt.setString(4,skillBean.getInitialSpInfo());
                    stmt.setString(5,skillBean.getSkillUtilizationInfo());
                    stmt.setString(6,skillBean.getSkillEffectInfo());
                    stmt.setString(7,skillBean.getSpChargeType());
                    stmt.setString(8,skillBean.getSpSkillActivation());
                    stmt.setString(9,rangeToString( skillBean.getSkillTiles()));
                    stmt.setString(10,skillBean.getSpCostInfo());
                    stmt.executeUpdate(); 
                }
            }
            //conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getMaterials() {
        try {
            // Connect to Database
            //Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //Connection conn = null;
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/arknights", name,null);


            conn.close();

            // Run query
            /*
            String query = "SELECT p.CoffeeProduct,p.ImageURl,Cpt.`desc` as Description,p.ProductTypeId,p.SKU " +
                    "from products p " +
                    "JOIN `coffee-product-types` Cpt " +
                    "ON Cpt.type = p.CoffeeProduct " +
                    "WHERE p.ProductTypeId in (1,2,3) group by CoffeeProduct";

             */
            String query = "SELECT * " +
                    "FROM materials";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                String name = rs.getString("name");
                System.out.println(name);
            }
            /*
            // Process query
            ArrayList<Product> products = new ArrayList<Product>();
            while (rs.next()) {
                Product p = new Product();
                p.CoffeeProduct = rs.getString("CoffeeProduct");
                p.ImageUrl = rs.getString("ImageUrl");
                p.Description = rs.getString("Description");
                p.ProductTypeId = rs.getInt("ProductTypeId");
                p.SKU = rs.getString("SKU");
                products.add(p);
            }*/

            // Close connection
            //conn.close();

            // Return the products
        } catch (Exception e) {
            System.out.println("Did not query data successfully.");
        }
    }

}