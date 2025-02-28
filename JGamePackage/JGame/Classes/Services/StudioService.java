package JGamePackage.JGame.Classes.Services;

import java.io.File;
import java.io.FileInputStream;

import JGamePackage.JGame.Classes.StudioClasses.ProjectRepresentation;
import JGamePackage.lib.CustomError.CustomError;

/**This service is used to perform JGameStudio-related operations.
 * 
 */
public class StudioService extends Service {
    private CustomError ErrorNoProject = new CustomError("Unable to %s: the directory %s is not a JGameStudio project.", CustomError.ERROR, "JGamePackage");

    private ProjectRepresentation projectRepresentation;

    private void assertProjectStatus() {
        if (!IsStudioProject()) {
            ErrorNoProject.Throw(new String[] {"get the project representation", new File("").getAbsolutePath()});
            return null;
        }
    }

    public StudioService() {
        super();

        if (IsStudioProject()) {
            BuildProjectRepresentation();
        }
    }

    private void BuildProjectRepresentation() {

    }
    
    public boolean IsStudioProject() {
        return new File("/.jgame").exists();
    }

    public String GetJGameVersion() {
        assertProjectStatus();
        
    }

    public ProjectRepresentation GetProjectRepresentation() {
        assertProjectStatus();
        return projectRepresentation;
    }
}
