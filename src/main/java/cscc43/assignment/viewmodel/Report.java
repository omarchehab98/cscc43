package cscc43.assignment.viewmodel;

public class Report {
    private Object[][] data;
    private String[] header;

    public Report(Object[][] data, String[] header) {
        this.data = data;
        this.header = header;
    }

    public Object[][] getData() {
        return this.data;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }

    public String[] getHeader() {
        return this.header;
    }

    public void setHeader(String[] header) {
        this.header = header;
    }
}
