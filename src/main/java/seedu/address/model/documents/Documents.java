package seedu.address.model.documents;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a contact in the internship application.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Documents {

    // Identity fields
    private final ResumeLink resumeLink;
    private final CoverLetterLink coverLetterLink;

    /**
     * Every field must be present and not null.
     */
    public Documents(ResumeLink resumeLink, CoverLetterLink coverLetterLink) {
        requireAllNonNull(resumeLink, coverLetterLink);
        this.resumeLink = resumeLink;
        this.coverLetterLink = coverLetterLink;
    }

    public ResumeLink getResume() {
        return resumeLink;
    }

    public CoverLetterLink getCoverLetter() {
        return coverLetterLink;
    }

    /**
     * Returns true if both contacts have the same identity.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Documents)) {
            return false;
        }

        Documents otherDocuments = (Documents) other;
        return otherDocuments.getResume().equals(getResume())
                && otherDocuments.getCoverLetter().equals(getCoverLetter());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(resumeLink, coverLetterLink);
    }

    @Override
    public String toString() {
        String builder = "Resume: "
                + getResume()
                + "; Cover letter: "
                + getCoverLetter();

        return builder;
    }

}
