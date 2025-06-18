A lightweight game engine created using the Java AWT library.
The engine centers around the use of "instances", which can be almost anything from rectanges, text, corner modifiers, and more.

# Scripts
The easiest way to control and program behaviors in the game is through the use of Scripts, which are similar to Unity Scripts. Scripts consist of 2 main methods: void Start() and void Tick(double). Start() is called when the script is loaded and run by the script manager and Tick(double) is called every frame with the delta time value as a parameter.
Scripts are instances that can be attached to Java files. Because Scripts are instances,
they can be parented to objects just like any other instance, allowing for modular behavior. Scripts can access 

# JGame Studio
![image](https://github.com/user-attachments/assets/bdccb2b0-6cd1-4774-b586-c9d5959af59f)
![image](https://github.com/user-attachments/assets/c52e96fb-e391-4955-9f45-5f5d17bc84ad)

Included with this release is a visual editor called JGame Studio which allows you to edit your JGame projects with a viewport, explorer, and properties window. Keep in mind that this editor is a heavy WIP and prone to bugs and crashes.

# Other Notes & Warnings
What becomes obvious by just looking at the file structure is that this is not how a traditional java project would be structured. While I would love to have a good explanation for this, the truth is that I started this project with almost no knowledge about Java and have kept the file structure since.
Not to worry: the file structure **will** be refacted to match java conventions.

Another glaring issue with JGame is the fact that it's quite slow. This is due to the reliance on the AWT library for rendering. As AWT itself is not intended for per-frame rendering calls, this slows down the engine significantly. A port to libgdx is likely in the future,
however this is a big change and will likely take quite a lot of time to do.
