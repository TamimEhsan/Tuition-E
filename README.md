# Tuition-E

Tuition-E is a Java powered software made for peer to peer network connection using Java, JavaFX, JavaX, SQLite, Jfoenix and I don't know what more!

## Dependencies

- [JDK 11+](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [JavaFX 11](https://gluonhq.com/products/javafx/) for UI
- [Jfoenix](https://github.com/TamimEhsan/Tuition-E/blob/master/Dependencies/TrayTester.jar) for material design
- [TrayTester](https://github.com/TamimEhsan/Tuition-E/blob/master/Dependencies/TrayTester.jar) for push notifications
- [SQLite JDBC](https://github.com/TamimEhsan/Tuition-E/blob/master/Dependencies/sqlite-jdbc-3.32.3.2.jar) for database connection
- [Sarxos](https://github.com/TamimEhsan/Tuition-E/blob/master/Dependencies/webcam-capture-0.3.13-20200330.202351-7.jar) for webcam capture

## Features

- Audio Call
- Screenshare
- Remote Control
- Chat
- Whiteboard
- Saving Tasks
- File share
- Notifications
- And lots more

![UI](https://raw.githubusercontent.com/TamimEhsan/Tuition-E/master/Assets/ui.PNG)

## Setting up a Connection

The host at first creates a server . And the user can connect to that server. And after that they can use the shared options. Some of the freatures like Tasks, Profile can be used wothout connection too!

![Connection](https://raw.githubusercontent.com/TamimEhsan/Tuition-E/master/Assets/Connection.gif)

## Screenshare

Both the user can share their screen with one another using realtime screenshare and view share option. Just click and minimize and do your job. That's it

### Features

- Two way share screen
- Seperate Share scree and view share option

![Image](https://raw.githubusercontent.com/TamimEhsan/Tuition-E/master/Assets/ScreenShare.gif)

## Remote Control

This is a light weight remote control function that allows only mouse click events between peers. And as of such only the host can enable remote control.

### Features

- 2:1 screen display
- Primary and Secondary Mouse event
- Supports multiple click

![Remote Control](https://raw.githubusercontent.com/TamimEhsan/Tuition-E/master/Assets/RemoteControl.gif)

## Chat

Chat window features a simple but attractive chat gui where the users can share their thoughts

### Features

- Seperate Sender and Reciever [Left right] message display


![Chat](https://raw.githubusercontent.com/TamimEhsan/Tuition-E/master/Assets/Chat.gif)

## File share

The File share option allows user to upload and download files to and from the peer. The transfer rate is pretty good i guess.

### Features

- Supports all kind of file type
- Changable download directory
- Supports upto 250 MB file with no pressure

![File](https://raw.githubusercontent.com/TamimEhsan/Tuition-E/master/Assets/FileShare2.gif)

## Audio Call

Although not tested ( cause my mic is broken ) this allows the users to directly call each other. The voice gets slightly broken.

![Audio](https://raw.githubusercontent.com/TamimEhsan/Tuition-E/master/Assets/AudioCall.gif)

## Video Call

Same problem here! My webcam is also broken. Will update this feature asap!

## Whiteboard

The whiteboard feature allows both the user to share a board and with some handy features the experience would be excellent

### Features

- Sharable whiteboard
- Simultaneous usability by both user
- Toggle annote
- Free writing and drawing
- Both pen and type feature
- Resizable pen/brush tip
- Custom color for pen/brush
- Type tool to write texts
- Resizable texts
- Clear Screen

![Image](https://raw.githubusercontent.com/TamimEhsan/Tuition-E/master/Assets/Whiteboard.gif)

![Image](https://raw.githubusercontent.com/TamimEhsan/Tuition-E/master/Assets/WhiteBoardFunctions.gif)

## Tasks

Used SQLite database here to store the tasks lists. 

### Features

- Data persistance
- Add new tasks
- Delete selected task
- Search for task 
- Sort by value for any column
- Set date efficiently

![Tasks](https://raw.githubusercontent.com/TamimEhsan/Tuition-E/master/Assets/Tasks.gif)

## Profile

The user can change his or her profile section too!

### Features

- Data persistance
- Editable name and description
- Set image from local device

![Profile](https://raw.githubusercontent.com/TamimEhsan/Tuition-E/master/Assets/Profile.gif)
