package JGamePackage.JGame.Classes.Services;

import java.io.File;

import JGamePackage.lib.CustomError.CustomError;

public class ScriptService extends Service {
    private CustomError ErrorNoDotJGame = new CustomError("Unable to load scripts: unable to find %s.", CustomError.ERROR, "JGamePackage");

    private ProjectRepresentation traceDotJGameDir() {
        File dotJGame = new File("/.jgame");

        if (!dotJGame.exists()) {
            ErrorNoDotJGame.Throw(".jgame directory");
            return null;
        }

        File projJson = new File("/.jgame/project.json");

        if (!projJson.exists()) {
            ErrorNoDotJGame.Throw("project.json file");
            return null;
        }

        return new ProjectRepresentation(dotJGame, projJson);
    }

    record ProjectRepresentation(File dotJGame, File projectJSON) {}
}