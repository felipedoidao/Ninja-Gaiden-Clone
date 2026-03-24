package World;

public class Camera {

    public static int x = 0;
    public static int y = 0;

    public static int clamp(int Actual, int Min, int Max){
        if (Actual < Min){
            Actual = Min;
        }

        if (Actual > Max){
            Actual = Max;
        }

        return Actual;
    }
}
