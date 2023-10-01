package pathfinder.junitTests.textInterface;

import org.junit.Test;
import pathfinder.textInterface.TextInterfaceView;

public class TestTextInterfaceView {

    @Test(expected = IllegalStateException.class)
    public void testBeginWithoutHandler() {
        TextInterfaceView textInterfaceView = new TextInterfaceView();
        textInterfaceView.begin();
    }

    @Test(expected = IllegalStateException.class)
    public void testBlockingInputWithoutHandler() {
        TextInterfaceView textInterfaceView = new TextInterfaceView();
        textInterfaceView.blockingInput();
    }

}
