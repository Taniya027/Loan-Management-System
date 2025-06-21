package LoanManagementSystem;

import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;

@Contract(
        name = "LoanManagement",
        info = @Info(
                title = "Loan Management contract",
                description = "A Sample contract for managing loan records",
                version = "0.0.1-SNAPSHOT"))
@Default
public final class LoanManagementContract implements ContractInterface {

    private final Genson genson = new Genson();

    // Enum for errors
    private enum LoanRecordErrors {
        LOAN_NOT_FOUND,
        LOAN_ALREADY_EXISTS
    }

    /**
     * Add a new loan record to the ledger.
     *
     * @param ctx the transaction context
     * @param loanId the ID for the new loan record
     * @param borrowerName the name of the borrower
     * @param borrowerId the ID of the borrower
     * @param loanAmount the amount of the loan
     * @param interestRate the interest rate of the loan
     * @param loanDate the date the loan was issued
     * @param repaymentDate the date the loan is due for repayment
     * @return the created LoanRecord
     */
    @Transaction()
    public LoanRecord addNewLoan(final Context ctx, final String loanId, final String borrowerName,
                                  final String borrowerId, final double loanAmount, final double interestRate,
                                  final String loanDate, final String repaymentDate) {

        ChaincodeStub stub = ctx.getStub();
        String loanState = stub.getStringState(loanId);

        // Check if the loan record already exists
        if (!loanState.isEmpty()) {
            String errorMessage = String.format("Loan record %s already exists", loanId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, LoanRecordErrors.LOAN_ALREADY_EXISTS.toString());
        }

        // Create a new LoanRecord
        LoanRecord loanRecord = new LoanRecord(loanId, borrowerName, borrowerId, loanAmount, interestRate, loanDate, repaymentDate);

        // Serialize the LoanRecord to save on the ledger
        loanState = genson.serialize(loanRecord);

        // Store the new loan record in the ledger
        stub.putStringState(loanId, loanState);

        return loanRecord;
    }

    /**
     * Retrieve a loan record by loan ID.
     *
     * @param ctx the transaction context
     * @param loanId the ID of the loan record to retrieve
     * @return the LoanRecord if found
     */
    @Transaction()
    public LoanRecord queryLoanById(final Context ctx, final String loanId) {
        ChaincodeStub stub = ctx.getStub();
        String loanState = stub.getStringState(loanId);

        // If the loan doesn't exist, throw an error
        if (loanState.isEmpty()) {
            String errorMessage = String.format("Loan record %s does not exist", loanId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, LoanRecordErrors.LOAN_NOT_FOUND.toString());
        }

        // Deserialize and return the loan record
        return genson.deserialize(loanState, LoanRecord.class);
    }

    /**
     * Updates the loan amount and interest rate of an existing loan record.
     *
     * @param ctx the transaction context
     * @param loanId the ID of the loan to update
     * @param newLoanAmount the new loan amount
     * @param newInterestRate the new interest rate
     * @return the updated LoanRecord
     */
    @Transaction()
    public LoanRecord updateLoan(final Context ctx, final String loanId, final double newLoanAmount,
                                 final double newInterestRate) {
        ChaincodeStub stub = ctx.getStub();
        String loanState = stub.getStringState(loanId);

        // If the loan doesn't exist, throw an error
        if (loanState.isEmpty()) {
            String errorMessage = String.format("Loan record %s does not exist", loanId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, LoanRecordErrors.LOAN_NOT_FOUND.toString());
        }

        // Deserialize the loan record to update it
        LoanRecord loanRecord = genson.deserialize(loanState, LoanRecord.class);

        // Update the loan amount and interest rate
        loanRecord = new LoanRecord(loanRecord.getLoanId(), loanRecord.getBorrowerName(), loanRecord.getBorrowerId(),
                newLoanAmount, newInterestRate, loanRecord.getLoanDate(), loanRecord.getRepaymentDate());

        // Serialize and update the loan record in the ledger
        loanState = genson.serialize(loanRecord);
        stub.putStringState(loanId, loanState);

        return loanRecord;
    }
}
