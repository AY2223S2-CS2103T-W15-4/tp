package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.InternshipApplication;

/**
 * Controller for an internship page
 */
public class InternshipWindow extends UiPart<Stage> {

    private static final String FXML = "InternshipWindow.fxml";
    private static final Logger logger = LogsCenter.getLogger(InternshipWindow.class);

    @FXML
    private ScrollPane sPane;
    @FXML
    private VBox vBox;
    @FXML
    private Label companyName;
    @FXML
    private Label jobTitle;
    @FXML
    private Label place;
    @FXML
    private Label salary;
    @FXML
    private Label rating;

    /**
     * Creates a new InternshipWindow.
     *
     * @param root Stage to use as the root of the InternshipWindow.
     */
    public InternshipWindow(Stage root, InternshipApplication internship) {
        super(FXML, root);
        companyName.setText(internship.getCompanyName().fullName);
        jobTitle.setText(internship.getJobTitle().fullName);
        place.setText(internship.getLocation().value);
        salary.setText(internship.getSalary().value);
        rating.setText(internship.getRating().value);
        vBox.getChildren().add(new Text("Qualifications:"));
        internship.getQualifications().stream()
                .forEach(q -> vBox.getChildren().add(new Label(q.value)));
        vBox.getChildren().add(new Text("Programming Languages:"));
        internship.getProgrammingLanguages().stream()
                .forEach(plg -> vBox.getChildren().add(new Label(plg.value)));
        vBox.getChildren().add(new Text("Reviews:"));
        internship.getReviews().stream()
                .forEach(review -> vBox.getChildren().add(new Label(review.value)));
        vBox.getChildren().add(new Text("Notes:"));
        internship.getNotes().stream()
                .forEach(n -> vBox.getChildren().add(new Label(n.value)));
        vBox.getChildren().add(new Text("Reflections:"));
        internship.getReflections().stream()
                .forEach(r -> vBox.getChildren().add(new Label(r.value)));
        for (int i = 0; i < 5; i += 1) {
            vBox.getChildren().add(new Text(" "));
        }
    }

    /**
     * Creates a new InternshipWindow.
     */
    public InternshipWindow(InternshipApplication internship) {
        this(new Stage(), internship);
        companyName.setText(internship.getCompanyName().fullName);
    }

    /**
     * Shows the internship window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing internship page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the internship window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the internship window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the internship window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
