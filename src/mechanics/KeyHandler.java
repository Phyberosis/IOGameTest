package mechanics;

import IO.EatGame;
import IO.SceneIDs;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyHandler implements EventHandler<KeyEvent> {

    private EatGame parent;

    public KeyHandler(EatGame eg){
        parent = eg;
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()){
            case R:
                parent.changeScene(SceneIDs.GAME);
        }
    }
}
