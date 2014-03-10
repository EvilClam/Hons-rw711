package GUI;

import GOL.GolLattice;
import GOL3D.GolLattice3D;
import cellularAutomaton.CAUpdateListener;
import cellularAutomaton.Cell;
import cellularAutomaton.CellularAutomaton;
import cellularAutomaton.Lattice;
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

/**
 * test
 *
 * @author Shaun Schreiber
 */
public class Main extends SimpleApplication implements CAUpdateListener {

    private CellularAutomaton ca = new CellularAutomaton() {
    };
    private GolLattice goll = new GolLattice();
    private GolLattice3D goll3D = new GolLattice3D();
    private boolean is2D = true;
    private volatile boolean isRunning = true;
    private String fileName;
    private float time = 0;
    private BitmapText txt;

    public static void main(String[] args) {
        System.out.println("Please note there are two arguments.");
        System.out.println("The first is the file name and the second is 'true' or 'false'");
        System.out.println("true(Default) indicates that it is a 2D file that is to be loaded");
        System.out.println("false indicates that it is a 3D file that is to be loaded.");
        System.out.println("If there is no arguments or thefile name = -1 then a random CA will be generated.");
        if (args.length == 0) {
            Main app = new Main(true, "-1");
            app.start();
        } else if (args.length == 1) {
            Main app = new Main(false, args[0]);
            app.start();

        } else {
            Main app = new Main(Boolean.parseBoolean(args[1]), args[0]);
            app.start();
        }


    }

    /**
     * Contructor
     * @param is2D True, 2D game of life. False, 3D game of life.
     * @param fileName The file name where the cellular automaton is stored or 
     *                 -1 if it should be generated. 
     */
    public Main(boolean is2D, String fileName) {
        super();
        this.is2D = is2D;
        this.fileName = fileName;
    }

    /**
     * Initializes the keys and nodes.
     * Creates the neccessary listeners for the keys and mouse bindings.
     * Initializes the 3D environment.
     */
    @Override
    public void simpleInitApp() {
        initKeys();
        if (is2D) {
            startup2D();
            buildMenu2D();
        } else {
            startup3D();
            buildMenu3D();
        }

        setDisplayStatView(false);
    }

    /**
     * Updates the Lattice every 200ms.
     * Only updates the Lattice if isRunning is true.
     * @param tpf time per frame 
     */
    @Override
    public void simpleUpdate(float tpf) {
        if (time > 0.200) {
            if (isRunning) {
                ca.nextLattice();
                time = 0;
            }
        }
        time += tpf;
    }

    /**
     * Function not implemented.
     * @param rm 
     */
    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    /**
     * Updates the display every time the lattice is updated.
     * @param lat The current lattice. 
     */
    @Override
    public void update(Lattice lat) {
        if (is2D) {
            update2D(lat);
        } else {
            update3D(lat);
        }

    }

    /**
     * Creates the initial grid.
     */
    public void startup2D() {
        cam.setLocation(new Vector3f(0.25f, 0.25f, -2f));
        cam.lookAt(new Vector3f(0.25f, 0.25f, -1f), Vector3f.UNIT_Y);
        if(!fileName.equals("-1")) {
            goll.readFromFile(fileName);
        } else {
            goll.randomCA();
        }
        ca.initCA(goll);
        ca.addListener(this);
        ca.start();
        Material cube2Mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        cube2Mat.setTexture("ColorMap", assetManager.loadTexture("Textures/dead.png"));
        cube2Mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        float scale = 1f / Math.max((float) goll.getDimensions()[0], (float) goll.getDimensions()[1]);
        for (int j = 0; j < goll.getDimensions()[0]; j++) {
            for (int i = 0; i < goll.getDimensions()[1]; i++) {

                Geometry geom = new Geometry("Box" + i + ":" + j, new Box(0.5f, 0.5f, 0.5f));
                geom.move(i, j, 0);

                geom.setMaterial(cube2Mat);
                rootNode.attachChild(geom);
                int[] temp = {j, i};
                if ((Integer) goll.getCell(temp).getValue() == 0) {
                    geom.setCullHint(Spatial.CullHint.Always);
                }
            }
        }
        rootNode.setLocalScale(scale);
        getViewPort().setBackgroundColor(ColorRGBA.DarkGray);
    }

    /**
     * Updates the grid according to the lattice.
     * @param lat the nect lattice.
     */
    public void update2D(Lattice lat) {
        GolLattice tempLattice = (GolLattice) lat;

        Material cube2Mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        cube2Mat1.setTexture("ColorMap", assetManager.loadTexture("Textures/dead.png"));
        cube2Mat1.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);

        Material cube2Mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        cube2Mat2.setTexture("ColorMap", assetManager.loadTexture("Textures/dead1.png"));
        cube2Mat2.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);

        for (int j = 0; j < tempLattice.getDimensions()[0]; j++) {
            for (int i = 0; i < tempLattice.getDimensions()[1]; i++) {
                int[] temp = {j, i};
                if ((Integer) goll.getCell(temp).getValue() == 0) {
                    rootNode.getChild("Box" + i + ":" + j).setCullHint(Spatial.CullHint.Always);
                    rootNode.getChild("Box" + i + ":" + j).setMaterial(cube2Mat2);
                } else {
                    rootNode.getChild("Box" + i + ":" + j).setCullHint(Spatial.CullHint.Never);
                    rootNode.getChild("Box" + i + ":" + j).setMaterial(cube2Mat1);
                }

            }
        }
    }

   /**
     * Creates the initial grid.
     */
    public void startup3D() {
        cam.setLocation(new Vector3f(0.25f, 1f, -2f));
        cam.lookAt(new Vector3f(0.25f, 1f, -1f), Vector3f.UNIT_Y);
        if(!fileName.equals("-1")) {
           goll3D.readFromFile(fileName);
        } else {
            goll3D.randomCA();
        }
        
        ca.initCA(goll3D);
        ca.addListener(this);
        ca.start();
        Material cube2Mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        cube2Mat.setTexture("ColorMap", assetManager.loadTexture("Textures/dead.png"));
        cube2Mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        float scale = 1f / Math.max((float) goll3D.getDimensions()[1], (float) goll3D.getDimensions()[2]);
        for (int k = 0; k < goll3D.getDimensions()[0]; k++) {
            for (int j = 0; j < goll3D.getDimensions()[1]; j++) {
                for (int i = 0; i < goll3D.getDimensions()[2]; i++) {
                    Geometry geom = new Geometry("Box" + i + ":" + j + ":" + k, new Box(0.5f, 0.5f, 0.5f));
                    geom.move(i, j, k);
                    int[] temp = {k, j, i};
                    geom.setMaterial(cube2Mat);
                    rootNode.attachChild(geom);
                    if ((Integer) goll3D.getCell(temp).getValue() == 0) {
                        geom.setCullHint(Spatial.CullHint.Always);
                    }
                }
            }
        }
        rootNode.setLocalScale(scale);
        getViewPort().setBackgroundColor(ColorRGBA.DarkGray);
    }

    /**
     * Updates the grid according to the lattice.
     * @param lat the nect lattice.
     */
    public void update3D(Lattice lat) {
        GolLattice3D tempLattice = (GolLattice3D) lat;
        for (int k = 0; k < tempLattice.getDimensions()[0]; k++) {
            for (int j = 0; j < tempLattice.getDimensions()[1]; j++) {
                for (int i = 0; i < tempLattice.getDimensions()[2]; i++) {
                    int[] temp = {k, j, i};
                    if ((Integer) goll3D.getCell(temp).getValue() == 1) {
                        rootNode.getChild("Box" + i + ":" + j + ":" + k).setCullHint(Spatial.CullHint.Never);
                    } else {
                        rootNode.getChild("Box" + i + ":" + j + ":" + k).setCullHint(Spatial.CullHint.Always);
                    }
                }
            }
        }
    }

    /**
     * Creates the keys bindings and adds the required listeners.
     */
    private void initKeys() {
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("remove", new KeyTrigger(KeyInput.KEY_E));
        inputManager.addMapping("Life", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(actionListener, "Pause", "Life", "remove");
    }
    
    /**
     * Handles the user input.
     */
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {

            if (name.equals("Pause") && !keyPressed && is2D) {
                isRunning = !isRunning;
                if (!isRunning) {
                    showDeadNodes2D();
                    initCrossHairs();
                } else {
                    hideDeadNodes2D();
                    guiNode.detachChildAt(0);
                }
            } else if (name.equals("Life") && !keyPressed && is2D) {
                if (!isRunning) {
                    CollisionResults results = new CollisionResults();
                    Ray ray = new Ray(cam.getLocation(), cam.getDirection());
                    rootNode.collideWith(ray, results);

                    if (results.size() > 0) {
                        CollisionResult closest = results.getClosestCollision();
                        Spatial closestChild = closest.getGeometry();
                        String[] pos = closestChild.getName().substring(3).split(":");
                        closestChild = rootNode.getChild(closestChild.getName());

                        int position[] = {Integer.parseInt(pos[1]), Integer.parseInt(pos[0])};
                        Cell golc = goll.getCell(position);



                        if ((Integer) golc.getValue() == 0) {
                            Material cube2Mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                            cube2Mat1.setTexture("ColorMap", assetManager.loadTexture("Textures/dead.png"));
                            cube2Mat1.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
                            closestChild.setMaterial(cube2Mat1);
                            golc.setValue(1);
                        } else {
                            Material cube2Mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                            cube2Mat2.setTexture("ColorMap", assetManager.loadTexture("Textures/dead1.png"));
                            cube2Mat2.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
                            closestChild.setMaterial(cube2Mat2);
                            golc.setValue(0);
                        }
                    }
                }
            } else if (name.equals("remove") && !keyPressed) {
                if (guiNode.detachChild(txt) == -1) {
                    if (is2D) {
                        buildMenu2D();
                    } else {
                        buildMenu3D();
                    }
                }
            }
        }
    };

    /**
     * Creates and adds the crosshair to the GUI node.
     */
    protected void initCrossHairs() {

        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+");
        ch.setLocalTranslation(
                settings.getWidth() / 2 - ch.getLineWidth() / 2, settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChildAt(ch, 0);
    }
    
    /**
     * Makes the Dead nodes visiable.
     */
    private void showDeadNodes2D() {
        Material cube2Mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        cube2Mat.setTexture("ColorMap", assetManager.loadTexture("Textures/dead1.png"));
        cube2Mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        for (int j = 0; j < goll.getDimensions()[0]; j++) {
            for (int i = 0; i < goll.getDimensions()[1]; i++) {
                int[] temp = {j, i};
                if ((Integer) goll.getCell(temp).getValue() == 0) {
                    rootNode.getChild("Box" + i + ":" + j).setMaterial(cube2Mat);
                    rootNode.getChild("Box" + i + ":" + j).setCullHint(Spatial.CullHint.Never);
                }
            }
        }
    }

    /**
     * Hides the dead nodes.
     */
    private void hideDeadNodes2D() {
        for (int j = 0; j < goll.getDimensions()[0]; j++) {
            for (int i = 0; i < goll.getDimensions()[1]; i++) {
                int[] temp = {j, i};
                if ((Integer) goll.getCell(temp).getValue() == 0) {
                    rootNode.getChild("Box" + i + ":" + j).setCullHint(Spatial.CullHint.Always);
                }
            }
        }
    }

    /**
     * Adds the menu to the GUI node.
     */
    private void buildMenu2D() {
        BitmapFont fnt = assetManager.loadFont("Interface/Fonts/Default.fnt");
        txt = new BitmapText(fnt, false);
        txt.setBox(new Rectangle(0, 0, settings.getWidth(), settings.getHeight()));
        txt.setSize(fnt.getPreferredSize() * 2f);
        String part1 = "2D Pause - space\nWhen in pause aim with the crosshair";
        String part2 = ".\nLeft click on a cube to change its state.";
        String part3 = "\nYellow = dead  and orange = life\n press e to remove this message.";
        String part4 = "Use wasd and the mouse to move and look around\nPress ESC to exit";
        txt.setText(part1 + part2 + part3 + part4);
        txt.setLocalTranslation(0, txt.getHeight(), 0);
        guiNode.attachChild(txt);
    }

    /**
     * Adds the menu to the GUI node.
     */
    private void buildMenu3D() {
        BitmapFont fnt = assetManager.loadFont("Interface/Fonts/Default.fnt");
        txt = new BitmapText(fnt, false);
        txt.setBox(new Rectangle(0, 0, settings.getWidth(), settings.getHeight()));
        txt.setSize(fnt.getPreferredSize() * 2f);
        String part1 = "Use wasd and the mouse to move and look around";
        String part2 = "\nPress ESC to exit";
        String part3 = "\nPress e to remove this message.";
        txt.setText(part1 + part2 +part3);
        txt.setLocalTranslation(0, txt.getHeight(), 0);
        guiNode.attachChild(txt);
    }
}
