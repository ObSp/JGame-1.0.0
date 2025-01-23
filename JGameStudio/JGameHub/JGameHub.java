package JGameStudio.JGameHub;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.formdev.flatlaf.FlatDarculaLaf;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.UI.UIBase;
import JGamePackage.JGame.Classes.UI.UIButton;
import JGamePackage.JGame.Classes.UI.UIFrame;
import JGamePackage.JGame.Classes.UI.UIImage;
import JGamePackage.JGame.Classes.UI.UIText;
import JGamePackage.JGame.Classes.UI.UITextButton;
import JGamePackage.JGame.Classes.UI.UITextInput;
import JGamePackage.JGame.Classes.UI.Modifiers.UIAspectRatioConstraint;
import JGamePackage.JGame.Classes.UI.Modifiers.UIBorder;
import JGamePackage.JGame.Classes.UI.Modifiers.UICorner;
import JGamePackage.JGame.Classes.UI.Modifiers.UIListLayout;
import JGamePackage.JGame.Types.Constants.Constants;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import JGamePackage.JGame.Types.StartParams.StartParams;
import JGameStudio.StudioGlobals;
import JGameStudio.StudioUtil;
import JGameStudio.Classes.DataReader;
import JGameStudio.JGameHub.Instances.UIFileButton;
import JGameStudio.JGameHub.ProjectHandler.ProjectHandler;
import JGameStudio.JGameHub.ProjectHandler.ProjectHandler.ProjectData;

public class JGameHub {
    
    JGame game;

    private DataReader jsonData = new DataReader(StudioGlobals.jsonData);

    private UIFrame newProjectPrompt;

    private UIFrame projectTable;

    int windowWidth;
    int windowHeight;

    private void createProj(String name, String location, String packageLocation) {
        File dir = ProjectHandler.Create(name, location, packageLocation);
        
        jsonData.Data.projects.add(0, dir.getAbsolutePath());
        
        jsonData.UpdateJSON();
        reloadProjectTableItems();
    }

    private Image getImageFromPath(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

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

        game.Services.WindowService.SetWindowIcon(getImageFromPath("JGameStudio\\Assets\\Logo.png"));
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
        int zIndex = 100;

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

        UITextInput input = new UITextInput();
        input.Size = UDim2.fromScale(.9, 1);
        input.AnchorPoint = Vector2.half;
        input.Position = UDim2.fromScale(.5, .5);
        input.BackgroundTransparency = 1;
        input.CustomFont = StudioGlobals.GlobalFont;
        input.TextColor = StudioGlobals.TextColor;
        input.PlaceholderText = "Search Projects";
        input.FontSize = 25;
        input.SetParent(container);


        return container;
    }

    private UITextButton createCreateButton(UIFrame area) {
        UITextButton create = new UITextButton();
        create.BackgroundColor = StudioGlobals.GreenColor;
        create.Size = UDim2.fromAbsolute(100, 34);
        create.Position = im2ScaleToAbs(.87, .011, area);
        create.Text = "Create";
        create.FontSize = 23;
        create.CustomFont = StudioGlobals.GlobalFont;
        create.TextColor = StudioGlobals.TextColor;
        create.ZIndex = 2;
        create.HoverColor = create.BackgroundColor.darker();

        UIBorder border = new UIBorder();
        border.BorderColor = StudioGlobals.ForegroundColor;
        border.Width = 1;
        border.SetParent(create);

        UICorner corner = new UICorner();
        corner.Radius = .2;
        corner.RelativeTo = Constants.Vector2Axis.Y;
        corner.SetParent(create);

        return create;
    }

    private UITextButton createAddButton(UIFrame area) {
        UITextButton button = createCreateButton(area);
        button.Position = button.Position.subtract(UDim2.fromAbsolute(85, 0));
        button.Size = button.Size.subtract(UDim2.fromAbsolute(30, 0));
        button.Text = "Add";
        button.BackgroundColor = StudioGlobals.BackgroundColor;
        button.HoverColor = button.BackgroundColor.brighter();
        return button;
    }

    private void createSettingssPage() {
        UIFrame container = new UIFrame();
        container.Position = UDim2.fromAbsolute(250, 1);
        container.Size = UDim2.fromAbsolute(windowWidth-250, windowHeight-1);
        container.BackgroundTransparency = .95;
        container.Name = "Settings";
        container.Visible = false;
        container.SetParent(game.UINode);
        
        UIText header = createText(im2Scale(.5, 0).add(im2Abs(0, 10)), im2ScaleToAbs(.95, .04, container) , Color.lightGray.brighter(), "Settings");
        header.AnchorPoint = new Vector2(.5, 0);
        header.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
        header.SetParent(container);

        UIFrame searchBar = createSearchBar(container);
        searchBar.GetChildWhichIsA(UITextInput.class).PlaceholderText = "Search Settings";
        searchBar.SetParent(container);
    }

    private void createInstallsPage() {
        UIFrame container = new UIFrame();
        container.Position = UDim2.fromAbsolute(250, 1);
        container.Size = UDim2.fromAbsolute(windowWidth-250, windowHeight-1);
        container.BackgroundTransparency = .95;
        container.Name = "Installs";
        container.Visible = false;
        container.SetParent(game.UINode);
        
        UIText header = createText(im2Scale(.5, 0).add(im2Abs(0, 10)), im2ScaleToAbs(.95, .04, container) , Color.lightGray.brighter(), "JGame Installation");
        header.AnchorPoint = new Vector2(.5, 0);
        header.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
        header.SetParent(container);

        UITextButton add = createAddButton(container);
        add.Text = "Locate";
        add.Size = add.Size.add(UDim2.fromAbsolute(50, 0));
        add.Position = add.Position.add(UDim2.fromAbsolute(67, 0));
        add.SetParent(container);
    }

    private UIFrame createNewProjectPrompt() {
        UIFrame frame = new UIFrame();
        frame.BackgroundTransparency = .3;
        frame.BackgroundColor = Color.black;
        frame.Size = UDim2.fromScale(1,1);
        frame.ZIndex = 100;
        frame.Visible = false;

        UIFrame container = new UIFrame();
        container.BackgroundColor = StudioGlobals.BackgroundColor;
        container.AnchorPoint = Vector2.half;
        container.Position = UDim2.fromScale(.5, .5);
        container.Size = UDim2.fromScale(.4, .7);
        container.SetParent(frame);

        UIBorder border = new UIBorder();
        border.Width = 1;
        border.BorderColor = StudioGlobals.ForegroundColor;
        border.SetParent(container);

        UICorner corner = new UICorner();
        corner.Radius = .05;
        corner.SetParent(container);

        UIText header = createText(im2Scale(.5, 0).add(im2Abs(0, 10)), im2ScaleToAbs(.95, .05, container) , Color.lightGray.brighter(), "Create Project");
        header.AnchorPoint = new Vector2(.5, 0);
        header.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
        header.SetParent(container);

        UITextInput nameInput = new UITextInput();
        nameInput.AnchorPoint = Vector2.half;
        nameInput.Size = UDim2.fromScale(.8, .1);
        nameInput.Position = UDim2.fromScale(.5, .17);
        nameInput.BackgroundColor = StudioGlobals.BackgroundColor;
        nameInput.FontSize = 20;
        nameInput.PlaceholderText = "Project Name";
        nameInput.CustomFont = StudioGlobals.GlobalFont;
        nameInput.TextColor = StudioGlobals.TextColor;
        nameInput.SetParent(container);
        
        UICorner inpCorner = new UICorner();
        inpCorner.Radius = .3;
        inpCorner.SetParent(nameInput);

        border.Clone().SetParent(nameInput);
        
        UIFileButton pathToProj = new UIFileButton();
        pathToProj.AnchorPoint = Vector2.half;
        pathToProj.Size = UDim2.fromScale(.8, .16);
        pathToProj.Position = UDim2.fromScale(.5, .37);
        pathToProj.ConfirmButtonText = "Pick Folder";
        pathToProj.SetCurrentPath(jsonData.Data.creation_path);
        pathToProj.SetParent(container);

        UIFileButton pathToInstallation = pathToProj.Clone();
        pathToInstallation.Position = pathToInstallation.Position.add(UDim2.fromScale(0, .2));
        pathToInstallation.SetLocationHeaderText("JGame Installation Location");
        pathToInstallation.ConfirmButtonText = "Select JGame Installation";
        pathToInstallation.SetCurrentPath(jsonData.Data.default_jgame_installation);
        pathToInstallation.SetParent(container);

        UITextButton create = createCreateButton(container);
        create.Size = UDim2.fromScale(.8, .1);
        create.AnchorPoint = Vector2.half;
        create.Position = pathToInstallation.Position.add(UDim2.fromScale(0, .2));
        create.GetChildWhichIsA(UICorner.class).Radius = inpCorner.Radius;
        create.SetParent(container);

        UITextButton cancel = create.Clone();
        cancel.Position = create.Position.add(UDim2.fromScale(0, .13));
        cancel.Text = "Cancel";
        cancel.BackgroundColor = StudioGlobals.RedColor;
        cancel.HoverColor = cancel.BackgroundColor.darker();
        cancel.SetParent(container);

        create.Mouse1Down.Connect(()-> {
            createProj(nameInput.Text, pathToProj.GetCurrentPath(), pathToInstallation.GetCurrentPath());
            frame.Visible = false;
            nameInput.Text = "";
        });

        cancel.Mouse1Down.Connect(()->{
            frame.Visible = false;
            nameInput.Text = "";
        });

        return frame;
    }

    private UIButton createProjectTableItem(ProjectData projData) {
        UIButton test = new UIButton();
        test.Size = UDim2.fromScale(.99, .1);
        test.BackgroundTransparency = 1;
        test.HoverEffectsEnabled = false;

        UICorner corner = new UICorner();
        corner.RelativeTo = Constants.Vector2Axis.Y;
        corner.Radius = .5;
        corner.SetParent(test);

        UIText testName = new UIText();
        testName.Text = projData.name();
        testName.BackgroundTransparency = 1;
        testName.AnchorPoint = new Vector2(0, .5);
        testName.Position = UDim2.fromScale(.1, .5);
        testName.CustomFont = StudioGlobals.GlobalFont;
        testName.Size = UDim2.fromScale(.16, 1);
        testName.FontSize = 18;
        testName.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
        testName.TextColor = StudioGlobals.TextColor;
        testName.FontStyle = Font.BOLD;
        testName.MouseTargetable = false;
        testName.SetParent(test);

        UIText testModified = testName.Clone();
        testModified.Text = projData.modifiedDate();
        testModified.Position = UDim2.fromScale(.28, .5);
        testModified.SetParent(test);

        UIText testCreated = testName.Clone();
        testCreated.Text = projData.creationDate();
        testCreated.Position = UDim2.fromScale(.505, .5);
        testCreated.SetParent(test);

        UITextButton testPath = new UITextButton();
        testPath.Text = projData.path();
        testPath.Position = UDim2.fromScale(.75, .5);
        testPath.AnchorPoint = new Vector2(0, .5);
        testPath.BackgroundTransparency = 1;
        testPath.CustomFont = StudioGlobals.GlobalFont;
        testPath.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
        testPath.TextColor = StudioGlobals.TextColor;
        testPath.FontSize = 18;
        testPath.HoverEffectsEnabled = false;
        testPath.Size = UDim2.fromScale(.24, .7);
        testPath.SetParent(test);

        corner.Clone().SetParent(testPath);

        if (testPath.Text.length() > 21) {
            testPath.Text = testPath.Text.substring(0, 20) +"...";
        }

        testPath.MouseEnter.Connect(()->{
            testPath.BackgroundTransparency = .9;
        });

        testPath.MouseLeave.Connect(()->{
            testPath.BackgroundTransparency = 1;
        });

        testPath.Mouse1Down.Connect(()->{
            try {
                Desktop.getDesktop().open(new File(projData.path()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        test.MouseEnter.Connect(()->{
            test.BackgroundTransparency = .95;
        });

        test.MouseLeave.Connect(()->{
            test.BackgroundTransparency = 1;
        });

        return test;
    }

    private void reloadProjectTableItems() {
        for (UIBase v : projectTable.GetChildrenOfClass(UIBase.class))
            v.Destroy();

        for (int i = 0; i < jsonData.Data.projects.size(); i++) {
            String projPath = jsonData.Data.projects.get(i);
            ProjectData proj = ProjectHandler.ReadProjectDir(projPath);
            createProjectTableItem(proj).SetParent(projectTable);
        }
    }

    private UIFrame createProjectTable(UIFrame area) {
        projectTable = new UIFrame();
        projectTable.Size = UDim2.fromScale(1, .73);
        projectTable.BackgroundTransparency = 1;
        projectTable.Position = UDim2.fromScale(.006, .28);

        UIListLayout layout = new UIListLayout();
        layout.SetParent(projectTable);

        reloadProjectTableItems();

        return projectTable;
    }

    private UIFrame createProjTableHead() {
        UIFrame bg = new UIFrame();
        bg.BackgroundColor = StudioGlobals.BackgroundColor;
        bg.Size = UDim2.fromScale(1, 0).add(im2Abs(0, 43));
        bg.Position = UDim2.fromAbsolute(1, 118);

        //top + bottom lines
        createFrame(UDim2.zero, UDim2.fromAbsolute(1000, 1), StudioGlobals.ForegroundColor, 1).SetParent(bg);
        createFrame(UDim2.fromScale(0, 1), UDim2.fromAbsolute(1000, 1), StudioGlobals.ForegroundColor, 1).SetParent(bg);

        //seperators
        createFrame(im2Abs(71, 0), im2Scale(0, 1).add(im2Abs(1, 0)), StudioGlobals.ForegroundColor, 1).SetParent(bg);
        createFrame(im2Abs(220, 0), im2Scale(0, 1).add(im2Abs(1, 0)), StudioGlobals.ForegroundColor, 1).SetParent(bg);
        createFrame(im2Abs(458-50, 0), im2Scale(0, 1).add(im2Abs(1, 0)), StudioGlobals.ForegroundColor, 1).SetParent(bg);
        createFrame(im2Abs(670-50, 0), im2Scale(0, 1).add(im2Abs(1, 0)), StudioGlobals.ForegroundColor, 1).SetParent(bg);

        //star icon
        UIImage star = new UIImage();
        star.SetImage("JGameStudio\\Assets\\Icons\\Star.png");
        star.Size = im2Abs(28, 28);
        star.BackgroundTransparency = 1;
        star.AnchorPoint = Vector2.half;
        star.Position = UDim2.fromScale(.043, .5);
        star.SetParent(bg);

        //name text
        UIText name = new UIText();
        name.Text = "Name";
        name.BackgroundTransparency = 1;
        name.AnchorPoint = new Vector2(0, .5);
        name.Position = UDim2.fromScale(.1, .5);
        name.CustomFont = StudioGlobals.GlobalFont;
        name.Size = UDim2.fromScale(.16, 1);
        name.FontSize = 22;
        name.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
        name.TextColor = StudioGlobals.TextColor;
        name.SetParent(bg);

        
        UIText modified = name.Clone();
        modified.Text = "Modified";
        modified.Position = modified.Position.add(im2Scale(.177, 0));
        modified.SetParent(bg);

        UIText created = modified.Clone();
        created.Text = "Created";
        created.Position = created.Position.add(im2Scale(.224, 0));
        created.SetParent(bg);

        UIText path = created.Clone();
        path.Text = "Location";
        path.Position = path.Position.add(im2Scale(.26, 0));
        path.SetParent(bg);

        return bg;
    }

    private void createHomeArea() {
        UIFrame container = new UIFrame();
        container.Position = UDim2.fromAbsolute(250, 1);
        container.Size = UDim2.fromAbsolute(windowWidth-250, windowHeight-1);
        container.BackgroundTransparency = .95;
        container.Name = "Home";
        container.SetParent(game.UINode);
        
        UIText header = createText(im2Scale(.5, 0).add(im2Abs(0, 10)), im2ScaleToAbs(.95, .04, container) , Color.lightGray.brighter(), "My Projects");
        header.AnchorPoint = new Vector2(.5, 0);
        header.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
        header.SetParent(container);

        UITextButton create = createCreateButton(container);
        create.SetParent(container);

        UITextButton add = createAddButton(container);
        add.SetParent(container);

        UIFrame searchBar = createSearchBar(container);
        searchBar.Position = searchBar.Position.add(UDim2.fromAbsolute(0, 5));
        searchBar.SetParent(container);

        UIFrame tblHead = createProjTableHead();
        tblHead.SetParent(container);
        
        projectTable = createProjectTable(container);
        projectTable.SetParent(container);

        create.Mouse1Down.Connect(()-> {
            newProjectPrompt.Visible = true;
        });
    }

    private UIButton[] createNavbarButtons(UIFrame area) {
        UIButton[] buttons = new UIButton[3];

        UIButton home = new UIButton();
        home.Size = UDim2.fromScale(1, .1);
        home.BackgroundTransparency = 1;

        UIImage homeIcon = new UIImage();
        homeIcon.AnchorPoint = new Vector2(0, .5);
        homeIcon.SetImage("JGameStudio\\Assets\\Icons\\HomeEmpty.png");
        homeIcon.Name = "Home";
        homeIcon.Size = UDim2.fromScale(.1, .7);
        homeIcon.SetImageScale(new Vector2(50));
        homeIcon.Position = UDim2.fromScale(.05, .5);
        homeIcon.BackgroundTransparency = 1;
        homeIcon.SetCProp("Selected", false);
        homeIcon.MouseTargetable = false;
        homeIcon.SetParent(home);

        UIAspectRatioConstraint constraint = new UIAspectRatioConstraint();
        constraint.DominantAxis = Constants.Vector2Axis.Y;
        constraint.SetParent(homeIcon);

        UIText homeText = new UIText();
        homeText.MouseTargetable = false;
        homeText.Size = UDim2.fromScale(.6, 1);
        homeText.Position = UDim2.fromScale(.25, 0);
        homeText.Text = "Home";
        homeText.CustomFont = StudioGlobals.GlobalFont;
        homeText.BackgroundTransparency = 1;
        homeText.TextColor = StudioGlobals.TextColor;
        homeText.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
        homeText.FontSize = 20;
        homeText.SetParent(home);

        UICorner homeCorner = new UICorner();
        homeCorner.Radius = .5;
        homeCorner.RelativeTo = Constants.Vector2Axis.Y;
        homeCorner.SetParent(home);

        buttons[0] = home;
        home.SetParent(area);

        UIButton installs = home.Clone();
        installs.GetChildWhichIsA(UIText.class).Text = "Installation";
        installs.GetChildWhichIsA(UIImage.class).SetImage("JGameStudio\\Assets\\Icons\\InstallsEmpty.png");
        installs.SetParent(area);
        buttons[1] = installs;

        UIButton settings = home.Clone();
        settings.GetChildWhichIsA(UIText.class).Text = "Settings";
        settings.GetChildWhichIsA(UIImage.class).SetImage("JGameStudio\\Assets\\Icons\\SettingsEmpty.png");
        settings.SetParent(area);
        buttons[2] = settings;

        return buttons;
    }

    private UIButton curSelected;
    private UIFrame curFrame;

    private void setNavBarButtonSelected(UIButton b) {
        b.SetCProp("Selected", true);
        b.HoverEffectsEnabled = false;

        b.BackgroundTransparency = .9;
    }

    private void setNavBarButtonDeselected(UIButton b) {
        b.SetCProp("Selected", false);
        b.HoverEffectsEnabled = true;

        b.BackgroundTransparency = 1;
    }

    private void createNavBar() {
        String[] menus = new String[] {"Home", "Installs", "Settings"};

        UIFrame container = new UIFrame();
        container.Size = UDim2.fromAbsolute(250, windowHeight);
        container.BackgroundTransparency = 1;
        container.SetParent(game.UINode);

        UIFrame list = new UIFrame();
        list.Position = UDim2.fromScale(.5, .05);
        list.Size = UDim2.fromScale(.9, .7);
        list.AnchorPoint = new Vector2(.5, 0);
        list.BackgroundTransparency = 1;
        list.SetParent(container);

        UIListLayout layout = new UIListLayout();
        layout.SetParent(list);

        UIButton[] buttons = createNavbarButtons(list);

        curSelected = buttons[0];
        curFrame = game.UINode.GetTypedChild(menus[0]);
        
        setNavBarButtonSelected(curSelected);

        for (int i = 0; i < buttons.length; i++) {
            UIButton button = buttons[i];

            int index = i;

            if (button == null) continue;

            button.MouseEnter.Connect(()-> {
                if (curSelected == button) return;
                button.BackgroundTransparency = .9;
            });

            button.MouseLeave.Connect(()-> {
                if (curSelected == button) return;
                button.BackgroundTransparency = 1;
            });

            button.Mouse1Down.Connect(()-> {
                setNavBarButtonDeselected(curSelected);
                curFrame.Visible = false;

                curSelected = button;
                setNavBarButtonSelected(button);
                
                curFrame = game.UINode.<UIFrame>GetTypedChild(menus[index]);
                curFrame.Visible = true;
            });
        }

        UIText version = new UIText();
        version.AnchorPoint = new Vector2(.5, 1);
        version.Position = UDim2.fromScale(.5, .99);
        version.Size = UDim2.fromScale(.95, .015);
        version.BackgroundTransparency = 1;
        version.CustomFont = StudioGlobals.GlobalFont;
        version.TextColor = StudioGlobals.TextColor;
        version.Text = jsonData.Data.hub_version;
        version.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
        version.TextScaled = true;
        version.SetParent(container);
    }

    public JGameHub() {
        FlatDarculaLaf.setup();

        game = new JGame(new StartParams(false));
        StudioUtil.game = game;
        StudioGlobals.construct();

        initWindow();

        windowWidth = game.Services.WindowService.GetWindowWidth();
        windowHeight = game.Services.WindowService.GetWindowHeight();

        createLines();

        createHomeArea();
        createInstallsPage();
        createSettingssPage();

        createNavBar();

        newProjectPrompt = createNewProjectPrompt();
        newProjectPrompt.SetParent(game.UINode);
    }

}
