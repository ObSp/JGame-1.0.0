package JGamePackage.JGame.Classes.Services;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

import JGamePackage.JGame.Classes.Instance;
import JGamePackage.JGame.Classes.UI.UIBase;
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
    
    public Integer FullscreenHotKey = KeyEvent.VK_F11;

    private ArrayList<String> heldKeys = new ArrayList<>();

    private ArrayList<UIBase> hoveringUIItems = new ArrayList<>();


    private boolean isMouse1Down = false;
    private boolean isMouse2Down = false;


    public InputService(SignalWrapper<Double> onTick) {
        JFrame gameWindow = game.GetWindow();

        gameWindow.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
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
                    isMouse1Down = true;
                    onclick.Fire();

                    UIBase uiTarget = GetMouseUITarget();
                    if (uiTarget != null) {
                        uiTarget.Mouse1Down.Fire();
                    }

                } else if (e.getButton() == MouseEvent.BUTTON2) {
                    isMouse2Down = true;
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

                } else if(e.getButton() == MouseEvent.BUTTON2){
                    isMouse2Down = false;
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

        onTick.Connect(dt->{
            List<UIBase> mouseTargets = Arrays.asList(GetMouseUITargetList());
            for (UIBase v : game.UINode.GetDescendantsOfClass(UIBase.class)) {
                boolean inList = hoveringUIItems.contains(v);

                if (!mouseTargets.contains(v)) { //idk why ! is needed, smth is wrong with GetMouseUITargetList()
                    if (!inList) {
                        v.MouseEnter.Fire();
                        hoveringUIItems.add(v);
                    }
                } else if (inList) {
                    v.MouseLeave.Fire();
                    hoveringUIItems.remove(v);
                }

            }
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

    public Vector2 GetMousePosition() {
        Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
        return new Vector2(mouseLoc.getX(), mouseLoc.getY() - (game.Services.WindowService.IsFullscreen() ? 0 : 22));
    }

    public UIBase GetMouseUITarget() {
        Vector2 mousePos = GetMousePosition();
        UIBase curTarget = null;
        //first find an UI instance that (SHOULD BE) diplayed on top
        //the one issue with this is that children are always displayed on TOP of parents, therefore step 2 is needed 
        for (UIBase v : game.UINode.GetChildrenOfClass(UIBase.class)) {
            if (!isPointInBounds(v.GetAbsolutePosition(), v.GetAbsoluteSize(), mousePos)) continue;
            if (curTarget != null && v.ZIndex <= curTarget.ZIndex) continue;
            curTarget = v;
        }
        if (curTarget == null) return null;
        //to fix the above mentioned issue, repeadedly loop through children
        while (true) {
            UIBase[] children = curTarget.GetChildrenOfClass(UIBase.class);
            if (children.length == 0) break;
            UIBase lastChild = null;
            for (UIBase child : children) {
                if (!isPointInBounds(child.GetAbsolutePosition(), child.GetAbsoluteSize(), mousePos)) continue;
                if (lastChild != null && child.ZIndex <= lastChild.ZIndex) continue;
                lastChild = child;
            }
            if (lastChild == null) break;
            curTarget = lastChild;
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


    //--FULLSCREEN--//
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
