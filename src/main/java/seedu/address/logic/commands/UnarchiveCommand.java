package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ONGOING_APPLICATIONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.person.CompanyName;
import seedu.address.model.person.InternshipApplication;
import seedu.address.model.person.InternshipStatus;
import seedu.address.model.person.InterviewDate;
import seedu.address.model.person.JobTitle;

/**
 * Unarchives an internship application identified using it's displayed index from the list of internship applications.
 */
public class UnarchiveCommand extends Command {

    public static final String COMMAND_WORD = "unarchive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unarchives the specified application from the list of internships applied.\n"
            + "Unarchives the application of internship at the specified INDEX.\n"
            + "The index refers to the index number shown in the displayed internship list.\n"
            + "Parameters: INDEX (must be a positive integer 1, 2, 3, ...)\n"
            + "Example: " + COMMAND_WORD + " 2";

    public static final String MESSAGE_ARCHIVE_APPLICATION_SUCCESS = "Unarchived Application: %1$s";

    private final Index targetIndex;

    /**
     * Creates an UnarchiveCommand to unarchive the specified {@code targetIndex} internship
     *
     * @param targetIndex of the internship application to unarchive
     */
    public UnarchiveCommand(Index targetIndex) {
        requireNonNull(targetIndex);

        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<InternshipApplication> lastShownList = model.getFilteredInternshipList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
        }

        InternshipApplication internshipToUnarchive = lastShownList.get(targetIndex.getZeroBased());
        InternshipApplication archivedApplication = createdUnarchivedApplication(internshipToUnarchive);

        model.setApplication(internshipToUnarchive, archivedApplication);
        model.updateFilteredInternshipList(PREDICATE_SHOW_ONGOING_APPLICATIONS);
        return new CommandResult(String.format(MESSAGE_ARCHIVE_APPLICATION_SUCCESS, archivedApplication));
    }

    /**
     * Creates and returns an archived {@code InternshipApplication}
     */
    private static InternshipApplication createdUnarchivedApplication(InternshipApplication internshipApplication) {
        assert internshipApplication != null;

        CompanyName companyName = internshipApplication.getCompanyName();
        JobTitle jobTitle = internshipApplication.getJobTitle();
        Contact contact = internshipApplication.getContact();
        InternshipStatus status = internshipApplication.getStatus();
        InterviewDate interviewDate = internshipApplication.getInterviewDate();

        return new InternshipApplication(companyName, jobTitle, contact, status,
                false, interviewDate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnarchiveCommand // instanceof handles nulls
                && targetIndex.equals(((UnarchiveCommand) other).targetIndex)); // state check
    }
}
