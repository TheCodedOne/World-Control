package worldcontrolteam.worldcontrol.api;

import worldcontrolteam.worldcontrol.client.Screen;

public interface IScreenPart {
    Screen getScreen();

    void setScreen(Screen screen);

    void updateData();
}
