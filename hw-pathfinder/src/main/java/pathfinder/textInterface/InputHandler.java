/*
 * Copyright (C) 2023 Soham Pardeshi.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Autumn Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder.textInterface;

/**
 * An {@link InputHandler} is capable of responding to a {@link String} command being input.
 */
public interface InputHandler {

    /**
     * Responds to the text of the command, in some way.
     *
     * @param input The command text that was input.
     */
    void handleInput(String input);

}
