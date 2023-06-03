package recordpatterns;

public record PicPayBenefits(String nickname, String nextRechargeDate, Brand brand) implements Card {

    @Override
    public String bin() {
        return "123456";
    }

}
