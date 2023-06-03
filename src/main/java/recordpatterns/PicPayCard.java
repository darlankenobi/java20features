package recordpatterns;

public record PicPayCard(String nickname, String invoiceStatus, Boolean isCreditFunctionActive, Brand brand) implements Card{

    @Override
    public String bin() {
        return "654321";
    }

}
