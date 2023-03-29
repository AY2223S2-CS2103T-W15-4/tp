package seedu.address.logic.commands.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPLICATIONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.CompanyName;
import seedu.address.model.person.InternshipApplication;
import seedu.address.model.person.InternshipStatus;
import seedu.address.model.person.InterviewDate;
import seedu.address.model.person.JobTitle;
import seedu.address.model.person.Location;
import seedu.address.model.person.Note;
import seedu.address.model.person.ProgrammingLanguage;
import seedu.address.model.person.Qualification;
import seedu.address.model.person.Rating;
import seedu.address.model.person.Reflection;
import seedu.address.model.person.Review;
import seedu.address.model.person.Salary;

/**
 * Delete the contact details of an application identified using it's displayed index
 * from the list of internship applications.
 */
public class DeleteContactCommand extends Command {

    public static final String COMMAND_WORD = "delete_contact";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the contact details of of the specified application from the "
            + "list of internships applied.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_CONTACT_NOT_FOUND =
            "No contact added";

    public static final String MESSAGE_DELETE_CONTACT_SUCCESS =
            "Contact deleted from application: %1$s";

    private final Index targetIndex;

    /**
     * @param targetIndex of the internship application to delete contact
     */
    public DeleteContactCommand(Index targetIndex) {
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

        InternshipApplication internshipToDeleteContact = lastShownList.get(targetIndex.getZeroBased());
        if (internshipToDeleteContact.getContact() == null) {
            throw new CommandException(MESSAGE_CONTACT_NOT_FOUND);
        }
        InternshipApplication internshipWithContactDeleted =
                createInternshipWithContactDeleted(internshipToDeleteContact);

        model.setApplication(internshipToDeleteContact, internshipWithContactDeleted);
        model.updateFilteredInternshipList(PREDICATE_SHOW_ALL_APPLICATIONS);
        return new CommandResult(String.format(MESSAGE_DELETE_CONTACT_SUCCESS, internshipToDeleteContact));
    }

    /**
     * Creates and returns a {@code InternshipApplication} with the details of {@code internshipToDeleteDocuments}
     * and contact removed.
     */
    private static InternshipApplication createInternshipWithContactDeleted(
            InternshipApplication internshipToDeleteContact) {
        assert internshipToDeleteContact != null;

        CompanyName companyName = internshipToDeleteContact.getCompanyName();
        JobTitle jobTitle = internshipToDeleteContact.getJobTitle();
        Set<Review> reviews = internshipToDeleteContact.getReviews();
        Set<ProgrammingLanguage> programmingLanguages = internshipToDeleteContact.getProgrammingLanguages();
        Set<Qualification> qualifications = internshipToDeleteContact.getQualifications();
        Location location = internshipToDeleteContact.getLocation();
        Salary salary = internshipToDeleteContact.getSalary();
        Set<Note> notes = internshipToDeleteContact.getNotes();
        Rating rating = internshipToDeleteContact.getRating();
        Set<Reflection> reflections = internshipToDeleteContact.getReflections();
        InternshipStatus status = internshipToDeleteContact.getStatus();
        InterviewDate interviewDate = internshipToDeleteContact.getInterviewDate();

        return new InternshipApplication(companyName, jobTitle, reviews, programmingLanguages, qualifications, location,
                salary, notes, rating, reflections, null, status, interviewDate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteContactCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteContactCommand) other).targetIndex));
    }
}
