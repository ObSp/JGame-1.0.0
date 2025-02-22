package JGameStudio.Studio.Components;

import java.awt.Color;
import java.lang.reflect.Field;

import JGamePackage.JGame.Classes.Instance;
import JGamePackage.JGame.Classes.Modifiers.AspectRatioConstraint;
import JGamePackage.JGame.Classes.Modifiers.CornerEffect;
import JGamePackage.JGame.Classes.Modifiers.ListLayout;
import JGamePackage.JGame.Classes.UI.UIButton;
import JGamePackage.JGame.Classes.UI.UIFrame;
import JGamePackage.JGame.Classes.UI.UIScrollFrame;
import JGamePackage.JGame.Classes.UI.UIText;
import JGamePackage.JGame.Classes.UI.UITextInput;
import JGamePackage.JGame.Types.Constants.Constants;
import JGamePackage.JGame.Types.PointObjects.UDim;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import JGamePackage.lib.Signal.AbstractSignal;
import JGamePackage.lib.Signal.AbstractSignalWrapper;
import JGameStudio.StudioGlobals;
import JGameStudio.Studio.Modules.Selection;

public class Properties extends UIFrame {

    public UITextInput filter;

    private UIScrollFrame propsFrame;

    private UIText header;

    private int fieldFontSize = 14;

    public Properties() {
        this.Size = UDim2.fromScale(1, .45);
        this.Position = UDim2.fromScale(0, .55);
        this.BackgroundColor = StudioGlobals.BackgroundColor;
        this.ZIndex = 2;
        createHeader();
    }
        
    private void createHeader() {
        UIFrame headerBackground = new UIFrame();
        headerBackground.Size = UDim2.fromScale(1, 0).add(UDim2.fromAbsolute(0, 25));
        headerBackground.BackgroundColor = StudioGlobals.GrayColor.darker();
        headerBackground.SetParent(this);

        header = new UIText();
        header.Size = UDim2.fromScale(1, 1);
        header.BackgroundTransparency = 1;
        header.Text = "Properties";
        header.TextColor = StudioGlobals.TextColor;
        header.FontSize = 20;
        header.CustomFont = StudioGlobals.GlobalFont;
        header.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
        header.Position = UDim2.fromAbsolute(10, 0);
        header.SetParent(headerBackground);

        UIFrame line = new UIFrame();
        line.Size = UDim2.fromScale(1, 0).add(UDim2.fromAbsolute(0, 1));
        line.BackgroundColor = StudioGlobals.ForegroundColor;
        line.Position = UDim2.fromScale(0, 1);
        line.SetParent(headerBackground);

        UIFrame topLine = line.Clone();
        topLine.Position = UDim2.zero;
        topLine.SetParent(headerBackground);

        UIFrame filterBackground = headerBackground.Clone();
        filterBackground.Position = filterBackground.Position.add(UDim2.fromAbsolute(0, filterBackground.Size.Y.Absolute));
        filterBackground.BackgroundColor = filterBackground.BackgroundColor.darker();
        filterBackground.Size = filterBackground.Size.subtract(UDim2.fromAbsolute(0, 3));
        filterBackground.ZIndex = 2;
        filterBackground.SetParent(this);

        filter = new UITextInput();
        filter.Size = UDim2.fromScale(1, 1);
        filter.BackgroundTransparency = 1;
        filter.PlaceholderText = "Filter Properties";
        filter.TextColor = StudioGlobals.TextColor;
        filter.FontSize = 17;
        filter.CustomFont = StudioGlobals.GlobalFont;
        filter.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
        filter.Position = UDim2.fromAbsolute(10, 0);
        filter.SetParent(filterBackground);

        propsFrame = new UIScrollFrame();
        propsFrame.Position = filterBackground.Position.add(new UDim2(UDim.zero, filterBackground.Size.Y));
        propsFrame.Size = UDim2.fromScale(1, .9);
        propsFrame.BackgroundTransparency = 1;
        propsFrame.SetParent(this);

        ListLayout listLayout = new ListLayout();
        listLayout.Padding = UDim2.zero;
        listLayout.SetParent(propsFrame);

        filterBackground.GetChildOfClass(UIText.class).Destroy();

        Selection.InstanceSelected.Connect(inst -> {
            try {
                UpdateWindow(inst);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        Selection.InstanceDeselected.Connect(inst -> {
            try {
                UpdateWindow(Selection.getFirst());
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private UIFrame createFieldBaseFrame(String fieldName) {
        UIFrame f = new UIFrame();
        f.Size = UDim2.fromScale(1, 0).add(UDim2.fromAbsolute(0, 25));
        f.BackgroundTransparency = 1;

        UIText propName = new UIText();
        propName.Text = fieldName;
        propName.TextColor = StudioGlobals.TextColor;
        propName.FontSize = fieldFontSize;
        propName.CustomFont = StudioGlobals.GlobalFont;
        propName.BackgroundTransparency = 1;
        propName.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
        propName.Position = UDim2.fromScale(.02, 0);
        propName.Size = UDim2.fromScale(.5 - propName.Position.X.Scale, 1);
        propName.SetParent(f);

        UIFrame line = new UIFrame();
        line.Size = UDim2.fromScale(1, 0).add(UDim2.fromAbsolute(0, 1));
        line.BackgroundColor = new Color(23, 23, 23);
        line.AnchorPoint = new Vector2(0, 1);
        line.Position = UDim2.fromScale(0, 1);
        line.SetParent(f);

        UIFrame seperator = line.Clone();
        seperator.Position = UDim2.fromScale(.5, 0);
        seperator.AnchorPoint = new Vector2(.5, 0);
        seperator.Size = UDim2.fromScale(0, 1).add(UDim2.fromAbsolute(1, 0));
        seperator.SetParent(f);

        return f;
    }

    private UIFrame createStringField(String fieldName, String val, Field field, Instance inst) {
        UIFrame f = createFieldBaseFrame(fieldName);

        UITextInput inp = new UITextInput();
        inp.Text = val;
        inp.TextColor = StudioGlobals.TextColor;
        inp.FontSize = fieldFontSize;
        inp.BackgroundTransparency = 1;
        inp.CustomFont = StudioGlobals.GlobalFont;
        inp.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
        inp.Position = UDim2.fromScale(.52, 0);
        inp.Size = UDim2.fromScale(.5, 1);
        inp.SetParent(f);

        inp.FocusChanged.Connect(focused -> {
            if (focused) return;
            try {
                field.set(inst, inp.Text);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return f;
    }

    private UIFrame createBooleanField(String fieldName, boolean val, Field field, Instance inst) {
        UIFrame f = createFieldBaseFrame(fieldName);

        UIButton toggle = new UIButton();
        toggle.Size = UDim2.fromScale(0, .6);
        toggle.AnchorPoint = new Vector2(0, .5);
        toggle.Position = UDim2.fromScale(.52, .5);
        toggle.BackgroundColor = StudioGlobals.RedColor;
        toggle.SetParent(f);

        if (val) {
            toggle.BackgroundColor = StudioGlobals.GreenColor;
        }

        toggle.Mouse1Down.Connect(()-> {
            try {
                field.set(inst, !((boolean) field.get(inst)));
                toggle.BackgroundColor = (boolean) field.get(inst) ? StudioGlobals.GreenColor : StudioGlobals.RedColor;
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        CornerEffect corner = new CornerEffect();
        corner.Radius = .5;
        corner.SetParent(toggle);

        new AspectRatioConstraint().SetParent(toggle);

        return f;
    }

    private UIFrame createNumberField(String fieldName, Number val, Field field, Instance inst) {
        UIFrame f = createFieldBaseFrame(fieldName);

        UITextInput inp = new UITextInput();
        inp.Text = ""+val;
        inp.TextColor = StudioGlobals.TextColor;
        inp.FontSize = fieldFontSize;
        inp.BackgroundTransparency = 1;
        inp.CustomFont = StudioGlobals.GlobalFont;
        inp.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
        inp.Position = UDim2.fromScale(.52, 0);
        inp.Size = UDim2.fromScale(.5, 1);
        inp.SetParent(f);

        inp.FocusChanged.Connect(focused -> {
            if (focused) return;
            try {
                System.out.println(field.getType());
                if (field.getType().getSimpleName() == "int") {
                    field.set(inst, Integer.valueOf(inp.Text));
                } else if (field.getType().getSimpleName() == "double") {
                    field.set(inst, Double.valueOf(inp.Text));
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return f;
    }

    private void UpdateWindow(Instance cur) throws IllegalArgumentException, IllegalAccessException {
        for (Instance v : propsFrame.GetChildrenOfClass(UIFrame.class)) {
            v.Destroy();
        }

        if (cur == null) return;
        header.Text = "Properties - "+cur.getClass().getSimpleName()+ " \"" + cur.Name + "\"";

        for (Field f : cur.getClass().getFields()) {
            Class<?> type = f.getType();
       
            if (AbstractSignal.class.isAssignableFrom(type) || AbstractSignalWrapper.class.isAssignableFrom(type)) continue;

            String name = f.getName();
            Object curValue = f.get(cur);

            if (curValue instanceof String) {
                createStringField(name, (String) curValue, f, cur).SetParent(propsFrame);
            } else if (curValue instanceof Boolean) {
                createBooleanField(name, (Boolean) curValue, f, cur).SetParent(propsFrame);
            } else if (curValue instanceof Number) {
                createNumberField(name, (Number) curValue, f, cur).SetParent(propsFrame);
            }
        }
    }
}
