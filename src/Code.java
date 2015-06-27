/**
 * Code used for encoding the data.
 * @author Chris Marriott
 * @author Casey Morrison and Vladimir Gudzyuk
 * @version Dec 4, 2013 TCSS 242A Fall 2013
 */
public class Code {
	String code;
	String word;
	public Code(String cde, String wrd){
		code = cde;
		word = wrd;
	}
	public String toString(){
		return "("+code+", "+word+")";
	}
}