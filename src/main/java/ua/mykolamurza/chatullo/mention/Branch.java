package ua.mykolamurza.chatullo.mention;

public class Branch {
    public char c;
    public Branch[] sub;

    // In some cases, this doesn't mean that there aren't more sub-branches.
    // For example, if we have two names: example and example1, we need to set
    // last 'e' branch isEnd to true to be able to match it.
    public boolean isEnd = false;

    public Branch(char c, Branch[] sub) {
        this.c = c;
        this.sub = sub;
    }
}
