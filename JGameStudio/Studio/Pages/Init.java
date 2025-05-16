package JGameStudio.Studio.Pages;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.UI.UIImage;
import JGamePackage.JGame.Classes.UI.UIText;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import JGamePackage.lib.Signal.VoidSignal;
import JGameStudio.StudioGlobals;

public class Init {
    public static VoidSignal showLoadingFrame(JGame game) {
        VoidSignal finished = new VoidSignal();

        game.Services.WindowService.SetWindowIcon("JGameStudio\\Assets\\Logo.png");

        JFrame window = game.GetWindow();
        window.dispose();

        window.setExtendedState(JFrame.NORMAL);
        window.setSize((int) (150 * 4), (int) (40 * 4));
        window.setResizable(false);
        window.setAlwaysOnTop(true);
        window.setUndecorated(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        game.Services.WindowService.BackgroundColor = new Color(0, 0, 0, 0);

        UIImage bannerImage = new UIImage();
        bannerImage.Size = UDim2.fromScale(1,1);
        bannerImage.SetImage("JGameStudio\\Assets\\jgameBannerBackground.png");
        bannerImage.BackgroundTransparency = 1;
        bannerImage.PixelPerfect = true;
        bannerImage.SetParent(game.UINode);

        UIText header = new UIText();
        header.CustomFont = StudioGlobals.GlobalFont;
        header.Size = UDim2.fromScale(1, .3);
        header.ZIndex = 1;
        header.BackgroundTransparency = 1;
        header.Text = "JGame Studio";
        header.TextScaled = true;
        header.FontStyle = Font.BOLD;
        header.AnchorPoint = Vector2.half;
        header.CustomFont = StudioGlobals.StartupFont;
        header.Position = UDim2.fromScale(.5, .5);
        header.TextColor = new Color(0);
        header.SetParent(game.UINode);

        game.Services.TimeService.DelaySeconds(3, ()-> {
            window.dispose();
            window.setSize(500, 500);
            window.setExtendedState(JFrame.MAXIMIZED_BOTH);
            window.setIconImage(new ImageIcon("JGamePackage\\JGame\\Assets\\icon.png").getImage());
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setUndecorated(false);
            window.setResizable(true);
            window.setAlwaysOnTop(false);

            window.setLocationRelativeTo(null);

            window.setVisible(true);

            game.UINode.DestroyChildren();
            finished.Fire();
        });

        return finished;
    }
}
