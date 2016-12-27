# java-stuff
Advanced Programming Lab Homeworks

### Lab description

Starting from this week...:
+ Create multiple packages in your projects for organizing classes and interfaces
+ Pay attention to the acces level modifiers specified for class members
+ Pay attention to the warnings/hints issued by the IDE.


**Visual AudioManager**  
Rewrite the audio file management application from the previous lab so that its functionality is available also via a **graphical user interface**. The command line functionality should be preserved (if possible).
The application must cover the following aspects:
+ A *tree-like* representation of the audio files, using a `JTree` component. (you may also take a look at the `FileSystemView` class).
+ When selecting a node in the tree, detailed information about the current selection should be displayed:
  + if the selected node is a directory, a `table` containing all the songs from that directory will be shown (filename, song name, artist, etc).
  + if the selected node is an audio file, a `list` or a `text area` containing the metadata of that song will be shown.
+ A `contextual menu` should offer various actions such as: add a song to the favourites, play a song, search the Web for information about a song.
+ The main frame should be organized using a `split pane`.
+ A special node of the tree will represent the favourite list (similar to a directory node).
+ A bonus may be given for storing the favourite list in an external **XML** file (use either an XML parser or XML serialization).
+ **Warning**: It is important to separate the GUI code of the application logic code!
+ **Warning**: Create appropriate packages to organize your project!