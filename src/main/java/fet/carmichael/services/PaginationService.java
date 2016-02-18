package fet.carmichael.services;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 *
 * @author bill
 */
@Service
public class PaginationService {

    /**
     *
     * @param clickPage 點選的分頁編號
     * @param totalCount query的資料筆數
     * @param rowsShowCount 一頁欲顯示的資料筆數
     * @param pageIndexCount 欲顯示的頁數索引數量
     * @return
     */
    public Map<String, Object> pagination(final int clickPage, final int totalCount, final int rowsShowCount, final int pageIndexCount) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("clickPage", clickPage);
        result.put("begin", ((clickPage - 1) / pageIndexCount) * pageIndexCount + 1);

        final int totalPages = totalCount % rowsShowCount != 0 ? (totalCount / rowsShowCount) + 1 : totalCount / rowsShowCount;
        if (totalPages < pageIndexCount) {
            result.put("end", totalPages);
        } else {
            if ((totalPages - 1) / pageIndexCount == (clickPage - 1) / pageIndexCount) {
                result.put("end", ((totalPages - 1) / pageIndexCount) * pageIndexCount + (totalPages % pageIndexCount == 0 ? pageIndexCount : totalPages % pageIndexCount));
            } else {
                result.put("end", ((clickPage - 1) / pageIndexCount) * pageIndexCount + pageIndexCount);
            }
        }
        result.put("disablePrevious", clickPage <= pageIndexCount);
        result.put("disableNext", (totalPages - 1) / pageIndexCount == (clickPage - 1) / pageIndexCount);

        return result;
    }
}
