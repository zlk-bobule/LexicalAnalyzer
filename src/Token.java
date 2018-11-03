/**
 * created by zlk on 2018/10/28
 */
public class Token {
    private String[] typestr = {"定界符","关键字","操作符","普通词","数字"};
    /**
     * 词
     */
    private String str;
    /**
     * 种类
     */
    private String type;
    private String kind;
    private int code;

    public Token(String str,int type,String kind){
        this.str = str;
        this.type = typestr[type];
        this.kind = kind;
        this.code = 0;
    }

    public Token(String str,int type){
        this.str = str;
        this.type = typestr[type];
        this.code = 1;
    }

    public String toString(){
        String token = "";
        if(code==0){
            token = "'"+str+"' : '"+type+"','"+kind+"'";
        }else{
            token = "'"+str+"' : '"+type+"'";
        }
        return token;
    }
}
