package za.ac.cput.lostfoundapp.domain;

import java.sql.Date;

public class Claim {

    private int claimId;
    private int itemId;
    private int claimantUserId;
    private Date claimDate;
    private String proofDescription;
    private String status;
    private Date handoverDate;

    public Claim(int claimId, int itemId, int claimantUserId, Date claimDate,
            String proofDescription, String status, Date handoverDate) {
        this.claimId = claimId;
        this.itemId = itemId;
        this.claimantUserId = claimantUserId;
        this.claimDate = claimDate;
        this.proofDescription = proofDescription;
        this.status = status;
        this.handoverDate = handoverDate;
    }

    public int getClaimId() {
        return claimId;
    }

    public void setClaimId(int claimId) {
        this.claimId = claimId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getClaimantUserId() {
        return claimantUserId;
    }

    public void setClaimantUserId(int claimantUserId) {
        this.claimantUserId = claimantUserId;
    }

    public Date getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(Date claimDate) {
        this.claimDate = claimDate;
    }

    public String getProofDescription() {
        return proofDescription;
    }

    public void setProofDescription(String proofDescription) {
        this.proofDescription = proofDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getHandoverDate() {
        return handoverDate;
    }

    public void setHandoverDate(Date handoverDate) {
        this.handoverDate = handoverDate;
    }

}