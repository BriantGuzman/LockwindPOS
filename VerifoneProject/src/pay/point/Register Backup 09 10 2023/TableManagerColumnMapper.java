import java.util.HashMap;
import java.util.Map;

public class TableManagerColumnMapper {
    private Map<String, Integer> columnMap;
    private int columnCount;

    public TableManagerColumnMapper() {
        columnMap = new HashMap<>();
        columnCount = 0;
    }

    public void addColumn(String columnName) {
        columnMap.put(columnName, columnCount);
        columnCount++;
    }

    public int getColumnNumber(String columnName) {
        if (columnMap.containsKey(columnName)) {
            return columnMap.get(columnName);
        }
        return -1; // Column not found
    }

    public static void main(String[] args) {

/* 
        ColumnMapper columnMapper = new ColumnMapper();
        
        // Adding column names
        columnMapper.addColumn("Name");
        columnMapper.addColumn("Age");
        columnMapper.addColumn("Address");
        columnMapper.addColumn("Phone");

        // Searching for column numbers
        String columnName = "name";
        int columnNumber = columnMapper.getColumnNumber(columnName);
        if (columnNumber != -1) {
            System.out.println("Column \"" + columnName + "\" found at number: " + columnNumber);
        } else {
            System.out.println("Column \"" + columnName + "\" not found.");
        }
        */
    }
}
