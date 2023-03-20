package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.InternshipApplication;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<InternshipApplication> PREDICATE_SHOW_ALL_APPLICATIONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code internEase}.
     */
    void setInternEase(ReadOnlyAddressBook internEase);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if an internship application with the same identity as
     * {@code internshipApplication} exists in the address book.
     */
    boolean hasApplication(InternshipApplication person);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Deletes the given internship application.
     * The application must exist in the address book.
     */
    void deleteInternship(InternshipApplication application);

    /**
     * Adds the given application.
     * {@code InternshipApplication} must not already exist in the tracker.
     */
    void addApplication(InternshipApplication application);

    /**
     * Adds the given applications.
     * {@code InternshipApplications} must not already exist in the tracker.
     */
    void addApplications(List<InternshipApplication> applications);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedApplication}.
     * {@code target} must exist in the address book.
     * The application identity of {@code editedApplication} must not be the
     * same as another existing application in the address book.
     */
    void setApplication(InternshipApplication target, InternshipApplication editedApplication);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns an unmodifiable view of the filtered internship list
     */
    ObservableList<InternshipApplication> getFilteredInternshipList();

    /**
     * Updates the filter of the filtered internship list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredInternshipList(Predicate<InternshipApplication> predicate);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns an unmodifiable view of the cached internship list.
     */
    List<InternshipApplication> getCachedInternshipList();


    /**
     * Gets and remove item from the cached internship list.
     */
    InternshipApplication getAndRemoveCachedApplication();

    /**
     * Add current deleted internship application to the end of the cached internship list.
     */
    void addInternshipToCache(InternshipApplication application);

    /**
     * Add all cleared internship applications to the end of the cached internship list.
     */
    void addAllInternshipToCache(List<InternshipApplication> application);

    /**
     * Empties the internship cache list.
     */
    void setEmptyInternshipCacheList();
}
