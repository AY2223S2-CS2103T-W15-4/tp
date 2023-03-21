package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.InternshipApplication;
import seedu.address.model.person.Person;
import seedu.address.model.task.InternshipTodo;
import seedu.address.model.task.Note;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final TodoList todoList;
    private final NoteList noteList;
    private final FilteredList<InternshipApplication> filteredInternships;
    private final FilteredList<InternshipTodo> filteredTodo;
    private final FilteredList<Note> filteredNote;
    private final FilteredList<Person> filteredPersons;
    private List<InternshipApplication> cachedInternshipList;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs,
                        ReadOnlyTodoList todoList, ReadOnlyNote noteList) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.noteList = new NoteList(noteList);
        this.todoList = new TodoList(todoList);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredInternships = new FilteredList<>(this.addressBook.getInternshipList());
        cachedInternshipList = new ArrayList<>();
        filteredTodo = new FilteredList<>(this.todoList.getTodoList());
        filteredNote = new FilteredList<>(this.noteList.getNoteList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new TodoList(), new NoteList());
    }

    //=========== UserPrefs ==================================================================================
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setInternEase(ReadOnlyAddressBook internEase) {
        this.addressBook.resetData(internEase);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public ReadOnlyTodoList getTodoList() {
        return todoList;
    }

    @Override
    public ReadOnlyNote getNoteList() {
        return noteList;
    }

    @Override
    public boolean hasApplication(InternshipApplication application) {
        requireNonNull(application);
        return addressBook.hasApplication(application);
    }

    @Override
    public boolean hasTodo(InternshipTodo todo) {
        requireNonNull(todo);
        return todoList.hasTodo(todo);
    }

    @Override
    public boolean hasNote(Note note) {
        requireNonNull(note);
        return noteList.hasNote(note);
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void deleteInternship(InternshipApplication application) {
        addressBook.removeApplication(application);
    }

    @Override
    public void deleteTodo(InternshipTodo target) {
        todoList.removeTodo(target);
    }

    @Override
    public void deleteNote(Note target) {
        noteList.removeNote(target);
    }

    @Override
    public void clearTodo(ReadOnlyTodoList internEase) {
        todoList.clearTodo(internEase);
    }

    @Override
    public void clearNote(ReadOnlyNote internEase) {
        noteList.clearNote(internEase);
    }

    @Override
    public void addApplication(InternshipApplication application) {
        addressBook.addApplication(application);
        updateFilteredInternshipList(PREDICATE_SHOW_ALL_APPLICATIONS);
    }

    @Override
    public void addApplications(List<InternshipApplication> applications) {
        addressBook.addApplications(applications);
        updateFilteredInternshipList(PREDICATE_SHOW_ALL_APPLICATIONS);
    }

    @Override
    public void addTodo(InternshipTodo todo) {
        todoList.addTodo(todo);
        updateFilteredTodoList(PREDICATE_SHOW_ALL_TODO);
    }

    @Override
    public void addNote(Note note) {
        noteList.addNote(note);
        updateFilteredNoteList(PREDICATE_SHOW_ALL_NOTES);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setApplication(InternshipApplication target, InternshipApplication editedApplication) {
        requireAllNonNull(target, editedApplication);

        addressBook.setApplication(target, editedApplication);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public void setTodo(InternshipTodo target,
                        InternshipTodo editedTodo) {
        requireAllNonNull(target, editedTodo);

        todoList.setTodo(target, editedTodo);
    }

    @Override
    public void setNote(Note target, Note editedNote) {
        requireAllNonNull(target, editedNote);

        noteList.setNote(target, editedNote);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<InternshipApplication> getFilteredInternshipList() {
        return filteredInternships;
    }

    @Override
    public void updateFilteredInternshipList(Predicate<InternshipApplication> predicate) {
        requireNonNull(predicate);
        filteredInternships.setPredicate(predicate);
    }

    @Override
    public ObservableList<InternshipTodo> getFilteredTodoList() {
        return filteredTodo;
    }

    @Override
    public void updateFilteredTodoList(Predicate<InternshipTodo> predicate) {
        requireNonNull(predicate);
        filteredTodo.setPredicate(predicate);
    }

    @Override
    public ObservableList<Note> getFilteredNoteList() {
        return filteredNote;
    }

    @Override
    public void updateFilteredNoteList(Predicate<Note> predicate) {
        requireNonNull(predicate);
        filteredNote.setPredicate(predicate);
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public List<InternshipApplication> getCachedInternshipList() {
        return cachedInternshipList;
    }

    @Override
    public InternshipApplication getAndRemoveCachedApplication() {
        InternshipApplication application = cachedInternshipList.get(cachedInternshipList.size() - 1);
        cachedInternshipList.remove(application);
        return application;
    }

    @Override
    public void addInternshipToCache(InternshipApplication application) {
        cachedInternshipList.remove(application);
        cachedInternshipList.add(application);
    }

    @Override
    public void addAllInternshipToCache(List<InternshipApplication> application) {
        cachedInternshipList.removeAll(application);
        cachedInternshipList.addAll(application);
    }

    @Override
    public void setEmptyInternshipCacheList() {
        cachedInternshipList = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;

        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons)
                && filteredTodo.equals(other.filteredTodo)
                && filteredNote.equals(other.filteredNote);
    }

}
