package recordpatterns;

public class RecordPatternsForSwitch {
    static void showCardWithRecordPattern(Card card) {
        System.out.println("-------------------");
        final var cardStatus = switch (card) {
            case PicPayCard(String name, String invoiceStatus, Boolean isCredit, Brand(String id, String brandName, String image)) -> invoiceStatus;
            case PicPayBenefits(var name, var nextRecharge, Brand(var id, var brandName, var image)) -> nextRecharge;
        };
        System.out.println(String.format("Status do cartão: %s", cardStatus));
    }

    public static void main(String[] args) {
        final var picpayCard = new PicPayCard(
            "Meu PicPay Card",
            "Fatura Fechada",
            true,
            new Brand("7", "picpay", "picpay"));
        final var picpayBenefitsCard = new PicPayBenefits(
            "Meu Cartão de Benefícios",
            "28/04/2023",
            new Brand("5", "elo", "elo"));
        showCardWithRecordPattern(picpayCard);
        showCardWithRecordPattern(picpayBenefitsCard);
    }


}
