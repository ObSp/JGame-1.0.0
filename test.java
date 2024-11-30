import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.UI.UIText;
import JGamePackage.JGame.Classes.World.Text2D;
import JGamePackage.JGame.Types.Constants.Constants;

public class test {
    static JGame game = new JGame();
    public static void main(String[] args) {
        Text2D text = new Text2D();
        text.Text = "HELLOOOOOO";
        text.TextScaled = true;
        text.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
        text.SetParent(game.WorldNode);

        game.Services.TimeService.OnTick.Connect(dt->{
            game.Camera.Position = game.Camera.Position.add(game.Services.InputService.GetInputHorizontal()*10, -game.Services.InputService.GetInputVertical()*10);
        });
    }
}
