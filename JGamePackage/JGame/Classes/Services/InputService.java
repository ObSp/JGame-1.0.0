package JGamePackage.JGame.Classes.Services;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JFrame;

import JGamePackage.JGame.Classes.Instance;
import JGamePackage.JGame.Classes.UI.UIBase;
import JGamePackage.JGame.Classes.UI.UITextInput;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import JGamePackage.lib.Signal.Signal;
import JGamePackage.lib.Signal.SignalWrapper;
import JGamePackage.lib.Signal.VoidSignal;
import JGamePackage.lib.Signal.VoidSignalWrapper;

public class InputService extends Service {

    private Signal<KeyEvent> onkeypress = new Signal<>();
    public SignalWrapper<KeyEvent> OnKeyPress = new SignalWrapper<>(onkeypress);

    private VoidSignal onclick = new VoidSignal();
    public VoidSignalWrapper OnMouseClick = new VoidSignalWrapper(onclick);

    private VoidSignal mouseUp = new VoidSignal();
    public VoidSignalWrapper OnMouseUp = new VoidSignalWrapper(mouseUp);

    private VoidSignal windowClosing = new VoidSignal();
    public VoidSignalWrapper GameClosing = new VoidSignalWrapper(windowClosing);

    private Signal<Integer> mouseWheelMoved = new Signal<>();
    public SignalWrapper<Integer> OnMouseScroll = new SignalWrapper<>(mouseWheelMoved);

    private VoidSignal windowResized = new VoidSignal();
    public VoidSignalWrapper OnWindowResized = new VoidSignalWrapper(windowResized);
    
    public Integer FullscreenHotKey = KeyEvent.VK_F11;

    private ArrayList<String> heldKeys = new ArrayList<>();

    //private ArrayList<UIBase> hoveringUIItems = new ArrayList<>();
    private UIBase currentHover = null;

    private UITextInput selectedUIInput = null;

    private Vector2 lastMousePos;
    private Vector2 curMousePos;


    private boolean isMouse1Down = false;
    private boolean isMouse2Down = false;
    private boolean isMouse3Down = false;


    public InputService(SignalWrapper<Double> onTick) {
        JFrame gameWindow = game.GetWindow();

        gameWindow.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (selectedUIInput != null) {
                    int code = e.getKeyCode();
                    char keyChar = e.getKeyChar();
                    if (code == KeyEvent.VK_BACK_SPACE) {
                        selectedUIInput.Text =  selectedUIInput.Text.length() > 0 ? selectedUIInput.Text.substring(0, selectedUIInput.Text.length()-1) : "";
                        selectedUIInput.TextUpdated.Fire();
                    } else if (code == KeyEvent.VK_ENTER) {
                        selectedUIInput = null;
                    } else if (Character.isLetterOrDigit(keyChar) || Pattern.matches("\\p{Punct}", new String(new char[] {keyChar})) || code == KeyEvent.VK_SPACE){
                        selectedUIInput.Text += e.getKeyChar();
                        selectedUIInput.TextUpdated.Fire();
                    }
                }

                if (heldKeys.indexOf(KeyEvent.getKeyText(e.getKeyCode()))!=-1) return;

                if (e.getKeyCode() == FullscreenHotKey) {
                    game.Services.WindowService.SetFullscreen(!game.Services.WindowService.IsFullscreen());
                }

                //if (heldKeys.indexOf(KeyEvent.getKeyText(e.getKeyCode()))==-1) heldKeys.add(KeyEvent.getKeyText(e.getKeyCode()));
                heldKeys.add(KeyEvent.getKeyText(e.getKeyCode()));
                onkeypress.Fire(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String keyText = KeyEvent.getKeyText(e.getKeyCode());
                heldKeys.remove(keyText);
            }
        });

        gameWindow.addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                mouseWheelMoved.Fire(e.getWheelRotation());
            }
            
        });
    
        gameWindow.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1){
                    selectedUIInput = null;

                    isMouse1Down = true;
                    onclick.Fire();

                    UIBase uiTarget = GetMouseUITarget();
                    if (uiTarget != null) {
                        new Thread(()->uiTarget.Mouse1Down.Fire()).start();
                    }
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    isMouse2Down = true;
                } else if (e.getButton() == MouseEvent.BUTTON2) {
                    isMouse3Down = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1){
                    isMouse1Down = false;
                    mouseUp.Fire();

                    UIBase uiTarget = GetMouseUITarget();
                    if (uiTarget != null) {
                        uiTarget.Mouse1Up.Fire();
                    }
                } else if(e.getButton() == MouseEvent.BUTTON3){
                    isMouse2Down = false;
                } else if (e.getButton() == MouseEvent.BUTTON2) {
                    isMouse3Down = false;
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        gameWindow.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                windowClosing.Fire();
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                heldKeys.clear();
            }
        });

        gameWindow.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                windowResized.Fire();
            }
        });

        onTick.Connect(dt->{
            lastMousePos = curMousePos;
            curMousePos = GetMousePosition();

            if (lastMousePos == null) lastMousePos = curMousePos;

            UIBase newTarget = GetMouseUITarget();
            
            if (newTarget == currentHover) return;

            UIBase oldTarget = currentHover;
            currentHover = newTarget;

            if (oldTarget != null) oldTarget.MouseLeave.Fire();
            if (currentHover != null) currentHover.MouseEnter.Fire();
        });
    }

    //--KEY RELATED--//

    /**Returns an {@code int} ranging from {@code -1} to {@code 1} based on whether
     * keys that are typically associated with horizontal movement, such as the {@code A and D} keys
     * are currently being pressed by the user, returning {@code 0} if no such keys are being pressed.
     * 
     * @return An int corresponding to the horizontal direction of keys currently pressed by the user
     * 
     * @see InputService#IsKeyDown(int)
     * @see InputService#GetInputVertical()
     */
    public int GetInputHorizontal(){
        int val = 0;

        if (IsKeyDown(KeyEvent.VK_A)){
            val--;
        }
        if(IsKeyDown(KeyEvent.VK_D)){
            val++;
        }
        return val;
    }


    /**Returns an {@code int} ranging from {@code -1} to {@code 1} based on whether
     * keys that are typically associated with vertical movement, such as the {@code W and S} keys
     * are currently being pressed by the user, returning {@code 0} if no such keys are being pressed.
     * 
     * @return An int corresponding to the vertical direction of keys currently pressed by the user
     * 
     * @see InputService#IsKeyDown(int)
     * @see InputService#GetInputHorizontal()
     */
    public int GetInputVertical(){
        int val = 0;
        if (IsKeyDown(KeyEvent.VK_S)){
            val--;
        } 
        if(IsKeyDown(KeyEvent.VK_W)){
            val++;
        }
        return val;
    }


    /**Returns whether or not the Key corresponding to the provided {@code KeyCode} is currently being pressed by the user.
    * 
    * @param KeyCode : The KeyCode of the Key to be checked
    * @return Whether or not the Key is currently being pressed by the user
    * 
    * @see KeyEvent
    */
    public boolean IsKeyDown(int KeyCode){
        String keyText = KeyEvent.getKeyText(KeyCode);

        return heldKeys.indexOf(keyText)>-1 ? true : false;
    }


    //--MOUSE--//
    public boolean IsMouse1Down() {
        return isMouse1Down;
    }

    public boolean IsMouse2Down() {
        return isMouse2Down;
    }

    public boolean IsMouse3Down() {
        return isMouse3Down;
    }

    public Vector2 GetMouseDelta() {
        return curMousePos.subtract(lastMousePos);
    }

    public Vector2 GetMousePosition() {
        boolean fullscreen = game.Services.WindowService.IsFullscreen();

        Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
        Point windowLoc = game.GetWindow().getLocation();
        return new Vector2(mouseLoc.getX() - windowLoc.getX() - (!fullscreen ? 7 : 0), mouseLoc.getY() - windowLoc.getY() - (fullscreen ? 0 : 30));
    }

    public UIBase GetMouseUITarget() {
        Vector2 mousePos = game.InputService.GetMousePosition();
        UIBase curTarget = null;
        // first find an UI instance that (SHOULD BE) diplayed on top
        // the one issue with this is that children are always displayed on TOP of
        // parents, therefore step 2 is needed
        for (UIBase v : game.UINode.GetDescendantsOfClass(UIBase.class)) {
            if (!isPointInBounds(v.GetAbsolutePosition(), v.GetAbsoluteSize(), mousePos) || !isUIItemVisible(v)|| !v.MouseTargetable)
                continue;

            if (curTarget == null) {
                curTarget = v;
                continue;
            }

            if (v.ZIndex > curTarget.ZIndex || v.GetAncestors().length > curTarget.GetAncestors().length) {
                curTarget = v;
            }
        }
        return curTarget;
    }

    public UIBase[] GetMouseUITargetList() {
        ArrayList<UIBase> list = new ArrayList<>();
        Vector2 mousePos = GetMousePosition();
        for (Instance item : game.UINode.GetDescendants()) {
            if (!(item instanceof UIBase)) continue;

            UIBase v = (UIBase) item;

            if (!isPointInBounds(v.GetAbsolutePosition(), v.GetAbsoluteSize(), mousePos))
                list.add(v);
        }
        UIBase[] arr = new UIBase[list.size()];
        for (int i = 0; i < arr.length; i++)
            arr[i] = list.get(i);
        return arr;
    }

    public UITextInput GetFocusedUITextInput() {
        return selectedUIInput;
    }

    public void SetFocusedUITextInput(UITextInput input) {
        selectedUIInput = input;
    }

    private boolean isUIItemVisible(UIBase uiBase) {
        if (!uiBase.Visible) return false;

        for (Instance ancestor : uiBase.GetAncestors()) {
            if (ancestor == game.UINode) return true;
            if (!(ancestor instanceof UIBase)) return false;
            if (!((UIBase) ancestor).Visible) return false;
        }

        return true;
    }

    private boolean isPointInBounds(Vector2 pos, Vector2 size, Vector2 point) {
        double left = pos.X;
        double right = left + size.X;
        double top = pos.Y;
        double bottom = top + size.Y;

        double x = point.X;
        double y = point.Y;

        return (left<=x && x <=right && y<=bottom && y>=top);
    }
}
