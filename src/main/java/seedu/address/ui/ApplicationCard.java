package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.contact.Contact;
import seedu.address.model.documents.Documents;
import seedu.address.model.person.InternshipApplication;
import seedu.address.model.person.InterviewDate;
import seedu.address.ui.control.PopupEditInternship;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class ApplicationCard extends UiPart<Region> {
    private static final String FXML = "ApplicationListCard.fxml";
    public final InternshipApplication application;
    private PopupEditInternship popupEditInternship;
    private int index;
    private final MainWindow mainWindow;
    /**
     * NoteList: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private HBox cardPane;
    @FXML
    private Label companyName;
    @FXML
    private Label jobTitle;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label internshipStatus;
    @FXML
    private Label interviewDate;
    @FXML
    private Label resume;
    @FXML
    private Label coverLetter;
    @FXML
    private Button editButton;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public ApplicationCard(InternshipApplication application, int displayedIndex, MainWindow mainWindow) {
        super(FXML);
        this.application = application;
        this.mainWindow = mainWindow;
        this.index = displayedIndex;
        this.popupEditInternship = new PopupEditInternship(mainWindow);
        id.setText(displayedIndex + ". ");
        companyName.setText(application.getCompanyName().fullName);
        jobTitle.setText(application.getJobTitle().fullName);
        internshipStatus.setText(application.getStatus().name());
        Contact companyContact = application.getContact();
        if (companyContact != null) {
            email.setText(companyContact.getEmail().value);
            phone.setText(companyContact.getPhone().value);
            email.setVisible(true);
            phone.setVisible(true);
            email.setManaged(true);
            phone.setManaged(true);
        }
        InterviewDate interviewDateStr = application.getInterviewDate();
        if (interviewDateStr != null) {
            interviewDate.setText(interviewDateStr.toString());
            interviewDate.setVisible(true);
            interviewDate.setManaged(true);
        }
        Documents documents = application.getDocuments();
        if (documents != null) {
            resume.setText(documents.getResumeLink().value);
            coverLetter.setText(documents.getCoverLetterLink().value);
            resume.setVisible(true);
            coverLetter.setVisible(true);
            resume.setManaged(true);
            coverLetter.setManaged(true);
        }
    }

    /**
     * Creates a {@code ApplicationCard} with the given {@code InternshipApplication}.
     */
    public ApplicationCard(InternshipApplication application, MainWindow mainWindow) {
        super(FXML);
        this.application = application;
        this.mainWindow = mainWindow;
        this.popupEditInternship = new PopupEditInternship(mainWindow);
        companyName.setText(application.getCompanyName().fullName);
        jobTitle.setText(application.getJobTitle().fullName);
        internshipStatus.setText(application.getStatus().name());
        Contact companyContact = application.getContact();
        if (companyContact != null) {
            email.setText(companyContact.getEmail().value);
            phone.setText(companyContact.getPhone().value);
            email.setVisible(true);
            phone.setVisible(true);
            email.setManaged(true);
            phone.setManaged(true);
        }
        InterviewDate interviewDateStr = application.getInterviewDate();
        if (interviewDateStr != null) {
            interviewDate.setText(interviewDateStr.toString());
            interviewDate.setVisible(true);
            interviewDate.setManaged(true);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ApplicationCard)) {
            return false;
        }

        // state check
        ApplicationCard card = (ApplicationCard) other;
        return id.getText().equals(card.id.getText())
                && application.equals(card.application);
    }

    /**
     * Handles the edit internship button clicked event.
     */
    @FXML
    private void handleEditInternshipClicked() {
        if (!popupEditInternship.isShowing()) {
            popupEditInternship.show(index, application);
        } else {
            popupEditInternship.focus();
        }
    }
}
