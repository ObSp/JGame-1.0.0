import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.World.Box2D;

public class test {
    static JGame game = new JGame();
    public static void main(String[] args) {
        Box2D b = new Box2D();
        b.SetParent(game.WorldNode);

        game.Services.TimeService.OnTick.Connect(dt->{
            game.Camera.Position = game.Camera.Position.add(game.Services.InputService.GetInputHorizontal()*10, -game.Services.InputService.GetInputVertical()*10);
        });
    }
}
