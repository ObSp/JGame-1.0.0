import java.util.Arrays;

import JGamePackage.JGame.Classes.Instance;

public class test {
    public static void main(String[] args) {
        Instance x = new Instance();
        x.Name = "x";

        Instance y = new Instance();
        y.Name = "y";
        y.SetParent(x);

        Instance z = new Instance();
        z.Name = "z";
        z.SetParent(y);
    }
}
