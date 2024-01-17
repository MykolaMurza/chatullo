package ua.mykolamurza.chatullo.mentions;

public class Branch {

    public char c;
    public Branch[] sub;

    public Branch(char c, Branch[] sub){
        this.c = c;
        this.sub = sub;
    }

}
