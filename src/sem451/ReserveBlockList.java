package sem451;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to manage a list of ReserveBlock objects.
 */
public class ReserveBlockList {
    private List<ReserveBlock> reserveBlocks;

    public ReserveBlockList() {
        reserveBlocks = new ArrayList<>();
    }

    public void addReserveBlock(ReserveBlock rb) {
        reserveBlocks.add(rb);
    }

    public boolean removeReserveBlock(ReserveBlock rb) {
        return reserveBlocks.remove(rb);
    }

    public List<ReserveBlock> findReserveBlocksByDate(LocalDate date) {
        List<ReserveBlock> result = new ArrayList<>();
        for (ReserveBlock rb : reserveBlocks) {
            if (rb.getDate().equals(date)) {
                result.add(rb);
            }
        }
        return result;
    }

  
    public List<LocalDate> findDatesForReservedBlock(ReserveBlock targetBlock) {
        List<LocalDate> matchingDates = new ArrayList<>();
        for (ReserveBlock rb : reserveBlocks) {
            if (rb.equals(targetBlock)) {
                matchingDates.add(rb.getDate());
            }
        }
        return matchingDates;
    }

    public List<ReserveBlock> getAllReserveBlocks() {
        return new ArrayList<>(reserveBlocks);
    }
}
