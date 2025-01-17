package JGameStudio.JGameHub;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.UI.UIBase;
import JGamePackage.JGame.Classes.UI.UIFrame;
import JGamePackage.JGame.Classes.UI.UIText;
import JGamePackage.JGame.Classes.UI.Modifiers.UIBorder;
import JGamePackage.JGame.Classes.UI.Modifiers.UICorner;
import JGamePackage.JGame.Classes.World.Box2D;
import JGamePackage.JGame.Types.Constants.Constants;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import JGamePackage.JGame.Types.StartParams.StartParams;
import JGameStudio.StudioGlobals;
import JGameStudio.StudioUtil;

public class JGameHub {
    
    JGame game;

    int windowWidth;
    int windowHeight;

    private void initWindow() {
        JFrame window = game.GetWindow();

        window.getRootPane().putClientProperty("JRootPane.titleBarBackground", StudioGlobals.BackgroundColor);
        window.getRootPane().putClientProperty("JRootPane.titleBarForeground", Color.lightGray);
        window.getRootPane().putClientProperty("JRootPane.menuBarEmbedded", true);

        window.setUndecorated(false);
        window.setSize(1100, 650);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);

        window.setAlwaysOnTop(true);

        game.Services.WindowService.SetWindowIcon("JGameStudio\\Assets\\Logo.png");
        game.Services.WindowService.BackgroundColor = StudioGlobals.BackgroundColor;

        game.Services.WindowService.SetWindowTitle("JGame Studio Hub");

        window.setVisible(true);
        window.setAlwaysOnTop(false);
    }

    private UDim2 im2Scale(double x, double y) {
        return UDim2.fromScale(x, y);
    }

    private UDim2 im2Abs(double x, double y) {
        return UDim2.fromAbsolute(x, y);
    }

    private UDim2 im2ScaleToAbs(double x, double y, UIBase parent) {
        return StudioUtil.UDim2ScaleToAbsolute(UDim2.fromScale(x, y), parent);
    }

    private UIFrame createFrame(UDim2 pos, UDim2 size, Color color, int zindex) {
        UIFrame frame = new UIFrame();
        frame.Position = pos;
        frame.Size = size;
        frame.BackgroundColor = color;
        frame.ZIndex = zindex;
        return frame;
    }

    private UIText createText(UDim2 pos, UDim2 size, Color color, String contents) {
        UIText text = new UIText();
        text.TextScaled = true;
        text.CustomFont = StudioGlobals.GlobalFont;
        text.Position = pos;
        text.BackgroundTransparency = 1;
        text.TextColor = color;
        text.Size = size;
        text.Text = contents;
        return text;
    }

    private void createLines() {
        Color color = StudioGlobals.ForegroundColor;
        int zIndex = 10;

        createFrame(UDim2.zero, UDim2.fromAbsolute(windowWidth, 1), color, zIndex).SetParent(game.UINode);
        createFrame(UDim2.fromAbsolute(250, 0), UDim2.fromAbsolute(1, windowHeight), color, zIndex).SetParent(game.UINode);
        createFrame(UDim2.fromAbsolute(250, 50), UDim2.fromAbsolute(windowWidth, 1), color, zIndex).SetParent(game.UINode);
    }

    private UIFrame createSearchBar(UIFrame area) {
        UIFrame container = new UIFrame();
        container.Size = im2ScaleToAbs(.8, .06, area);
        container.Position = im2ScaleToAbs(.5, .1, area);
        container.AnchorPoint = new Vector2(.5, 0);
        container.BackgroundColor = StudioGlobals.BackgroundColor;

        UIBorder border = new UIBorder();
        border.BorderColor = StudioGlobals.ForegroundColor;
        border.Width = 1;
        border.SetParent(container);

        UICorner corner = new UICorner();
        corner.Radius = .5;
        corner.RelativeTo = Constants.Vector2Axis.Y;
        corner.SetParent(container);
        return container;
    }

    private void createMainArea() {
        UIFrame container = new UIFrame();
        container.Position = UDim2.fromAbsolute(250, 1);
        container.Size = UDim2.fromAbsolute(windowWidth-250, windowHeight-1);
        container.BackgroundTransparency = .95;
        container.SetParent(game.UINode);
        
        UIText header = createText(im2Scale(.5, 0).add(im2Abs(0, 15)), im2ScaleToAbs(.95, .04, container) , Color.lightGray.brighter(), "My Games");
        header.AnchorPoint = new Vector2(.5, 0);
        header.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
        header.SetParent(container);

        UIFrame searchBar = createSearchBar(container);
        searchBar.SetParent(container);
    }

    public JGameHub() {
        FlatDarculaLaf.setup();

        game = new JGame(new StartParams(false));
        StudioUtil.game = game;

        initWindow();

        windowWidth = game.Services.WindowService.GetWindowWidth();
        windowHeight = game.Services.WindowService.GetWindowHeight();

        createLines();
        
        createMainArea();
    }

}
