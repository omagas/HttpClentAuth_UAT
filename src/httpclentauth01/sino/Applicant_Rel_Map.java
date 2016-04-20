/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package httpclentauth01.sino;

//import java.util.Map;

public enum Applicant_Rel_Map {

    getRelation11("01"),//8ªk©wÄ~©Ó¤H99
    getRelation12("02"),//2 °t°¸02
    getRelation13("03"),//3 ¤l¤k03
    getRelation14("04"),//4 ¤÷¥À04
    getRelation15("00"),//5 ¯ª¤÷¥À00
    getRelation16("00");//6 ¥S§Ì©j©f00
    //public static final String TableName = "Chl_Map_Prdt_Tb";
    
    private final String value;

    Applicant_Rel_Map(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}