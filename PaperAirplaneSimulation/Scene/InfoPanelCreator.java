package PaperAirplaneSimulation.Scene;

import java.awt.Color;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.UI.UIFrame;
import JGamePackage.JGame.Classes.UI.UIText;
import JGamePackage.JGame.Types.Constants.Constants;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import PaperAirplaneSimulation.ExperimentData;

public class InfoPanelCreator {
    public static void createInfoPanel(JGame game) {
        UIFrame panel1 = new UIFrame();
        panel1.Size = UDim2.fromScale(.15, .25);
        panel1.AnchorPoint = new Vector2(1, 0);
        panel1.Position = UDim2.fromScale(1, .2);
        panel1.BackgroundColor = Color.black;
        panel1.BackgroundTransparency = .5;
        panel1.Name = "Panel1";
        panel1.SetParent(game.UINode);

        UIText header = new UIText();
        header.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Center;
        header.TextScaled = true;
        header.BackgroundTransparency = 1;
        header.Position = UDim2.fromScale(0, 0);
        header.Size = UDim2.fromScale(1, 0.06);
        header.TextColor = Color.white;
        header.Text = "Plane 1";
        header.Name = "Header";
        header.SetParent(panel1);

        UIText drag = new UIText();
        drag.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
        drag.TextScaled = true;
        drag.BackgroundTransparency = 1;
        drag.Position = UDim2.fromScale(.02, header.Size.Y.Scale + .02);
        drag.Size = UDim2.fromScale(1, 0.06);
        drag.TextColor = Color.white;
        drag.Text = "Drag: 0N";
        drag.Name = "Drag";
        drag.SetParent(panel1);

        UIText lift = drag.Clone();
        lift.Text = "Lift: 0N";
        lift.Name = "Lift";
        lift.Position = lift.Position.add(UDim2.fromScale(0, drag.Size.Y.Scale + .03));
        lift.SetParent(panel1);

        UIText weight = lift.Clone();
        weight.Text = "Weight: 0N";
        weight.Name = "Weight";
        weight.Position = weight.Position.add(UDim2.fromScale(0, drag.Size.Y.Scale + .03));
        weight.SetParent(panel1);

        UIText height = weight.Clone();
        height.Name = "Height";
        height.Text = "Height: "+ExperimentData.ref1throwHeight+"m";
        height.Position = height.Position.add(UDim2.fromScale(0, drag.Size.Y.Scale*2));
        height.SetParent(panel1);

        UIText displacement = height.Clone();
        displacement.Name = "Displacement";
        displacement.Text = "Displacement: 0m";
        displacement.Position = displacement.Position.add(UDim2.fromScale(0, drag.Size.Y.Scale + .03));
        displacement.SetParent(panel1);

        UIText angle = displacement.Clone();
        angle.Name = "Angle";
        angle.Text = "Angle of Attack(α): 0°";
        angle.Position = angle.Position.add(UDim2.fromScale(0, drag.Size.Y.Scale * 2));
        angle.SetParent(panel1);

        UIText time = angle.Clone();
        time.Name = "Time";
        time.Text = "Time: 0s";
        time.Position = time.Position.add(UDim2.fromScale(0, drag.Size.Y.Scale * 2));
        time.SetParent(panel1);

        UIText wind = time.Clone();
        wind.Name = "Wind";
        wind.Text = "Wind: 0m/s";
        time.Position = wind.Position.add(UDim2.fromScale(0, drag.Size.Y.Scale * 2));
        wind.SetParent(panel1);

        UIFrame panel2 = panel1.Clone();
        panel2.Position = UDim2.fromScale(1, .5);
        panel2.Name = "Panel2";
        panel2.<UIText>GetTypedChild("Header").Text = "Plane 2";
        panel2.<UIText>GetTypedChild("Header").TextColor = Color.yellow;
        panel2.<UIText>GetTypedChild("Height").Text = "Height: "+ExperimentData.ref2throwHeight+"m";
        panel2.SetParent(game.UINode);
    }
}
