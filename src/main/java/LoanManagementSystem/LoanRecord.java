package LoanManagementSystem;
	
import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import java.util.Objects;

@DataType()
public final class LoanRecord {

    @Property()
    private final String loanId;

    @Property()
    private final String borrowerName;

    @Property()
    private final String borrowerId;

    @Property()
    private final double loanAmount;

    @Property()
    private final double interestRate;

    @Property()
    private final String loanDate;

    @Property()
    private final String repaymentDate;

    // Constructor
    public LoanRecord(
        @JsonProperty("loanId") final String loanId,
        @JsonProperty("borrowerName") final String borrowerName,
        @JsonProperty("borrowerId") final String borrowerId,
        @JsonProperty("loanAmount") final double loanAmount,
        @JsonProperty("interestRate") final double interestRate,
        @JsonProperty("loanDate") final String loanDate,
        @JsonProperty("repaymentDate") final String repaymentDate
    ) {
        this.loanId = loanId;
        this.borrowerName = borrowerName;
        this.borrowerId = borrowerId;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.loanDate = loanDate;
        this.repaymentDate = repaymentDate;
    }

    // Getter methods
    public String getLoanId() {
        return loanId;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public String getBorrowerId() {
        return borrowerId;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public String getRepaymentDate() {
        return repaymentDate;
    }

    // Override equals method to compare loan record objects
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        LoanRecord other = (LoanRecord) obj;

        return Objects.deepEquals(
            new Object[] { getLoanId(), getBorrowerName(), getBorrowerId(), getLoanAmount(), getInterestRate(), getLoanDate(), getRepaymentDate() },
            new Object[] { other.getLoanId(), other.getBorrowerName(), other.getBorrowerId(), other.getLoanAmount(), other.getInterestRate(), other.getLoanDate(), other.getRepaymentDate() }
        );
    }

    // Override hashCode method for consistency
    @Override
    public int hashCode() {
        return Objects.hash(getLoanId(), getBorrowerName(), getBorrowerId(), getLoanAmount(), getInterestRate(), getLoanDate(), getRepaymentDate());
    }

    // Override toString method for easy representation
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " [loanId=" + loanId
            + ", borrowerName=" + borrowerName + ", borrowerId=" + borrowerId + ", loanAmount=" + loanAmount
            + ", interestRate=" + interestRate + ", loanDate=" + loanDate + ", repaymentDate=" + repaymentDate + "]";
    }
}
