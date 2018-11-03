import java.io.*;

/**
 * created by zlk on 2018/10/28
 */
public class Operation {
    char[] input = new char[1000];
    /*关键字*/
    String[] keywords = {"abstract","public","assert","boolean","break","byte","case","catch","char","class","const","continue","default","do","double","else","enum","extends","final","finally","float","for","goto","if","implements","import","instanceof","int","interface","long","native","new","package","private","protected","return","strictfp","short","static","super","switch","synchronized","this","throw","throws","transient","try","void","volatile","while","String"};
    /*分隔符*/
    String[] delimiter = {"{","}",";",",","(",")"};
    /*操作符*/
    String[] opearator = {"+","-","*","/","=","==","+=","-=","*=","/=","|","&","&&","||","<","<=",">",">="};

    /**
     * 读取文件内容
     */
    void readFile(){
        try {
            FileReader reader = new FileReader(new File("content.txt"));
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            int index = -1;
            while((line=bufferedReader.readLine())!=null){
                char[] tmpLine = line.toCharArray();
                for(char c:tmpLine){
                    input[++index] = c;
                }
                input[++index] = '\n';
            }
            input[++index] = '#';
            bufferedReader.close();
//            System.out.println(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 词法分析
     */
    void LexicalAnalysis(){
        int index = 0;
        char c = input[index];
        String str = "";
        while(c!='#'){
            if((c>='a'&&c<='z')||(c>='A'&&c<='Z')){
                boolean isKeyword = false;
                int keyword_index = 0;
                while((c>='a'&&c<='z')||(c>='A'&&c<='Z')){
                    str += c;
                    for(int i=0;i<keywords.length;i++){
                        if(str.equals(keywords[i])){
                            isKeyword = true;
                            keyword_index = i;
                            break;
                        }
                    }
                    c = input[++index];
                    if(isKeyword){
                        break;
                    }
                }

                if(isKeyword){
                    Token token = new Token(str,1,keywords[keyword_index]);
                    System.out.println(token.toString());
                }else{
                    Token token = new Token(str,3);
                    System.out.println(token.toString());
                }
                str = "";
            }else if(c>='0'&&c<='9'||c=='.'){
                while(c>='0'&&c<='9'){
                    str += c;
                    c = input[++index];
                }
                Token token = new Token(str,4);
                System.out.println(token.toString());
                str="";
            }else{
                switch (c){
                    //分隔符
                    case '{':
                    case '}':
                    case ';':
                    case ',':
                    case '(':
                    case ')':
                        for(int i=0;i<delimiter.length;i++){
                            if(c==delimiter[i].charAt(0)){
                                Token token = new Token(delimiter[i],0);
                                System.out.println(token.toString());
                                break;
                            }
                        }
                        c = input[++index];
                        break;
                    //操作符
                    case '+':
                    case '-':
                    case '*':
                    case '=':
                    case '<':
                    case '>':
                    case '|':
                    case '&':
                        boolean isOperator = false;
                        char first_c = c;
                        str += first_c;
                        c = input[++index];
                        if(c=='='||c=='|'||c=='&'){
                            str += c;
                            for(int i=0;i<opearator.length;i++){
                                if(str.equals(opearator[i])){
                                    isOperator = true;
//                                    operator_index = i;
                                    Token token = new Token(opearator[i],2);
                                    System.out.println(token.toString());
                                    break;
                                }
                            }
                        }else{
                            index--;
                        }
                        if(!isOperator){
                            for(int i=0;i<opearator.length;i++){
                                if(str.equals(opearator[i])){
                                    Token token = new Token(opearator[i],2);
                                    System.out.println(token.toString());
                                    break;
                                }
                            }
                        }
                        c = input[++index];
                        str = "";
                        break;
                    case '/':
                        c = input[++index];
                        if(c == '*') {
                            while(!(c=='*'&&input[index+1]=='/')){
                                c = input[++index];
                            }
                            index += 2;
                            c = input[index];
                        }else if(c == '/'){
                            while(c!='\n'){
                                c = input[++index];
                            }
                        } else if(c=='='){
                            Token token = new Token("/=",2);
                            System.out.println(token.toString());
                        } else{
                            index--;
                            Token token = new Token("/",2);
                            System.out.println(token.toString());
                        }
                        break;
                    default:
                        c = input[++index];
                        break;
                }
            }
        }
    }
}
