import java.util.*;
import edu.duke.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder msg=new StringBuilder();
        for(int i=whichSlice;i<message.length();i+=totalSlices){
            msg.append(message.charAt(i));
        }
        return msg.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        CaesarCracker cc=new CaesarCracker();
        for(int i=0;i<klength;i++){
            String slice=sliceString(encrypted,i,klength);
            key[i]=cc.getKey(slice);
        }
        return key;
    }
    public HashSet<String> readDictionary(FileResource fr){
        HashSet<String> dic=new HashSet<String>();
        for(String line:fr.lines()){
            line=line.toLowerCase();
            dic.add(line);
        }
        return dic;
    }
    public int countWords(String message, HashSet<String> dic){
        int cnt=0;
        for(String word: message.split("\\W")){
            if(dic.contains(word.toLowerCase()))cnt++;
        }
        return cnt;
    }
    public void breakForLanguage(FileResource fr, HashSet<String> dic){
        int cnt=0,mkey=0;
        String real="";
        char comm='e';
        String encrypted=fr.asString();
        
        for(int klength=1;klength<=100;klength++){
            int[] key=new int[klength];
            key=tryKeyLength(encrypted,klength,comm);
            VigenereCipher vc= new VigenereCipher(key);
            String dec= vc.decrypt(encrypted);
            int x=countWords(dec,dic);
            if(cnt<x){cnt=x;real=dec;mkey=klength;}
        }
        System.out.println("\tCharacter matched: "+cnt+"\tKeyLength: "+mkey);
        System.out.println("\nFirst Line:\n"+real.substring(0,150)+"\n\n");
    }
    
    public void tester(){
        int i=2;
        String[] coun={"Danish","Dutch","English","French","German","Italian","Portuguese","Spanish"};
        for(i=0;i<8;i++){
            FileResource fr=new FileResource("dictionaries/"+coun[i]);
            FileResource fr2=new FileResource("messages/secretmessage4.txt");
            HashSet<String> dic=readDictionary(fr);
            System.out.print(coun[i]);
            breakForLanguage(fr2,dic);
        }
    }
/*    public char mostCommonChar(HashSet<String>dic){
        char ch='e';
        for(ch:dic){
            System.out.println(ch);
        }
        return ch;
    }
    
    public void test(){
        FileResource fr=new FileResource("dictionaries/English");
        FileResource fr2=new FileResource("messages/secretmessage2.txt");
        HashSet<String> dic=readDictionary(fr);
        String enc=fr2.asString();
        
        char ch=mostCommonChar(dic);
        
        //breakForLanguage(enc,dic);
    }
*/    
}























