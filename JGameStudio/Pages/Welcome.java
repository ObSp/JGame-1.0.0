package JGameStudio.Pages;

import java.awt.Color;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.UI.UIBase;
import JGamePackage.JGame.Classes.UI.UIButton;
import JGamePackage.JGame.Classes.UI.UIFrame;
import JGamePackage.JGame.Classes.UI.UIText;
import JGamePackage.JGame.Classes.UI.Modifiers.UICorner;
import JGamePackage.JGame.Classes.UI.Modifiers.UIListLayout;
import JGamePackage.JGame.Types.Constants.Constants;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGameStudio.StudioGlobals;
import JGameStudio.StudioUtil;

public class Welcome {

    static UIFrame createGameFrame(UIBase parent) {
        UIButton frame = new UIButton();
        frame.Size = StudioUtil.UDim2ScaleToAbsolute(UDim2.fromScale(.05, 1), parent);
        frame.BackgroundColor = StudioGlobals.ForegroundColor.darker();
        frame.HoverColor = StudioGlobals.ForegroundColor;

        UICorner corner = new UICorner();
        corner.SetParent(frame);

        return frame;
    }

    static UIFrame createMainListFrame(UIBase parent) {
        UIFrame frame = new UIFrame();
        frame.Size = StudioUtil.UDim2ScaleToAbsolute(UDim2.fromScale(3, .4), parent);
        frame.BackgroundTransparency = 1;
        frame.SetParent(parent);
        
        UIText header = new UIText();
        header.BackgroundTransparency = 1;
        header.TextScaled = true;
        header.TextColor = Color.white;
        header.Size = StudioUtil.UDim2ScaleToAbsolute(UDim2.fromScale(1, .07), frame);
        header.Position = UDim2.fromAbsolute(50, 20);
        header.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
        header.CustomFont = StudioGlobals.GlobalFont;
        header.Text = "Recent Games";
        header.Name = "Header";
        header.SetParent(frame);

        //Max items: 6
        UIFrame list = new UIFrame();
        list.Position = header.Position.add(UDim2.fromAbsolute(0, header.Size.Y.Absolute + 20));
        list.Size = StudioUtil.UDim2ScaleToAbsolute(UDim2.fromScale(1, .8), frame);
        list.BackgroundTransparency = 1;
        list.Name = "List";
        list.SetParent(frame);

        UIListLayout listLayout = new UIListLayout();
        listLayout.ItemAlignment = Constants.ListAlignment.Horizontal;
        listLayout.Padding = UDim2.fromAbsolute(15, 0);
        listLayout.SetParent(list);

        return frame;
    }

    static UIFrame createRecentFrame(UIBase parent) {
        UIFrame frame = createMainListFrame(parent);
        UIFrame list = frame.<UIFrame>GetTypedChild("List");

        for (int i = 0; i < 6; i++) {
            createGameFrame(list).SetParent(list);
        }

        return frame;
    }

    static UIFrame createTemplatesFrame(UIBase parent) {
        UIFrame frame = createMainListFrame(parent);
        UIFrame list = frame.<UIFrame>GetTypedChild("List");

        frame.<UIText>GetTypedChild("Header").Text = "Templates";

        for (int i = 0; i < 6; i++) {
            createGameFrame(list).SetParent(list);
        }

        return frame;
    }
    
    public static UIBase createWelcomePageNode(JGame game) {
        UIFrame container = new UIFrame();
        container.BackgroundColor = StudioGlobals.BackgroundColor;
        container.Size = UDim2.fromScale(100, 100);

        UIFrame leftMenu = new UIFrame();
        leftMenu.ZIndex = 2;
        leftMenu.BackgroundColor = StudioGlobals.ForegroundColor;
        leftMenu.Size = StudioUtil.UDim2ScaleToAbsolute(UDim2.fromScale(.15, 2));
        leftMenu.SetParent(container);

        UIFrame innerContainer = new UIFrame();
        innerContainer.Size = StudioUtil.UDim2ScaleToAbsolute(UDim2.fromScale(.85, 1));
        innerContainer.Position = StudioUtil.UDim2ScaleToAbsolute(UDim2.fromScale(.15, 0));
        innerContainer.BackgroundTransparency = 1;
        innerContainer.SetParent(container);

        UIListLayout listLayout = new UIListLayout();
        listLayout.SetParent(innerContainer);

        UIFrame recentFrame = createRecentFrame(innerContainer);
        UIFrame templateFrame = createTemplatesFrame(innerContainer);

        return container;
    }

}
