package JGamePackage.JGame.Classes.StudioClasses;

import java.io.File;

public class ProjectRepresentation {
    public final File dotJGameDirectory;
    public final File jgamePackageDirectory;
    public final String jgamePackageVersion;
    public final ProjectJSON projectJson;


    public ProjectRepresentation(File dotJGameDirectory, File jgamePackageDirectory, File projectJson) {
        this.dotJGameDirectory = dotJGameDirectory;
        this.jgamePackageDirectory = jgamePackageDirectory;
        this.projectJson = new ProjectJSON(projectJson.getPath());
        jgamePackageVersion = "1.0.0";
    }

    public ProjectRepresentation() {
        this(new File("/.jgame"), new File("/JGamePackage"), new File("/.jgame/project.json"));
    }

    public class ProjectJSON {
        public final String path;

        public ProjectJSON(String path) {
            this.path = path;
        }
        
    }
}
