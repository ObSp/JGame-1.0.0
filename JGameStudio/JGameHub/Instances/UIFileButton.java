package JGameStudio.JGameHub.Instances;

import java.awt.Font;

import javax.swing.JFileChooser;

import JGamePackage.JGame.Classes.UI.UIButton;
import JGamePackage.JGame.Classes.UI.UIText;
import JGamePackage.JGame.Classes.UI.Modifiers.UIBorder;
import JGamePackage.JGame.Classes.UI.Modifiers.UICorner;
import JGamePackage.JGame.Types.Constants.Constants;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.lib.Signal.Signal;
import JGameStudio.StudioGlobals;

public class UIFileButton extends UIButton {
    private UIText location;
    private UIText locationHeader;

    private String curPath;

    private boolean pickerOpen = false;

    public int MaxVisibleLetters = 30;

    public Signal<String> FilePicked = new Signal<>();

    public String ConfirmButtonText = "Pick File";

    public UIFileButton() {
        this.BackgroundColor = StudioGlobals.BackgroundColor.darker();
        this.HoverColor = StudioGlobals.BackgroundColor.brighter();

        UIBorder border = new UIBorder();
        border.BorderColor = StudioGlobals.ForegroundColor;
        border.Width = 1;
        border.SetParent(this);

        UICorner corner = new UICorner();
        corner.Radius = .3;
        corner.SetParent(this);

        locationHeader = new UIText();
        locationHeader.MouseTargetable = false;
        locationHeader.TextColor = StudioGlobals.ForegroundColor;
        locationHeader.Text = "Location";
        locationHeader.CustomFont = StudioGlobals.GlobalFont;
        locationHeader.TextScaled = true;
        locationHeader.FontStyle = Font.BOLD;
        locationHeader.Size = UDim2.fromScale(1, .2);
        locationHeader.Position = UDim2.fromScale(.06, .2);
        locationHeader.BackgroundTransparency = 1;
        locationHeader.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
        locationHeader.SetParent(this);

        location = locationHeader.Clone();
        location.TextColor = StudioGlobals.TextColor;
        location.FontStyle = Font.PLAIN;
        location.Position = location.Position.add(UDim2.fromScale(0, .3));
        location.Size = UDim2.fromScale(1, .22);
        location.SetParent(this);

        this.SetCurrentPath("C:\\Users\\Paul\\Documents\\JGameStudio\\test");

        this.Mouse1Down.Connect(()->{
            if (pickerOpen) return;

            pickerOpen = true;

            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
            int returnVal = chooser.showDialog(game.GetWindow(), ConfirmButtonText);

            pickerOpen = false;

            if (returnVal != JFileChooser.APPROVE_OPTION) return;

            String pickedPath = chooser.getSelectedFile().getAbsolutePath();

            this.SetCurrentPath(pickedPath);

            this.FilePicked.Fire(pickedPath);
        });
    }

    public String GetCurrentPath() {
        return curPath;
    }

    public void SetCurrentPath(String path) {
        curPath = path;
        if (path.length() > MaxVisibleLetters)
            path = path.substring(0, MaxVisibleLetters) + "...";

        location.Text = path;
    }

    public void SetLocationHeaderText(String text) {
        this.locationHeader.Text = text;
    }

    @Override
    public UIFileButton Clone() {
        UIFileButton clone = cloneWithoutChildren();
        //this.cloneHierarchyToNewParent(clone);
        return clone;
    }

    @Override
    protected UIFileButton cloneWithoutChildren() {
        UIFileButton frame = new UIFileButton();
        this.cloneHelper(frame);
        frame.HoverColor = this.HoverColor;
        frame.HoverEffectsEnabled = this.HoverEffectsEnabled;
        frame.MaxVisibleLetters = this.MaxVisibleLetters;
        frame.ConfirmButtonText = this.ConfirmButtonText;
        return frame;
    }
}
