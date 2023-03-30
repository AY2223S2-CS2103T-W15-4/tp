package seedu.address.model.task.exceptions;

/**
 * Signals that the operation will result requesting unavailable note.
 */
public class NoteNotFoundException extends RuntimeException {

    /**
     * Creates an instance of NoteNotFoundException.
     */
    public NoteNotFoundException() {
        super("Note not found!");
    }
}